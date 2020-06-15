package com.kqds.util.test;

import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

public class TestSocket
{
  public Session session;
  
  protected void start(String message)
  {
    WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    String uri = "";
    if (message.contains("HUDH")) {
      uri = "ws://192.168.1.138:10001/base/WSwebsocket";
    } else if (message.contains("HUDX")) {
      uri = "ws://192.168.1.138:11111/base/WSwebsocket";
    }
    System.out.println("Connecting to" + uri);
    try
    {
      if (!message.equals("无参")) {
        this.session = container.connectToServer(Myclient.class, URI.create(uri));
      }
    }
    catch (DeploymentException e)
    {
      e.printStackTrace();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public static void main(String[] args)
  {
    TestSocket client = new TestSocket();
    
    String message = "无参";
    if ((args != null) && (args.length > 0)) {
      message = args[0];
    }
    client.start(message);
    try
    {
      if (!message.equals("无参"))
      {
        client.session.getBasicRemote().sendText(message);
        client.session.close();
      }
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
