package com.hhr.accountbook.dao;

import com.hhr.accountbook.model.SpendingType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Harry
 * @Date: 2021/8/16 23:05
 * @Version 1.0
 */
public interface SpendingTypeDao extends JpaRepository<SpendingType, Long> {

    public SpendingType getSpendingTypeByType(String type);
}
