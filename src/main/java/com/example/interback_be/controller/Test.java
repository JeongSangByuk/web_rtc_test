package com.example.interback_be.controller;

import com.example.interback_be.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = Constants.API_URL, allowCredentials = "true")
public class Test {

    @ResponseBody
    @GetMapping("/test")
    public String test(){

        log.info("qweqwe");
        return "qwe";
    }

    @ResponseBody
    @PostMapping("/audio-test")
    public String audioTest(@RequestBody Map<String, String> map) throws IOException {

        String base64Audio = map.get("base64data");
        log.info(base64Audio);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedByte = decoder.decode(base64Audio.split(",")[1]);
        FileOutputStream fos = new FileOutputStream("MyAudio.webm");
        fos.write(decodedByte);
        fos.close();

        return "aw";
    }
}
