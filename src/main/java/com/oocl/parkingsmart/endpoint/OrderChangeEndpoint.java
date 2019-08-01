package com.oocl.parkingsmart.endpoint;

import org.springframework.stereotype.Component;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;

@Component
@ServerEndpoint("/employees/{employeeId}/orderChange")
public class OrderChangeEndpoint {
    private static Map<Long, Session> sessionPool = new HashMap<Long, Session>();

    @OnOpen
    public void onOpen(@PathParam("employeeId") Long employeeId, Session session) {
        sessionPool.put(employeeId, session);
    }

    public void sendOneMessage(Long id, String message) {
        Session session = sessionPool.get(id);

        if (session != null) {
            try {
                session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
