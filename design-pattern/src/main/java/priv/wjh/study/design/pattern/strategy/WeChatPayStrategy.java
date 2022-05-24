package priv.wjh.study.design.pattern.strategy;

import org.springframework.stereotype.Component;

/**
 * //TODO
 *
 * @author wangjunhao
 **/

@Component
public class WeChatPayStrategy implements IPayStrategy{

    @Override
    public void pay(PayEnum payEnum) {
        System.out.println("We chat pay strategy");
    }

    @Override
    public PayEnum getType() {
        return PayEnum.WE_CHAT;
    }
}
