package com.example.springbootexamples;

import com.example.springbootexamples.repository.impl.CustomizedRepositoryImp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomizedRepositoryImp.class)  //修改springboot启动注解，添加自定义的JPA实现类
public class SpringbootExamplesApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootExamplesApplication.class, args);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {  //添加密码编码组件到容器
        return new BCryptPasswordEncoder();
    }


}
