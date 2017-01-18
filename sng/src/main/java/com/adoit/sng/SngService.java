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

import com.google.common.base.Preconditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SngService {
    private static final Logger LOG = LoggerFactory.getLogger(SngService.class);

    private static Map<String, SngService> instanceMap = new ConcurrentHashMap<String, SngService>();
    private static Map<String, Counter> counterMap = new ConcurrentHashMap<String, Counter>();

    private SngServiceSetting serviceSetting;
    private CounterFactory counterFactory;

    private SngService(SngServiceSetting serviceSetting) {
        this.serviceSetting = serviceSetting;
    }

    public static SngService getInstance(SngServiceSetting serviceSetting) {
        SngService service = instanceMap.get(serviceSetting.getSequenceName());
        if (service == null) {
            service = new SngService(serviceSetting);
            instanceMap.put(serviceSetting.getSequenceName(), service);
        }

        return service;
    }

    public long next(String sequenceName) {
        return getCounter(sequenceName).incrementAndGet();
    }

    public long next(String sequenceName, long inc) {
        return getCounter(sequenceName).incrementAndGetBy(inc);
    }

    public void start() {
        Preconditions.checkNotNull(serviceSetting, "serviceSetting can't be null");
        this.counterFactory = CounterFactories.createFactory(
                serviceSetting.getStorageType(), serviceSetting.getConfiguration());
    }

    public void stop() {
        for (Counter counter : counterMap.values()) {
            counter.stop();
        }
    }

    public SngServiceSetting getServiceSetting() {
        return serviceSetting;
    }

    private String getFullSequenceName(String sequenceName) {
        return serviceSetting.getNamespace() + '/' + sequenceName;
    }

    private Counter getCounter(String sequenceName) {
        Preconditions.checkNotNull(counterFactory, "counter factory can't be null, you may not start service.");

        String counterName = this.getFullSequenceName(sequenceName);
        Counter counter = counterMap.get(counterName);
        if (counter == null) {
            counter = counterFactory.createCounter(counterName);
            counter.start();
            counterMap.put(counterName, counter);
        }
        return counter;
    }
}
