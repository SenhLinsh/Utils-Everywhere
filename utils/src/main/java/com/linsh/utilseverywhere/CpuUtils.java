package com.linsh.utilseverywhere;

import android.os.Build;
import android.support.annotation.NonNull;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/03/26
 *    desc   : CPU 相关工具类, 目前用于适配 cpu 结构
 * </pre>
 */
public class CpuUtils {

    public static String getCpuAbi() {
        String cpuAbi = Build.CPU_ABI;
        if (cpuAbi == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                String[] abis = Build.SUPPORTED_ABIS;
                if (abis != null) {
                    cpuAbi = abis[0];
                }
            }
        }
        return cpuAbi;
    }

    public static boolean matchAbi(@NonNull String... abis) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP && Build.SUPPORTED_ABIS != null) {
            for (String supportedAbi : Build.SUPPORTED_ABIS)
                for (String abi : abis)
                    if (supportedAbi.equals(abi)) return true;
        } else {
            for (String abi : abis)
                if (abi != null && (abi.equals(Build.CPU_ABI) || abi.equals(Build.CPU_ABI2))) return true;
        }
        return false;
    }
}
