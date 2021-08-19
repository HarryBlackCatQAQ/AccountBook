package com.hhr.accountbook.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Harry
 * @Date: 2021/8/17 4:38
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BillSpendingTypeVo implements Serializable {
    private String type;
    private Integer typeCount;
}
