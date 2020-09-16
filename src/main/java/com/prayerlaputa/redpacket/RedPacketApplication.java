package com.prayerlaputa.redpacket;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author chenglong.yu
 * created on 2020/9/15
 */
@SpringBootApplication
@MapperScan("com.prayerlaputa.redpacket.mapper")
public class RedPacketApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedPacketApplication.class, args);
    }
}
