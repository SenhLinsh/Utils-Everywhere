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
            if (manufacturer.isIt(device)) {
                curManufacturer = manufacturer;
                break;
            }
        }
        return curManufacturer;
    }

    public enum Manufacturer {

        HUAWEI("华为", "HUAWEI"),
        XIAOMI("小米", "Xiaomi"),
        SAMSUNG("三星", "samsung"),
        SONY("索尼", "Sony"),
        MEIZU("魅族", "Meizu"),
        OPPO("OPPO", "OPPO"),
        VIVO("vivo", "vivo"),
        LETV("乐视", "Letv"),
        ZTE("中兴", "ZTE"),
        YULONG("酷派", "Coolpad", "YuLong"),
        LENOVO("联想", "LENOVO"),
        LG("LG", "LG", "LGE"),
        SMARTISAN("锤子", "smartisan"),
        HTC("HTC", "HTC"),
        GIONEE("金立", "GIONEE"),
        _360("奇酷", "360"),
        MOTOROLA("摩托罗拉", "motorola"),
        NUBIA("努比亚", "nubia"),
        ONEPLUS("一加", "OnePlus"),
        OTHER("其他", "other");

        private String mName;
        private String[] mManufacturers;

        Manufacturer(String name, String... manufacturers) {
            mName = name;
            mManufacturers = manufacturers;
        }

        public String getName() {
            return mName;
        }

        public boolean isIt(String manufacturer) {
            for (String manu : mManufacturers) {
                if (manu.equalsIgnoreCase(manufacturer)) {
                    return true;
                }
            }
            return false;
        }
    }
}
