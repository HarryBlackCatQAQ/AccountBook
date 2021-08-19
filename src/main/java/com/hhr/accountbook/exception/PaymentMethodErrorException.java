package com.hhr.accountbook.exception;

/**
 * @Author: Harry
 * @Date: 2021/8/19 18:47
 * @Version 1.0
 */
public class PaymentMethodErrorException extends Exception{
    public PaymentMethodErrorException(String message) {
        super(message);
    }
}
