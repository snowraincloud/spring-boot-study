package priv.wjh.study.design.pattern.strategy;

import org.springframework.stereotype.Component;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Component
public class AliPayStrategy implements IPayStrategy{
    @Override
    public void pay(PayEnum payEnum) {
        System.out.println("ALi pay strategy");
    }

    @Override
    public PayEnum getType() {
        return PayEnum.ALI;
    }
}
