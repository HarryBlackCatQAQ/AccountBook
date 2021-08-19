package com.hhr.accountbook.dao;

import com.hhr.accountbook.model.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author: Harry
 * @Date: 2021/8/16 23:04
 * @Version 1.0
 */
public interface PaymentMethodDao extends JpaRepository<PaymentMethod, Long> {

    public PaymentMethod getPaymentMethodByMethod(String method);
}
