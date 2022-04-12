package com.example.interback_be.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.json.simple.JSONObject;

@Getter
@Setter
@AllArgsConstructor
public class User {

    private Long callee;

    private String name;

    private JSONObject signal;
}
