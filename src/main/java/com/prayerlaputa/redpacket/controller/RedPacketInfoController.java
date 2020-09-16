package com.prayerlaputa.redpacket.controller;

import com.prayerlaputa.redpacket.bean.RedPacketInfo;
import com.prayerlaputa.redpacket.bean.RedPacketRecord;
import com.prayerlaputa.redpacket.request.GetPacketReq;
import com.prayerlaputa.redpacket.request.SaveRedPacketReq;
import com.prayerlaputa.redpacket.service.RedPacketInfoService;
import com.prayerlaputa.redpacket.service.RedPacketRecordService;
import com.prayerlaputa.redpacket.service.RedisService;
import com.prayerlaputa.redpacket.utill.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Random;

/**
 * chenglong.yu
 */
@RestController
@RequestMapping("redpacket")
public class RedPacketInfoController {
    @Autowired
    private RedisService redisService;
    @Autowired
    private RedPacketInfoService redPacketInfoService;
    @Autowired
    private RedPacketRecordService redPacketRecordService;
    private static final String TOTAL_NUM = ":total:num";
    private static final String TOTAL_AMOUNT = ":total:amount";

    /**
     * 发红包：
     * 1. 将红包信息写入到红包表中
     * 2. 将红包金额、数量写入到redis，方便后续抢红包环节
     *
     * @param req 用户信息
     * @return
     */
    @PostMapping("/addPacket")
    public JsonData saveRedPacket(@RequestBody SaveRedPacketReq req) {
        RedPacketInfo redPacketInfo = new RedPacketInfo();
        redPacketInfo.setUid(req.getUid());
        redPacketInfo.setTotalAmount(req.getTotalAmount());
        redPacketInfo.setTotalPacket(req.getTotalNum());
        redPacketInfo.setRemainingAmount(req.getTotalAmount());
        redPacketInfo.setRemainingPacket(req.getTotalNum());
        redPacketInfo.setCreateTime(new Date());
        //TODO 分布式情况下，最好使用雪花算法生成
        Long redPacketId = System.currentTimeMillis();
        redPacketInfo.setRedPacketId(redPacketId);
        redPacketInfoService.insert(redPacketInfo);
        /*
        往redis插入2条记录：
            红包个数
            红包金额

            虽然restTemplate接口支持set(String, Object)
            但实际赋值时发现还是需要转成String传进去，否则可能报错：
            java.lang.ClassCastException: java.lang.Integer cannot be cast to java.lang.String
	at org.springframework.data.redis.serializer.StringRedisSerializer.serialize(StringRedisSerializer.java:36)
	at org.springframework.data.redis.core.AbstractOperations.rawValue(AbstractOperations.java:127)
	at org.springframework.data.redis.core.DefaultValueOperations.set(DefaultValueOperations.java:235)
	at com.prayerlaputa.redpacket.service.RedisService.set(RedisService.java:77)
	at com.prayerlaputa.redpacket.controller.RedPacketInfoController.saveRedPacket(RedPacketInfoController.java:61)
         */
        redisService.set(redPacketId + TOTAL_NUM, req.getTotalNum().toString());
        redisService.set(redPacketId + TOTAL_AMOUNT, req.getTotalAmount().toString());
        return JsonData.buildSuccess();
    }

    /**
     * 抢红包
     * 1. 若红包不存在，直接返回
     * 2. 若红包存在：
     * 2.1 检查红包剩余数量，有效：
     * 2.1.1 红包数量减1
     * 2.1.2 产生随机金额（若是最后一个，则拿到所有，这个规则需要根据业务需求定）
     * 2.1.3 上述信息入库、更新redis
     * 2.2 已抢光，提示
     *
     * 严格来讲此处需要将redis读写、数据库更新都写在一个事务中，
     * 如果是分布式情况下甚至要考虑分布式事务。此处就懒得写了。
     *
     * @param req
     * @return
     */
    @PostMapping("/getPacket")
    public JsonData getRedPacket(@RequestBody GetPacketReq req) {
        Integer randomAmount = 0;
        String redPacketName = req.getRedPacketId() + TOTAL_NUM;
        //红包总金额
        String totalAmountName = req.getRedPacketId() + TOTAL_AMOUNT;
        if (redisService.exists(redPacketName)) {
            Integer num = Integer.valueOf(redisService.get(redPacketName).toString());
            if (num != null && num > 0) {
                //红包数减一
                redisService.decr(redPacketName);

                //拿到红包总金额
                if (redisService.exists(totalAmountName)) {
                    Integer totalAmount = Integer.valueOf(redisService.get(totalAmountName).toString());
                    //红包最大金额
                    Integer maxMoney = totalAmount / Integer.valueOf(num) * 2;
                    Random random = new Random();
                    //随机抢到的红包
                    randomAmount = num == 1 ? totalAmount : random.nextInt(maxMoney);
                    //redis红包金额减指定值
                    redisService.decr(totalAmountName, randomAmount);
                }
                updateRedPacketInDB(req.getUid(), req.getRedPacketId(), randomAmount);
                return JsonData.buildSuccess("抢到红包:" + randomAmount);
            } else {
                //需要将redis中对应记录，避免无用数据堆积
                redisService.remove(redPacketName);
                redisService.remove(totalAmountName);
                return JsonData.buildError("红包被抢完啦");
            }
        } else {
            return JsonData.buildError("红包不存在，请重试");
        }
    }

    /**
     * 更新用户抢到红包记录
     * 事务什么的懒得写的了~.~
     *
     * @param uid         用户id
     * @param redPacketId 红包id
     * @param amount      金额
     */
    private void updateRedPacketInDB(int uid, Long redPacketId, int amount) {
        //添加抢到红包记录
        RedPacketRecord redPacketRecord = new RedPacketRecord();
        redPacketRecord.setUid(uid);
        redPacketRecord.setNickName("陈彦斌");
        redPacketRecord.setImgUrl("https://images.cnblogs.com/cnblogs_com/chenyanbin/1560326/o_qianxun.jpg");
        redPacketRecord.setRedPacketId(redPacketId);
        redPacketRecord.setAmount(amount);
        redPacketRecord.setCreateTime(new Date());
        redPacketRecordService.insert(redPacketRecord);
        //更新发红包的数据，红包总个数-1；总金额减去已抢红包数

        RedPacketInfo redPacketInfo = new RedPacketInfo();
        redPacketInfo.setRedPacketId(redPacketId);
        redPacketInfo.setTotalAmount(amount);
        redPacketInfoService.updateRedPacket(redPacketInfo);
    }
}
