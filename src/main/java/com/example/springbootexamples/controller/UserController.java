package com.example.springbootexamples.controller;

import com.example.springbootexamples.component.EncryptorComponent;
import com.example.springbootexamples.entity.Address;
import com.example.springbootexamples.entity.User;
import com.example.springbootexamples.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

/*
创建controller组件，注入业务逻辑/密码编码/加密解密组件
添加实现register()/login()/postAddress()/getAddresses()方法
login()，实现登录成功在header添加token
postAddress()，从requestattribute中注入用户信息
 */

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;                 //业务逻辑组件
    @Autowired
    private PasswordEncoder passwordEncoder;         //密码编码组件
    @Autowired
    private EncryptorComponent encryptorComponent;   //加密解密组件

    @PostMapping("/register")
    public Map register(@RequestBody User user) { //@RequestBody，指定controller方法参数绑定到请求体，自动基于jackson将请求数据反序列化为Java对象
        user.setPassword(passwordEncoder.encode(user.getPassword()));   //取出密码编码后后再放回去
        userService.addUser(user);                                      //密码编码后的user存入数据库
        return Map.of("user", user);
    }

    @PostMapping("/login")
    public void login(@RequestBody User user, HttpServletResponse response) {
        Optional.ofNullable(userService.getUser(user.getUserName()))
                .ifPresentOrElse(u -> {
                    if (!passwordEncoder.matches(user.getPassword(),u.getPassword())) {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名或密码错误！");
                    } else { //登录成功将 uid 和 AuthorityId 封装成 Map, 加密之后塞进 response 的 Header 当中
                        Map map = Map.of("uid", u.getId(), "AuthorityId", u.getAuthorityId());
                        String token = encryptorComponent.encrypt(map);
                        response.setHeader("Authorization", token);
                    }
                }, () -> {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名或密码错误！");
                });
    }

    @PostMapping("/users/{uid}/addresses")
    public Map postAddress(@RequestBody Address address, @RequestAttribute int uid) {
        address.setUser(new User(uid));
        userService.addAddress(address);
        return Map.of("address", address);
    }

    @GetMapping("/users/{uid}/address")
    public Map getAddresses(@RequestAttribute int uid) {
        return Map.of("Addresses", userService.ListAddresses(uid));
    }

    @PatchMapping("/users/{uid}/address/{aid}")
    public Map patchAddress(@RequestBody Address address) {
        return Map.of("address", userService.updateAddress(address));
    }
}
