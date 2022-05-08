package com.benchmarks.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

@Slf4j
public class BigDecimalTest {
    @Test
    public void test() {
        BigDecimal bigDecimal = new BigDecimal("0");
        if (BigDecimal.ZERO.compareTo(bigDecimal) < 0) {
            System.out.println("1");
        }else {
            System.out.println("2");
        }
    }
    @Test
    public void testLong() {
        long counter = 0L;
        counter = counter + 1;
        t(counter);
    }
    private void t(double a) {
        System.out.println(a);
    }

    @Test
    public void testFloor() {
        System.out.println(new BigDecimal("0.01").setScale(1, BigDecimal.ROUND_FLOOR).stripTrailingZeros().toPlainString());
        System.out.println(new BigDecimal("0.09").setScale(1, BigDecimal.ROUND_FLOOR).stripTrailingZeros().toPlainString());
        System.out.println(new BigDecimal("-0.01").setScale(1, BigDecimal.ROUND_FLOOR).stripTrailingZeros().toPlainString());
        System.out.println(new BigDecimal("-0.09").setScale(1, BigDecimal.ROUND_FLOOR).stripTrailingZeros().toPlainString());
    }
}
