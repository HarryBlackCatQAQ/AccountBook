package com.hhr.accountbook.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: Harry
 * @Date: 2021/8/16 17:39
 * @Version 1.0
 */
@Data
@Entity
@Table(name = "account")
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "account_name")
    private String  accountName;

    @Column(name = "password")
    private String  password;
}
