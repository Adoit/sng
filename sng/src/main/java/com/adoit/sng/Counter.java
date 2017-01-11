/*
 *
 *   Copyright (c) 2017, adoit.com. All rights reserved.
 *
 *   Author: Adoit
 *   Created: 1/11/17 8:12 PM
 *   Description:
 *
 */

package com.adoit.sng;

public interface Counter {

    /**
     * 获取当前值
     *
     * @return 当前值
     */
    long get();

    /**
     * 重置默认值,默认值由实现类指定
     */
    void reset();

    /**
     * 重置到指定值
     *
     * @param value 指定值
     */
    void resetTo(long value);

    /**
     * 增加默认步长,并返回增加后的值
     *
     * @return 返回按默认步长增加后的值
     */
    long incrementAndGet();

    /**
     * 增加指定步长, 并返回增加后的值
     *
     * @param inc 指定步长
     * @return 返回按指定步长增加后的值
     */
    long incrementAndGetBy(long inc);

    /**
     *
     */
    void start();

    /**
     * 停止
     */
    void stop();

}
