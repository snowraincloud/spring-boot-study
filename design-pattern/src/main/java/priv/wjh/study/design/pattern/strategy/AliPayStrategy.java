package priv.wjh.study.design.pattern.strategy;

import org.springframework.stereotype.Service;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Service
public class AliPayStrategy implements IPayStrategy{
    @Override
    public void pay() {
        System.out.println("ALi pay strategy");
    }

    @Override
    public PayEnum getType() {
        return PayEnum.ALI;
    }
}
