package com.linsh.utilseverywhere.entity;

/**
 * 执行 Shell 命令返回的命令结果
 */
public class CommandResult {
    /**
     * 结果码
     **/
    public int result;
    /**
     * 成功信息
     **/
    public String successMsg;
    /**
     * 错误信息
     **/
    public String errorMsg;

    public CommandResult(final int result, final String successMsg, final String errorMsg) {
        this.result = result;
        this.successMsg = successMsg;
        this.errorMsg = errorMsg;
    }
}