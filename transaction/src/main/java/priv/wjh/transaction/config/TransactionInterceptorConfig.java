package priv.wjh.transaction.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Properties;

/**
 * <h1>自定义切面拦截器配置</h1>
 *
 * @author wangjunhao
 **/
@Slf4j
@Configuration
@RequiredArgsConstructor
public class TransactionInterceptorConfig {

    private final PlatformTransactionManager transactionManager;


    public static class MyTransactionInterceptor extends TransactionInterceptor{
        public MyTransactionInterceptor(PlatformTransactionManager transactionManager, Properties properties) {
            super(transactionManager, properties);
        }

        @Override
        protected TransactionInfo createTransactionIfNecessary(PlatformTransactionManager tm, TransactionAttribute txAttr, String joinpointIdentification) {
            logger.info("begin transaction");
            return super.createTransactionIfNecessary(tm, txAttr, joinpointIdentification);
        }

        @Override
        protected void commitTransactionAfterReturning(TransactionInfo txInfo) {
            logger.info("commit transaction");
            super.commitTransactionAfterReturning(txInfo);
        }

        @Override
        protected void completeTransactionAfterThrowing(TransactionInfo txInfo, Throwable ex) {
            logger.info("rollback transaction");
            super.completeTransactionAfterThrowing(txInfo, ex);
        }
    }

    /**
     * <h2>事务拦截器</h2>
     * <p>拦截方法名以delete为前缀的方法</p>
     */
    public TransactionInterceptor txAdvice(PlatformTransactionManager transactionManager){
        Properties properties = new Properties();
        // 方法名, 事务配置
        properties.put("delete*", "PROPAGATION_REQUIRED,-Throwable");
        logger.info("customize transaction interceptor");
        return new MyTransactionInterceptor(transactionManager, properties);
    }


    /**
     * <h2>定义切面</h2>
     */
    @Bean
    public Advisor advisor() {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* priv.wjh..*Service.*(..))");
        return new DefaultPointcutAdvisor(pointcut, txAdvice(transactionManager));
    }
}
