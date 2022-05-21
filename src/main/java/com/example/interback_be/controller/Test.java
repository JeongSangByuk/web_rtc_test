package com.example.interback_be.controller;

import com.example.interback_be.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {Constants.API_URL, Constants.API_URL_DEV}, allowCredentials = "true")
public class Test {

    @ResponseBody
    @GetMapping("/test")
    public String test(){

        log.info("qweqwe");
        return "qwe";
    }
}
