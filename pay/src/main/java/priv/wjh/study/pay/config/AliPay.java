package priv.wjh.study.pay.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import com.alipay.easysdk.util.generic.models.AlipayOpenApiGenericResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import priv.wjh.study.pay.po.WithdrawDto;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Slf4j
@Component
public class AliPay {

    private static AtomicInteger i = new AtomicInteger(1);
    static {
        Config config = new Config();

        config.protocol = "https";
        config.gatewayHost = "openapi.alipay.com";
        config.signType = "RSA2";

        config.appId = AlipayConfig.APPID;

        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = AlipayConfig.RSA_PRIVATE_KEY;

        // 注：证书文件路径支持设置为文件系统中的路径或CLASS_PATH中的路径，优先从文件系统中加载，加载失败后会继续尝试从CLASS_PATH中加载
        config.merchantCertPath = "cert/appCertPublicKey_2016102700768055.crt";
        config.alipayCertPath = "cert/alipayCertPublicKey_RSA2.crt";
        config.alipayRootCertPath = "cert/alipayRootCert.crt";
//
        //注：如果采用非证书模式，则无需赋值上面的三个证书路径，改为赋值如下的支付宝公钥字符串即可
//         config.alipayPublicKey = AlipayConfig.ALIPAY_PUBLIC_KEY;

        //可设置异步通知接收服务地址（可选）
//        config.notifyUrl = "<-- 请填写您的支付类接口异步通知接收服务地址，例如：https://www.test.com/callback -->";

        Factory.setOptions(config);
    }

    private final static String SUCCESS_CODE = "10000";

    @Autowired
    private ObjectMapper objectMapper;

    public HashMap<String, String> transfer(WithdrawDto withdrawDto) throws Exception {
//        disableChecks();
        log.info("开始转账: 账号信息[{}]", objectMapper.writeValueAsString(withdrawDto));
        HashMap<String, Object> bizContent = new HashMap<>();
        bizContent.put("out_biz_no", String.valueOf(System.currentTimeMillis()));
        bizContent.put("trans_amount", withdrawDto.getAmount() / 100 + "." + withdrawDto.getAmount() % 100);
        bizContent.put("product_code", "TRANS_ACCOUNT_NO_PWD");
        bizContent.put("biz_scene", "DIRECT_TRANSFER");
        bizContent.put("order_title", "withdraw");
        HashMap<String, String> payee = new HashMap<>();
        payee.put("identity_type", "ALIPAY_LOGON_ID");
        payee.put("identity", withdrawDto.getToAlipayAccount());
        payee.put("name", withdrawDto.getRealName());
        bizContent.put("payee_info", objectMapper.writeValueAsString(payee));

        AlipayOpenApiGenericResponse response = Factory.Util.Generic()
                .execute("alipay.fund.trans.uni.transfer", new HashMap<>(), bizContent);

        if (!SUCCESS_CODE.equals(response.getCode())){
            log.error("转账失败, 入参[{}], 出参[{}]", objectMapper.writeValueAsString(bizContent), response.httpBody);
            throw new RuntimeException("转账失败: " + response.subMsg);
        }
        HashMap<String, Object> body = objectMapper.readValue(
                response.httpBody, new TypeReference<HashMap<String, Object>>() {});
        HashMap<String, String> data = (HashMap<String, String>) body.get("alipay_fund_trans_uni_transfer_response");
        if (!SUCCESS_CODE.equals(data.get("code"))){
            log.error("转账失败, 入参[{}], 出参[{}]", objectMapper.writeValueAsString(bizContent), response.httpBody);
            throw new RuntimeException("转账失败");
        }
        log.info("转账成功， 入参[{}], 出参[{}]", objectMapper.writeValueAsString(bizContent), response.httpBody);
        return data;
    }



}
