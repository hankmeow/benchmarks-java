package com.benchmarks.basic;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RandomTest {
    @Test
    public void testRandom() {
        //线程隔离的随机数
        int randomInt = ThreadLocalRandom.current().nextInt(2);
        System.out.println(randomInt);
        //uuid
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        //随机5个大小写字母+数字组合。使用原生random，高并发有性能问题。
        String randomStr = RandomStringUtils.randomAlphanumeric(5);
        //随机6个数字。使用原生random，高并发有性能问题。
        String randomNumStr = RandomStringUtils.randomNumeric(6);


    }

    /**
     * 随机权重树
     * 集合 {"一等奖","二等奖","三等奖","谢谢参与"}
     * 对应的权重值 {1,2,3,94}
     *
     *       一等奖  二等奖     三等奖                           谢谢参与
     * |--1--|---2---|----3-----|--------94---------------------|
     * 0     1       3          6                            100
     *
     *
     * A 1    B 2    C 3
     *
     *[ A  B  B  C  C  C ]
     *
     */
    @Test
    public void testWeightRandom() {

        //模拟有四个奖项，每个奖项的中奖概率不同。
        HashMap<String, Integer> map = new HashMap<>();
        map.put("一等奖", 1);
        map.put("二等奖", 1);
//        map.put("三等奖", 3);
//        map.put("谢谢参与", 94);

        //创建treemap，并初始化总权重为0
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        int total = 0;
        //循环四个奖项，并排除0权重的奖项
        for (Map.Entry<String, Integer> entry : CollectionUtils.emptyIfNull(map.entrySet())) {
            if (entry.getValue() > 0) {
                //将奖项的权重累加到total
                total = total + entry.getValue();
                //以total为key，奖项名为value，添加到treemap中
                treeMap.put(total, entry.getKey());

            }
        }
        //在total内随机一个整数
        int random = ThreadLocalRandom.current().nextInt(total);
        //treemap自有方法，获取第一个大于等于给定值的key，如果没有则返回null
        Map.Entry<Integer, String> entry = treeMap.higherEntry(random);
        String value = entry.getValue();
        System.out.println(value);
    }


}
