package com.lweishi.repair;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @ClassName PhoneRepairTest
 * @Description TODO
 * @Author zzm
 * @Data 2020/9/17 17:54
 * @Version 1.0
 */
@SpringBootTest
public class PhoneRepairApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void contentLoad() {
        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);
    }

}
