package priv.wjh.netty.study.common;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.TimeUnit;

public class DemoSocketClientHandler
        extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
        ctx.channel().writeAndFlush("from client: " + System.currentTimeMillis());
        TimeUnit.MILLISECONDS.sleep(5000);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {
        ctx.channel().writeAndFlush("from client：begin talking");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx,
                                Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}