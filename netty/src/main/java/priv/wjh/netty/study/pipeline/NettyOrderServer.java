package priv.wjh.netty.study.pipeline;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import priv.wjh.netty.study.common.DemoSocketServerHandler;

public class NettyOrderServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup parentGroup = new NioEventLoopGroup(1);
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {

            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup)
                    .channel(NioServerSocketChannel.class)
//                    .option(ChannelOption.SO_BACKLOG, 2)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) {

                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());

//                            pipeline.addLast(new OrderInboundHandler(false, false));
//                            pipeline.addLast(new OrderInboundHandler(false, false));
//
//                            pipeline.addLast(new OrderInboundHandler(true, false));
//                            pipeline.addLast(new OrderOutboundHandler());
//                            pipeline.addLast(new OrderOutboundHandler());
//                            pipeline.addLast(new OrderOutboundHandler());
                            pipeline.addLast(new DemoSocketServerHandler());

                        }
                    });

            ChannelFuture future = bootstrap.bind(8888)
                    .sync();
            System.out.println("服务器已启动。。。");

            future.channel()
                    .closeFuture()
                    .sync();
        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }
}
