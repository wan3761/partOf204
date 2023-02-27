package com.partof204.partof204website.controller;

import com.partof204.partof204website.bean.UserBean;
import com.partof204.partof204website.config.WebSocketConfig;
import com.partof204.partof204website.service.ArtcService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/chat/socket/",configurator = WebSocketConfig.class)
@Controller
@Component
public class WebSocketController {
    public static int online = 0;
    private static Map<String, WebSocketController> clients = new ConcurrentHashMap<String, WebSocketController>();

    private String username;

    private Session session;

    @GetMapping("/chat")
    public String onUserEnter(HttpServletRequest request){
        return "/chat/room";
    }
    @OnOpen
    public void onOpen(Session session,EndpointConfig config) {
        online++;
        this.session = session;
        HttpSession ses = (HttpSession) config.getUserProperties().get("session");
        UserBean user = (UserBean) ses.getAttribute("user");
        this.username = user.getName();
        clients.put(username, this);

        Set<String> lists = clients.keySet();

        Map<String, Object> map1 = new HashMap<>();
        map1.put("onlineUsers", lists);
        map1.put("messageType", 1);
        map1.put("username", username);
        map1.put("number", online);

        try {
            sendToAll(JSON.toJSONString(map1),username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<String, Object> map2 = new HashMap<>();
        map2.put("messageType", 3);
        map2.put("onlineUsers", lists);
        map2.put("number", online);
        try {
            sendMessageTo(JSON.toJSONString(map2), username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @OnClose
    public void onClose() {
        online--;
        clients.remove(username);
        Map<String, Object> map1 = new HashMap();
        map1.put("messageType", 2);
        //所有在线用户
        map1.put("onlineUsers", clients.keySet());
        //下线用户的用户名
        map1.put("username", username);
        //返回在线人数
        map1.put("number", online);
        //发送信息，所有人，通知谁下线了
        try {
            sendToAll(JSON.toJSONString(map1), username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @OnError
    public void onError(Session session,Throwable error) {
        error.printStackTrace();
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(message);
        String text = jsonObject.getString("message");
        String from = username;
        String to = jsonObject.getString("to");

        Map<String, Object> map1 = new HashMap<>();
        map1.put("messageType", 4);
        map1.put("textMessage", text.replaceAll("<",""));
        map1.put("fromusername", from);

        if(to.equals("All")){
            map1.put("tousername", "所有人");
            try {
                sendToAll(JSON.toJSONString(map1),from);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            map1.put("tousername", to);
            try {
                sendMessageTo(JSON.toJSONString(map1),to);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendMessageTo(String message, String to) throws IOException {
        for (WebSocketController control : clients.values()) {
            if (control.username.equals(to)){
                control.session.getBasicRemote().sendText(message);
                break;
            }
        }
    }

    public void sendToAll(String message, String FromUserName) throws IOException {
        for (WebSocketController item : clients.values()) {
            item.session.getBasicRemote().sendText(message);
        }
    }
    public static synchronized int getOnlineCount() {
        return online;
    }


}
