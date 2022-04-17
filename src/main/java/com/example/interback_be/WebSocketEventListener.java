package com.example.interback_be;

import com.example.interback_be.controller.VideoRoomController;
import com.example.interback_be.dto.TestSession;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.ArrayList;

//@Slf4j
//@Component
//@RequiredArgsConstructor
//public class WebSocketEventListener {
//
//    private final ArrayList<TestSession> sessionIdList;
//
//
//}
