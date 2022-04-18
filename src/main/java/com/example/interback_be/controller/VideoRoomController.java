package com.example.interback_be.controller;

import com.example.interback_be.dto.TestSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8000" , allowCredentials = "true")
public class VideoRoomController {

    // 테스트용 세션 리스트.
    private final ArrayList<TestSession> sessionIdList = new ArrayList<>();


    // 실시간으로 들어온 세션 감지하여 전체 세션 리스트 반환
    @MessageMapping("/video/joined-room-info")
    @SendTo("/sub/video/joined-room-info")
    private ArrayList<TestSession> joinRoom(@Header("simpSessionId") String sessionId, JSONObject ob){

        // 현재 들어온 세션 저장.
        sessionIdList.add(new TestSession((String) ob.get("from"),sessionId));

        return sessionIdList;
    }

    @MessageMapping("/video/caller-info")
    @SendTo("/sub/video/caller-info")
    private Map<String, Object> caller(JSONObject ob){

        log.info(ob.toJSONString());

        // caller의 정보를 소켓으로 쏴준다.
        Map<String, Object> data= new HashMap<>();
        data.put("toCall",ob.get("toCall"));
        data.put("from", ob.get("from"));
        data.put("signal", ob.get("signal"));

        return data;
    }

    @MessageMapping("/video/callee-info")
    @SendTo("/sub/video/callee-info")
    private Map<String, Object> answerCall(JSONObject ob){

        log.info(ob.toJSONString());

        // accepter의 정보를 소켓으로 쏴준다.
        Map<String, Object> data = new HashMap<>();
        data.put("signal", ob.get("signal"));
        data.put("from", ob.get("from"));
        data.put("to", ob.get("to"));

        return data;
    }

    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {

    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {

        // 세션 close인 경우 세션 리스트에서 빼준다.
        sessionIdList.removeIf(s -> s.getSessionId().equals(event.getSessionId()));
    }

}
