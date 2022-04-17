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
    @MessageMapping("/video/join-room")
    @SendTo("/sub/video/all-users")
    private ArrayList<TestSession> joinRoom(@Header("simpSessionId") String sessionId, JSONObject ob){

        // 현재 들어온 세션 저장.
        sessionIdList.add(new TestSession((String) ob.get("from"),sessionId));

        return sessionIdList;
    }

    @MessageMapping("/video/caller")
    @SendTo("/sub/video/caller")
    private Map<String, Object> caller(JSONObject ob){

        log.info(ob.toJSONString());

        // caller의 정보를 소켓으로 쏴준다.
        Map<String, Object> data= new HashMap<>();
        data.put("from", ob.get("from"));
        data.put("signal", ob.get("signal"));

        return data;
    }

    @MessageMapping("/video/answer-call")
    @SendTo("/sub/video/accept-call")
    private Map<String, Object> answerCall(JSONObject ob){

        log.info(ob.toJSONString());

        // accepter의 정보를 소켓으로 쏴준다.
        Map<String, Object> data = new JSONObject();
        data.put("signal", ob.get("signal"));
        data.put("from", ob.get("from"));

        return data;
    }

    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {

    }

    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        sessionIdList.removeIf(s -> s.getSessionId().equals(event.getSessionId()));
    }

}
