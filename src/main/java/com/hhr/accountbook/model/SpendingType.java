package com.hhr.accountbook.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: Harry
 * @Date: 2021/8/16 23:03
 * @Version 1.0
 */
@Data
@Entity
@Table(name = "spending_type")
public class SpendingType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;
}
