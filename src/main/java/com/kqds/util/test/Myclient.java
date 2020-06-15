package com.kqds.util.test;

import java.io.IOException;
import java.io.PrintStream;
import javax.websocket.ClientEndpoint;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;

@ClientEndpoint
public class Myclient
{
  @OnOpen
  public void open(Session session)
  {
    System.out.println("Client WebSocket is opening...");
    System.out.println("Connected to endpoint:" + session.getBasicRemote());
    try
    {
      session.getBasicRemote().sendText("Hello");
    }
    catch (IOException localIOException) {}
  }
  
  @OnMessage
  public void onMessage(String message)
  {
    System.out.println("Server send message: " + message);
  }
  
  @OnClose
  public void onClose()
  {
    System.out.println("Websocket closed");
  }
}
