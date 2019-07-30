package com.oocl.parkingsmart.endpoint;

import org.springframework.stereotype.Component;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;

@Component
@ServerEndpoint("/employees/{employeeId}/orders")
public class OrderEndpoint {
    private static Map<Long, Session> sessionPool = new HashMap<Long, Session>();

    @OnOpen
    public void onOpen(@PathParam("employeeId") Long employeeId, Session session) {
        sessionPool.put(employeeId, session);
        System.out.println(String.format("员工: %d 进入连接", employeeId));
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
