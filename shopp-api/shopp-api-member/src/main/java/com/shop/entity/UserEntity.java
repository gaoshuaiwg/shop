package com.shop.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author admin
 */
@Data
public class UserEntity{
    private Integer id;
    private String username;
    private String password;
    private String phone;
    private String email;
    private Date created;
    private Date updated;
}
