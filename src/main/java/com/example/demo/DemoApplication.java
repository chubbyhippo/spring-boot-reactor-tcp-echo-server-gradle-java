package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.netty.tcp.TcpServer;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Value("${tcp.server.port}")
    private int port;

    @Bean
    ApplicationRunner run() {
        return args -> TcpServer.create()
                .port(port)
                .wiretap(true)
                .handle((nettyInbound, nettyOutbound) -> nettyOutbound.sendByteArray(nettyInbound.receive()
                        .asByteArray()
                ))
                .bindNow()
                .onDispose()
                .block();

    }

}
