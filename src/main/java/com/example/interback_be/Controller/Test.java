package com.example.interback_be.Controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class Test {

    @ResponseBody
    @GetMapping("/test")
    public String test(){

        log.info("qweqwe");
        return "qwe";
    }
}
