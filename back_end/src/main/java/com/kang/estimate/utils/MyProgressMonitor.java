package com.kang.estimate.utils;

import com.jcraft.jsch.SftpProgressMonitor;

public class MyProgressMonitor implements SftpProgressMonitor {
    private long transfered;
    @Override
    public void init(int op, String src, String dest, long max) {
        System.out.println("开始传输.");
    }
    /**
     * 当每次传输了一个数据块后，调用count方法，count方法的参数为这一次传输的数据块大小
     */
    @Override
    public boolean count(long count) {
        transfered=transfered+count;
        System.out.println("已传输 "+transfered+" bytes数据");
        return true;
    }
    @Override
    public void end() {
        System.out.println("结束传输.");
    }
}