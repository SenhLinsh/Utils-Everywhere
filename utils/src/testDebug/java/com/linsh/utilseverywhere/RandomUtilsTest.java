package com.linsh.utilseverywhere;

import com.google.common.collect.Range;
import com.google.common.truth.Truth;
import com.linsh.utilseverywhere.util.ThrowableRunnable;
import com.linsh.utilseverywhere.util.TruthEx;

import org.junit.Test;

/**
 * Created by Senh Linsh on 17/7/6.
 */
public class RandomUtilsTest {

    @Test
    public void getBoolean() {
        for (int i = 0; RandomUtils.getBoolean(); i++) {
            Truth.assertThat(i).isLessThan(1000);
        }
        for (int i = 0; !RandomUtils.getBoolean(); i++) {
            Truth.assertThat(i).isLessThan(1000);
        }
    }

    @Test
    public void getInt() throws Exception {
        TruthEx.assertThat(new ThrowableRunnable() {
            @Override
            public void run() {
                RandomUtils.getInt(-1);
            }
        }).isInstanceOf(IllegalArgumentException.class);
        TruthEx.assertThat(new ThrowableRunnable() {
            @Override
            public void run() {
                RandomUtils.getInt(-2233);
            }
        }).isInstanceOf(IllegalArgumentException.class);
        Truth.assertThat(RandomUtils.getInt(0)).isEqualTo(0);
        Truth.assertThat(RandomUtils.getInt(3)).isAnyOf(0, 1, 2, 3);

        Truth.assertThat(RandomUtils.getInt(2, 2)).isEqualTo(2);
        Truth.assertThat(RandomUtils.getInt(1, 3)).isAnyOf(1, 2, 3);
        Truth.assertThat(RandomUtils.getInt(-2, 0)).isAnyOf(-2, -1, 0);
    }

    @Test
    public void nextInt() {
        TruthEx.assertThat(new ThrowableRunnable() {
            @Override
            public void run() {
                RandomUtils.nextInt(0);
            }
        }).isInstanceOf(IllegalArgumentException.class);
        TruthEx.assertThat(new ThrowableRunnable() {
            @Override
            public void run() {
                RandomUtils.nextInt(-818393);
            }
        }).isInstanceOf(IllegalArgumentException.class);
        Truth.assertThat(RandomUtils.nextInt(1)).isEqualTo(0);
        Truth.assertThat(RandomUtils.nextInt(2)).isAnyOf(0, 1);
        Truth.assertThat(RandomUtils.nextInt(3)).isAnyOf(0, 1, 2);
        Truth.assertThat(RandomUtils.nextInt(1000)).isIn(Range.openClosed(0, 999));
    }

    @Test
    public void getNumbers() throws Exception {
        TruthEx.assertThat(new ThrowableRunnable() {
            @Override
            public void run() {
                RandomUtils.getNumbers(-1);
            }
        }).isInstanceOf(IllegalArgumentException.class);
        Truth.assertThat(RandomUtils.getNumbers(0)).matches("");
        Truth.assertThat(RandomUtils.getNumbers(1)).matches("\\d");
        Truth.assertThat(RandomUtils.getNumbers(3)).matches("\\d{3}");
        Truth.assertThat(RandomUtils.getNumbers(100)).matches("\\d{100}");
    }

    @Test
    public void getLowerCaseLetters() {
        TruthEx.assertThat(new ThrowableRunnable() {
            @Override
            public void run() {
                RandomUtils.getLowerCaseLetters(-1);
            }
        }).isInstanceOf(IllegalArgumentException.class);
        Truth.assertThat(RandomUtils.getLowerCaseLetters(0)).isEqualTo("");
        Truth.assertThat(RandomUtils.getLowerCaseLetters(1)).matches("[a-z]");
        Truth.assertThat(RandomUtils.getLowerCaseLetters(100)).matches("[a-z]{100}");
    }

    @Test
    public void getUpperCaseLetters() {
        TruthEx.assertThat(new ThrowableRunnable() {
            @Override
            public void run() {
                RandomUtils.getUpperCaseLetters(-87214321);
            }
        }).isInstanceOf(IllegalArgumentException.class);
        Truth.assertThat(RandomUtils.getUpperCaseLetters(0)).isEqualTo("");
        Truth.assertThat(RandomUtils.getUpperCaseLetters(1)).matches("[A-Z]");
        Truth.assertThat(RandomUtils.getUpperCaseLetters(1245)).matches("[A-Z]{1245}");
    }

    @Test
    public void getLetters() {
        TruthEx.assertThat(new ThrowableRunnable() {
            @Override
            public void run() {
                RandomUtils.getLetters(-3243);
            }
        }).isInstanceOf(IllegalArgumentException.class);
        Truth.assertThat(RandomUtils.getLetters(0)).isEqualTo("");
        Truth.assertThat(RandomUtils.getLetters(1)).matches("[a-zA-Z]");
        Truth.assertThat(RandomUtils.getLetters(332)).matches("[a-zA-Z]{332}");
    }

    @Test
    public void getNumbersAndLetters() {
        TruthEx.assertThat(new ThrowableRunnable() {
            @Override
            public void run() {
                RandomUtils.getNumbersAndLetters(-246);
            }
        }).isInstanceOf(IllegalArgumentException.class);
        Truth.assertThat(RandomUtils.getNumbersAndLetters(0)).isEqualTo("");
        Truth.assertThat(RandomUtils.getNumbersAndLetters(1)).matches("[0-9a-zA-Z]");
        Truth.assertThat(RandomUtils.getNumbersAndLetters(125)).matches("[0-9a-zA-Z]{125}");
    }

    @Test
    public void getRandom() {
        TruthEx.assertThat(new ThrowableRunnable() {
            @Override
            public void run() {
                RandomUtils.getRandom("4231432", -2393);
            }
        }).isInstanceOf(IllegalArgumentException.class);
        TruthEx.assertThat(new ThrowableRunnable() {
            @Override
            public void run() {
                RandomUtils.getRandom("", 123);
            }
        }).isInstanceOf(IllegalArgumentException.class);
        TruthEx.assertThat(new ThrowableRunnable() {
            @Override
            public void run() {
                RandomUtils.getRandom((String) null, 123);
            }
        }).isInstanceOf(NullPointerException.class);
        Truth.assertThat(RandomUtils.getRandom("klsdjahgalsgd", 0)).isEqualTo("");
        Truth.assertThat(RandomUtils.getRandom("djsagh029-", 1)).matches("[djsagh029-]");
        Truth.assertThat(RandomUtils.getRandom("0", 125)).matches("[0]{125}");
    }
}