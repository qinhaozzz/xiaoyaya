package com.lim.xyyutil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class XyyUtilApplicationTests {

    @Autowired
    private ScanTestBean bean;

    @Test
    public void contextLoads() {
    }

}
