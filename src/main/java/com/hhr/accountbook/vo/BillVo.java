package com.hhr.accountbook.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Author: Harry
 * @Date: 2021/8/16 23:58
 * @Version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BillVo implements Serializable {
    private String id;
    private String accountName;
    private String details;
    private String payMethod;
    private String spendingType;
    private String payTime;
    private Float money;
}
