package config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.util.ResponseChecker;
import com.alipay.easysdk.payment.facetoface.models.AlipayTradePrecreateResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import priv.wjh.study.pay.PayApplication;
import priv.wjh.study.pay.config.AliPay;
import priv.wjh.study.pay.po.WithdrawDto;

/**
 * 如果你遇到了 `org/springframework/beans/factory/config/InstantiationAwareBeanPostProcessorAdapter` 类找不到的错误，可能是因为你使用的 Spring 版本较新，该类已经被废弃了。
 */
@SpringBootTest(classes = PayApplication.class)
class AliPayTest {

    @Autowired
    private AliPay aliPay;

    @Test
    void transfer() throws Exception {
        WithdrawDto withdrawDto = new WithdrawDto();
        withdrawDto.setAmount(500020L);
        withdrawDto.setToAlipayAccount("bcguyw6239@sandbox.com");
        withdrawDto.setRealName("bcguyw6239");
        aliPay.transfer(withdrawDto);
    }


    @Test
    void test(){
        try {

            // 2. 发起API调用（以创建当面付收款二维码为例）
            AlipayTradePrecreateResponse response = Factory.Payment.FaceToFace()
                    .preCreate("Apple iPhone11 128G", "2234567890", "5799.00");
            // 3. 处理响应或异常
            if (ResponseChecker.success(response)) {
                System.out.println("调用成功");
            } else {
                System.err.println("调用失败，原因：" + response.msg + "，" + response.subMsg);
            }
        } catch (Exception e) {
            System.err.println("调用遭遇异常，原因：" + e.getMessage());
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
