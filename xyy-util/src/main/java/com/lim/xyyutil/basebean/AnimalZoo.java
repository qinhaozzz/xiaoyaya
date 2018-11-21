package com.lim.xyyutil.basebean;

import com.lim.xyyutil.annotation.AnimalName;
import lombok.Data;

import java.util.Date;

/**
 * @author qinhao
 */
@Data
public class AnimalZoo {

    @AnimalName(value = "Spike")
    private String dog;
    @AnimalName(value = "Tom")
    private String cat;
    @AnimalName(value = "Simba")
    private String lion;
    @AnimalName(value = "Xiaohu")
    private String tiger;
    private Date createDate;
}
