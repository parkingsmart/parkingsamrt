package com.oocl.parkingsmart.endpoint;

import org.springframework.stereotype.Component;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
@Component
@ServerEndpoint("/users/{userId}/orders")
public class UserEndpoint {
    private static Map<Long, Session> sessionPool = new HashMap<Long, Session>();

    @OnOpen
    public void onOpen(@PathParam("userId") Long userId, Session session) {
        sessionPool.put(userId, session);
        System.out.println(String.format("用户: %d 进入连接", userId));
    }

    public void sendAllMessage(String message) {
        for (Session session : sessionPool.values()) {
            try {
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


