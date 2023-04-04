package priv.wjh.netty.study.pipeline;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

public class OrderInboundHandler extends ChannelInboundHandlerAdapter {
    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();
    private final Integer no;
    private final boolean flush;
    private final boolean throwable;
    private final String name;

    public OrderInboundHandler(boolean flush, boolean throwable) {
        this.flush = flush;
        this.throwable = throwable;
        this.no = ATOMIC_INTEGER.incrementAndGet();
        this.name = "OrderInboundHandler: " + no;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        System.out.println(name);
        if (throwable){
            throw new RuntimeException(name);
        }
        if (flush){
            // 从当前handler往前遍历发送
//            ctx.writeAndFlush(name);
            // 从pipeline的tail往前遍历发送
            ctx.channel().writeAndFlush(name);
            System.out.println(name + " write");
        }else {
            super.channelRead(ctx, msg);
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("OrderInboundHandlerException: " + name);
        super.exceptionCaught(ctx, cause);
    }
}
