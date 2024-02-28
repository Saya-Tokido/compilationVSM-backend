package com.ljz.compilationVSM.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
    private Long id;
    private String userName;
    private String password;
}
