package com.hhr.accountbook.util;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * @Author: Harry
 * @Date: 2021/8/16 18:22
 * @Version 1.0
 */

@Component
public class StringEncoderUtil{

    public String encode(String encodeString) {
        return DigestUtils.md5DigestAsHex(encodeString.getBytes());
    }

    public boolean matches(String rawString, String encodeString) {
        return encodeString.equals(DigestUtils.md5DigestAsHex(rawString.getBytes()));
    }
}
