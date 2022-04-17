package com.example.interback_be.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8000" , allowCredentials = "true")
public class VideoRoomController {

    private final SimpMessagingTemplate template;

    @MessageMapping("/video/caller")
    @SendTo("/sub/video/caller")
    public JSONObject caller(JSONObject ob){

        log.info(ob.toJSONString());

        // caller의 정보를 json 소켓으로 쏴준다.
        JSONObject data = new JSONObject();
        data.put("from", ob.get("from"));
        data.put("signal", ob.get("signal"));

        return data;
    }

    @MessageMapping("/video/answerCall")
    @SendTo("/sub/video/acceptCall")
    public JSONObject answerCall(JSONObject ob){

        log.info(ob.toJSONString());

        // caller의 정보를 json 소켓으로 쏴준다.
        JSONObject data = new JSONObject();
        data.put("signal", ob.get("signal"));
        data.put("from", ob.get("from"));

        return data;
    }
}
