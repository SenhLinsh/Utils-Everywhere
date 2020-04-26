package com.linsh.utilseverywhere;

import org.junit.Test;

import java.util.Random;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2020/04/26
 *    desc   :
 * </pre>
 */
public class NumberUtilsTest {

    @Test
    public void format() {
        for (int i = 0; i < 100000; i++) {
            double num = RandomUtils.getInt(-10000, 10000) + new Random().nextDouble();
            int count = RandomUtils.nextInt(7);
            String format = String.format("%." + count + "f", num);
            String result1 = NumberUtils.format(num, count, true, true);
            if (!format.equals(result1)) {
                throw new RuntimeException("num: " + num + ", count: " + count + ", format: " + format + ", result1: " + result1);
            }
            String result2 = NumberUtils.format(num, count, true, false);
            if (!format.matches(result2 + "\\.*0*")) {
                throw new RuntimeException("num: " + num + ", count: " + count + ", format: " + format + ", result2: " + result2);
            }
            String result3 = NumberUtils.format(num, count, false, true);
            if (!format.equals(result3) && !format.equals(String.format("%." + count + "f", Double.parseDouble(result3) + Math.pow(0.1, count) * (num < 0 ? -1 : 1)))) {
                throw new RuntimeException("num: " + num + ", count: " + count + ", format: " + format + ", result3: " + result3);
            }
            String result4 = NumberUtils.format(num, count, false, false);
            if (!format.equals(String.format("%." + count + "f", Double.parseDouble(result4)))
                    && !format.equals(String.format("%." + count + "f", Double.parseDouble(result4) + Math.pow(0.1, count) * (num < 0 ? -1 : 1)))) {
                throw new RuntimeException("num: " + num + ", count: " + count + ", format: " + format + ", result3: " + result4);
            }
        }
    }
}