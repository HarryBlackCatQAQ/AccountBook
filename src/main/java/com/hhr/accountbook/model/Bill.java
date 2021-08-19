package com.hhr.accountbook.model;

import lombok.Data;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @Author: Harry
 * @Date: 2021/8/17 0:00
 * @Version 1.0
 */
@Data
@Entity
@Table(name = "bill")
public class Bill implements Serializable {
    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id",nullable = false)
    private String id;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "spending_type")
    private Long spendingType;

    @Column(name = "payment_method")
    private Long paymentMethod;

    @Column(name = "details")
    private String details;

    @Column(name = "paytime")
    private String payTime;

    @Column(name = "money")
    private Float money;
}
