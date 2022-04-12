package com.example.interback_be.config;

import com.example.interback_be.DTO.User;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Component
public class SocketHandler extends TextWebSocketHandler {

    // 연결된 세션 저장.
    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    // socket cl
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        log.info("connected");
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        String msg = message.getPayload();
        JSONObject ob = jsonToObjectParser(msg);
        log.info(ob.toJSONString());
        switch (ob.get("type").toString()) {
            case "caller":

                for (WebSocketSession webSocketSession : sessions) {
                    JSONObject data = new JSONObject();
                    data.put("type", "caller");
                    data.put("from", ob.get("from"));
                    data.put("signal", ob.get("signal"));

                    webSocketSession.sendMessage(new TextMessage(data.toJSONString()));
                }
                break;

            case "answerCall":

                for (WebSocketSession webSocketSession : sessions) {
                    JSONObject data = new JSONObject();
                    data.put("type", "acceptCall");
                    data.put("signal", ob.get("signal"));

                    webSocketSession.sendMessage(new TextMessage(data.toJSONString()));
                }
                break;

            default:

                log.warn(ob.toJSONString());
                break;
        }


    }

    // json parser
    private static JSONObject jsonToObjectParser(String jsonStr) {
        JSONParser parser = new JSONParser();
        JSONObject obj = null;
        try {
            obj = (JSONObject) parser.parse(jsonStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return obj;
    }

}
