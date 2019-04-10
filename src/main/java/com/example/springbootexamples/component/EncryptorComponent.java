package com.example.springbootexamples.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Slf4j
@Component
public class EncryptorComponent {
    @Value("${my.password}")
    private String password;
    @Value("${my.salt}")
    private String salt;


    //SpringMVC默认基于Jackson实现序列化/反序列化
    //SpringMVC自动注入Jackson的序列化ObjectMapper对象到容器
    //ObjectMapper类是Jackson库的主要类。它提供一些功能将转换成Java对象匹配JSON结构，反之亦然
    @Autowired
    private ObjectMapper mapper;

    public String encrypt(Map payload) { //加密
        try {
            String json = mapper.writeValueAsString(payload);
            return Encryptors.text(password,salt).encrypt(json);
        } catch (JsonProcessingException e) {

        }
        return null;
    }

    public Map<String, Object> decrypt(String encryptString) {
        try {
            String json = Encryptors.text(password, salt).decrypt(encryptString);
            return mapper.readValue(json, Map.class);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "未登录！");
        }
    }
}
