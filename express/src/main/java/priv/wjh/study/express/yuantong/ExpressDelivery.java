package priv.wjh.study.express.yuantong;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ExpressDelivery {
    private static final Logger logger = LoggerFactory.getLogger(ExpressDelivery.class);
    private static final RestTemplate restTemplate = new RestTemplate();

    static {
        restTemplate.setErrorHandler(new AcceptResponseErrorHandler());
    }

    private static class AcceptResponseErrorHandler implements ResponseErrorHandler {

        @Override
        public boolean hasError(ClientHttpResponse response) throws IOException {
            return response.getStatusCode() != HttpStatus.OK;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            String read = IoUtil.read(response.getBody(), Charset.defaultCharset());
            throw new IOException("请求失败: " + read);
        }
    }

    public static Map createOrder(YTOOrder ytoOrder){
        String jsonString = JSONUtil.toJsonStr(ytoOrder);
        logger.info("创建快递订单: {}", jsonString);
        Map<String, String> map = toRequestData(ytoOrder, "privacy_create_adapter");

        Map order = sendRequest(YuanTongConfig.CREATE_ORDER_URL, map);
        if (map.containsKey("success")){
            logger.error("失败: {}", JSONUtil.toJsonStr(order));
            throw new RuntimeException("创建订单失败: " + map.get("reason"));
        }
        logger.info("创建快递订单成功: {}", JSONUtil.toJsonStr(order));
        return order;
    }

    public static Map printWaybill(String waybillNo){
        Map<String, String> data = new HashMap<>();
        data.put("waybillNo", waybillNo);
        Map<String, String> map = toRequestData(data, "waybill_print_adapter");

        Map order = sendRequest(YuanTongConfig.PRINT_WAYBILL_URL, map);
        if (Objects.equals(-1, order.get("code"))){
            logger.error("失败: {}", JSONUtil.toJsonStr(order));
            throw new RuntimeException("面单打印失败: " + map.get("message"));
        }
        logger.info("面单打印成功: {}", JSONUtil.toJsonStr(order));
        return order;

    }

    /**
     * 圆通签名获取
     * 开放平台公共加密方法-使用commons-codec-1.11.jar进行md5加密,然后对数组进行base64编码
     * @param data   = param+method+v
     * @param secret
     * @return
     */
    public static String encryptSignForOpen(String data, String secret) {
        String sign;
        try {
            byte[] signByte = DigestUtils.md5(data + secret);
            sign = Base64.encodeBase64String(signByte);
        } catch (Throwable e) {
            logger.info("签名失败: ", e);
            throw new RuntimeException("签名失败");
        }
        return sign;
    }

    private static Map<String, String> toRequestData(Object obj, String method) {
        String jsonString = JSONUtil.toJsonStr(obj);
        String data = jsonString + method + "v1";
        // 签名
        String sign = encryptSignForOpen(data,YuanTongConfig.SECRET);
        // 封装参数
        Map<String, String> map = new HashMap<>();
        // 获取时间戳
        map.put("timestamp", String.valueOf(System.currentTimeMillis()));
        map.put("param", jsonString);
        map.put("sign", sign);
        map.put("format", "JSON");
        return map;
    }
    private static Map sendRequest(String url, Map<String, String> map) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/json; charset=UTF-8"));
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(map, headers);

        ResponseEntity<Map> response = restTemplate
                .postForEntity(url, request, Map.class);
        if (response.getStatusCode() != HttpStatus.OK){
            logger.error("");
        }
        Map order = response.getBody();
        return order;
    }

    public static void main(String[] args) {
//        //参数设置
        YTOOrder ytoOrder = new YTOOrder();
        ytoOrder.setSenderName("cathy");
        ytoOrder.setSenderProvinceName("云南省");
        ytoOrder.setSenderCityName("昆明市");
        ytoOrder.setSenderCountyName("呈贡区");
        ytoOrder.setSenderAddress("春融街");
        ytoOrder.setSenderMobile("18789656985");

        ytoOrder.setRecipientName("福州艾玛超市");
        ytoOrder.setRecipientProvinceName("山西省");
        ytoOrder.setRecipientCityName("晋中市");
        ytoOrder.setRecipientCountyName("寿阳县朝阳镇");
        ytoOrder.setRecipientAddress("福州新弯曲5号");
        ytoOrder.setRecipientMobile("2132-23213421");
        ytoOrder.setRemark("测试");
        ytoOrder.setLogisticsNo("XS202306250001");

        createOrder(ytoOrder);
//

//        printWaybill("YT2819001955919");
    }
}
