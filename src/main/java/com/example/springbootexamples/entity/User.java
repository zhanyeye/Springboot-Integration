package com.example.springbootexamples.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String userName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //禁止User中密码属性序列化
    private String password;
    private int authorityId = 1;
    //当序列化addresses时，user-address的双向依赖导致死循环！
    //在one端取消many关联，在many端持久化组件添加查询语句/方法
    //@OneToMany(mappedBy = "user")
    //private List<Address> addresses;
    @Column(columnDefinition = "timestamp not null default current_timestamp", updatable = false, insertable = false)
    private LocalDateTime insertTime;
    public User(int id) {
        this.id = id;
    }
}
