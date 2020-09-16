package com.prayerlaputa.redpacket.mybatis;

import com.prayerlaputa.redpacket.bean.RedPacketInfo;
import com.prayerlaputa.redpacket.mapper.RedPacketInfoMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author chenglong.yu
 * created on 2020/9/16
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisMapperTests {

    @Autowired
    RedPacketInfoMapper redPacketInfoMapper;

    @Test
    public void testInfoMapper() {
        RedPacketInfo redPacketInfo = new RedPacketInfo();
        redPacketInfo.setUid(10086);
        redPacketInfo.setTotalAmount(1000);
        redPacketInfo.setTotalPacket(10);
        redPacketInfo.setRemainingAmount(1000);
        redPacketInfo.setRemainingPacket(10);
        redPacketInfo.setCreateTime(new Date());
        //TODO 分布式情况下，最好使用雪花算法生成
        Long redPacketId = System.currentTimeMillis();
        redPacketInfo.setRedPacketId(redPacketId);
        redPacketInfoMapper.insert(redPacketInfo);
    }
}
