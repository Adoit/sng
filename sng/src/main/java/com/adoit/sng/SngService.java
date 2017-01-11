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

    private String sequencePrefix;
    private SngServiceConfiguration configuration;
    private CounterFactory counterFactory;

    private SngService(String sequencePrefix) {
        this.sequencePrefix = sequencePrefix;
    }

    public static SngService getInstance(String sequencePrefix) {
        SngService service = instanceMap.get(sequencePrefix);
        if (service == null) {
            service = new SngService(sequencePrefix);
            instanceMap.put(sequencePrefix, service);
        }

        return service;
    }

    public long next(String sequenceName) {
        return getCounter(sequenceName).incrementAndGet();
    }

    public long next(String sequenceName, long inc) {
        return getCounter(sequenceName).incrementAndGetBy(inc);
    }

    synchronized public void start() {
        Preconditions.checkNotNull(configuration, "configuration can't be null");
        this.counterFactory = CounterFactories.createFactory(
                configuration.getStorageType(), configuration.getConfiguration());
    }

    synchronized public void stop() {
        for (Counter counter : counterMap.values()) {
            counter.stop();
        }
    }

    public SngServiceConfiguration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(SngServiceConfiguration configuration) {
        this.configuration = configuration;
    }

    private String getFullSequenceName(String sequenceName) {
        return this.sequencePrefix + '/' + sequenceName;
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
