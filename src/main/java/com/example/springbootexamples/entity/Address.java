package com.example.springbootexamples.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String detail;
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY)   //声明为延迟加载
    private User user;
    @Column(columnDefinition = "timestamp not null default current_timestamp", updatable = false, insertable = false)
    private LocalDateTime insertTime;
}
