package com.devlf.apitime.websocket;

import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/timer")
public class TimerEndpoint {
	
    @OnOpen
    public void onOpen(Session session) throws IOException {
        System.out.println("onOpen::" + session.getId());     
    }
    @OnClose
    public void onClose(Session session) {
        System.out.println("onClose::" +  session.getId());
    }
    
    @OnMessage
    public void onMessage(String message, Session session) {
        
        try {
        	
        	switch (message) {
			case "getTimer":
				 session.getBasicRemote().sendText(String.valueOf(System.currentTimeMillis()));
				break;

			default:
				 session.getBasicRemote().sendText("Hello Client " + session.getId() + "!" );
				break;
			}
        	
        	
           
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @OnError
    public void onError(Throwable t) {
        System.out.println("onError::" + t.getMessage());
    }
}
