package priv.wjh.netty.study.tls;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.util.CharsetUtil;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import priv.wjh.netty.study.common.DemoSocketClientHandler;

import java.io.IOException;
import java.io.InputStream;

public class NettyTlsClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Resource resource = new ClassPathResource("cert/server.crt");
            InputStream crtInputStream = resource.getInputStream();
//        System.out.println(IOUtils.toString(keyInputStream, Charset.defaultCharset()));
            SslContext sslContext = SslContextBuilder
                    .forClient()
                    .trustManager(crtInputStream)
                    .build();

            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addFirst("ssl", sslContext.newHandler(ch.alloc()));
                            pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
                            pipeline.addLast(new DemoSocketClientHandler());
                        }
                    });

            ChannelFuture future = bootstrap.connect("localhost", 8888).sync();
            future.channel().closeFuture().sync();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}