package com.prayerlaputa.redpacket.service;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.prayerlaputa.redpacket.bean.SysUser;
import com.prayerlaputa.redpacket.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author chenglong.yu
 * created on 2020/9/16
 */
@Service
public class BloomFilterService {

    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisService redisService;

    private BloomFilter<Integer> bloomFilter;

    public static final String USER_ID_REDIS_BLOOMFILTER_KEY = "user:bloomfilter";

    @PostConstruct
    public void initBloomFilter() {
        List<SysUser> sysUserList = sysUserMapper.listAllUserInfo();
        if (CollectionUtils.isEmpty(sysUserList)) {
            return;
        }
        /*
        布隆过滤器在使用时，预期插入数量这个值很关键，此处简化，暂时用
        表中数据量作为预期插入值。
         */
        bloomFilter = BloomFilter.create(Funnels.integerFunnel(), sysUserList.size());
        for (SysUser user : sysUserList) {
            bloomFilter.put(user.getId());
        }
    }

    public boolean ifUserExist(Integer userId) {
        return bloomFilter.mightContain(userId);
    }

    @PostConstruct
    public void initRedisBloomFilter() {
        List<SysUser> sysUserList = sysUserMapper.listAllUserInfo();
        if (CollectionUtils.isEmpty(sysUserList)) {
            return;
        }
        for (SysUser user : sysUserList) {
            redisService.bloomFilterAdd(USER_ID_REDIS_BLOOMFILTER_KEY, user.getId());
        }
    }

    public boolean ifUserExistsInRedis(Integer userId) {
        return redisService.bloomFilterExists(USER_ID_REDIS_BLOOMFILTER_KEY, userId);
    }
}
