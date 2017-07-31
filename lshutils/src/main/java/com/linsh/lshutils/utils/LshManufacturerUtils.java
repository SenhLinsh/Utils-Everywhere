package com.linsh.lshutils.utils;

import android.os.Build;

/**
 * Created by Senh Linsh on 17/7/27.
 */

public class LshManufacturerUtils {


    public Manufacturer getManufacturer() {
        String device = Build.MANUFACTURER;
        Manufacturer curManufacturer = Manufacturer.OTHER;
        for (Manufacturer manufacturer : Manufacturer.values()) {
            if (manufacturer.getName().equalsIgnoreCase(device)) {
                curManufacturer = manufacturer;
                break;
            }
        }
        return curManufacturer;
    }

    public enum Manufacturer {

        HUAWEI("华为", "HUAWEI"),
        MEIZU("魅族", "Meizu"),
        XIAOMI("小米", "Xiaomi"),
        SONY("索尼", "Sony"),
        SAMSUNG("三星", "samsung"),
        LETV("乐视", "Letv"),
        ZTE("中兴", "ZTE"),
        YULONG("酷派", "YuLong"),
        LENOVO("联想", "LENOVO"),
        LG("LG", "LG"),
        OPPO("oppo", "OPPO"),
        VIVO("vivo", "vivo"),
        SMARTISAN("锤子", "smartisan"),
        OTHER("其他", "other");

        private String mName;
        private String mManufacturer;

        Manufacturer(String name, String manufacturer) {
            mName = name;
            mManufacturer = manufacturer;
        }

        public String getName() {
            return mName;
        }

        public String getManufacturer() {
            return mManufacturer;
        }
    }
}
