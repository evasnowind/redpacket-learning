package com.prayerlaputa.redpacket.service.impl;

import com.prayerlaputa.redpacket.bean.RedPacketInfo;
import com.prayerlaputa.redpacket.mapper.RedPacketInfoMapper;
import com.prayerlaputa.redpacket.service.RedPacketInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * chenglong.yu
 */
@Service
public class RedPacketInfoServiceImpl implements RedPacketInfoService {

    @Autowired
    private RedPacketInfoMapper redPacketInfoMapper;

    /**
     * 添加红包
     *
     * @param redPacketInfo
     */
    @Override
    public void insert(RedPacketInfo redPacketInfo) {
        redPacketInfoMapper.insert(redPacketInfo);
    }

    @Override
    public void updateRedPacket(RedPacketInfo redPacketInfo) {
        redPacketInfoMapper.updateRedPacket(redPacketInfo);
    }
}
