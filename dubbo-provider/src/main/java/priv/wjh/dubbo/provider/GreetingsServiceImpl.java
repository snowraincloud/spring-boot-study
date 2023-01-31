package priv.wjh.dubbo.provider;


import priv.wjh.dubbo.api.GreetingsService;

public class GreetingsServiceImpl implements GreetingsService {
    @Override
    public String sayHi(String name) {
        return "hi, " + name;
    }
}