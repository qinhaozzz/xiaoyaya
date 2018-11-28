package com.lim.xyyutil.code.constant;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author qinhao
 */
@Component
@PropertySource("classpath:constant/lim.properties")
@ConfigurationProperties(prefix = "lim")
@Data
public class LimConstant {

    private int age;
    private double total;
    private String name;
    private String school;
    private List<Integer> luckNum;
    private Map<String, String> likePlay;

}
