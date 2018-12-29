package com.jaelyn.chat;

import com.jaelyn.chat.common.websocketchat.WebsocketChatServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyVueChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(NettyVueChatApplication.class, args);

        try {
            new WebsocketChatServer().run();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

