package com.prayerlaputa.redpacket.controller;

import com.prayerlaputa.redpacket.service.BloomFilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenglong.yu
 * created on 2020/9/16
 */
@RestController
@RequestMapping("bloom")
public class BloomFilterController {

    @Autowired
    private BloomFilterService bloomFilterService;


    @GetMapping("/userExist")
    public boolean ifUserExist(Integer id) {
        return bloomFilterService.ifUserExist(id);
    }

    @GetMapping("/userExistInRedis")
    public boolean ifUserExistInRedis(Integer id) {
        return bloomFilterService.ifUserExistsInRedis(id);
    }
}
