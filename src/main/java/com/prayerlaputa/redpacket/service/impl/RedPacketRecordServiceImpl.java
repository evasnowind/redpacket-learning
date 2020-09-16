package com.prayerlaputa.redpacket.service.impl;

import com.prayerlaputa.redpacket.bean.RedPacketRecord;
import com.prayerlaputa.redpacket.mapper.RedPacketRecordMapper;
import com.prayerlaputa.redpacket.service.RedPacketRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * chenglong.yu
 */
@Service
public class RedPacketRecordServiceImpl implements RedPacketRecordService {
    @Autowired
    private RedPacketRecordMapper redPacketRecordMapper;
    @Override
    public void insert(RedPacketRecord redPacketRecord) {
        redPacketRecordMapper.insert(redPacketRecord);
    }
}
