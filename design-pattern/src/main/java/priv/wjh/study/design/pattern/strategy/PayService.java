package priv.wjh.study.design.pattern.strategy;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * //TODO
 *
 * @author wangjunhao
 **/
@Service
public class PayService implements IPay{
    private final Map<PayEnum, IPayStrategy> payStrategy;

    public PayService(List<IPayStrategy> payStrategies) {
        this.payStrategy = payStrategies.stream()
                .collect(Collectors.toMap(IPayStrategy::getType, Function.identity()));
    }

    @Override
    public void pay(PayEnum payEnum){
        this.payStrategy.get(payEnum).pay(payEnum);
    }
}
