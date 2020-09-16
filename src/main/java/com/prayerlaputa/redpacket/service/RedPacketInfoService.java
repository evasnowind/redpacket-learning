package com.prayerlaputa.redpacket.service;

import com.prayerlaputa.redpacket.bean.RedPacketInfo;

public interface RedPacketInfoService {
    void insert(RedPacketInfo redPacketInfo);
    void updateRedPacket(RedPacketInfo redPacketInfo);
}
