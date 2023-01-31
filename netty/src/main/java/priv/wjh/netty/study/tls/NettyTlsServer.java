package priv.wjh.netty.study.tls;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import priv.wjh.netty.study.common.DemoSocketServerHandler;

import java.io.IOException;
import java.io.InputStream;

public class NettyTlsServer {
    public static void main(String[] args) throws IOException {
        EventLoopGroup ioEventLoopGroup = new NioEventLoopGroup();
        EventLoopGroup workEventLoopGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        Resource resource = new ClassPathResource("cert/server.crt");
        InputStream crtInputStream = resource.getInputStream();
//        System.out.println(IOUtils.toString(crtInputStream, Charset.defaultCharset()));
        resource = new ClassPathResource("cert/server.key");
        InputStream keyInputStream = resource.getInputStream();
//        System.out.println(IOUtils.toString(keyInputStream, Charset.defaultCharset()));
        SslContext sslContext = SslContextBuilder.forServer(crtInputStream, keyInputStream)
                .build();

        serverBootstrap
                .group(ioEventLoopGroup, workEventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addFirst("ssl", sslContext.newHandler(ch.alloc()));
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(new DemoSocketServerHandler());
                    }

                });


        try {
            ChannelFuture future = serverBootstrap.bind(8888).sync();
            System.out.println("服务器已启动。。。");

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally {
            ioEventLoopGroup.shutdownGracefully();
            workEventLoopGroup.shutdownGracefully();
        }
    }
}
