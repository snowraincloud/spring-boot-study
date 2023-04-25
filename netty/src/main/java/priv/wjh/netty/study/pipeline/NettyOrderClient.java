package priv.wjh.netty.study.pipeline;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import priv.wjh.netty.study.common.DemoSocketClientHandler;

public class NettyOrderClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup(2);
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
//                            pipeline.addFirst(new LoggingHandler(LogLevel.INFO));
                            pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new DemoSocketClientHandler());
                        }
                    });

            ChannelFuture[] futures = new ChannelFuture[4];
            for (int i = 0; i < 4; i++) {
                ChannelFuture future = bootstrap.connect("localhost", 8888);
                int finalI = i;
                future.addListener(new ChannelFutureListener() {
                    @Override
                    public void operationComplete(ChannelFuture channelFuture) throws Exception {
                        if (channelFuture.channel().isActive()){
                            System.out.println(finalI + " is active");
                        }
                    }
                });
                futures[i] = future;
            }

            // close all futures
            for (ChannelFuture future : futures) {
                future.channel().closeFuture().sync();
            }
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
