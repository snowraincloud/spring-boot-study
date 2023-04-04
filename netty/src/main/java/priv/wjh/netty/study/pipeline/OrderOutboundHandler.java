package priv.wjh.netty.study.pipeline;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class OrderOutboundHandler extends ChannelOutboundHandlerAdapter {

    private static final AtomicInteger ATOMIC_INTEGER = new AtomicInteger();
    private final Integer no;
    private final String name;

    public OrderOutboundHandler() {
        this.no = ATOMIC_INTEGER.incrementAndGet();
        this.name = "OrderOutboundHandler: " + no;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        System.out.println(name);
        super.write(ctx, msg, promise);
    }

}
