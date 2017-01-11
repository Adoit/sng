/*
 *
 *   Copyright (c) 2017, adoit.com. All rights reserved.
 *
 *   Author: Adoit
 *   Created: 1/11/17 8:12 PM
 *   Description:
 *
 */

package com.adoit.sng.zk;

import com.adoit.sng.Configuration;
import com.adoit.sng.Counter;
import com.adoit.sng.CounterException;
import com.adoit.sng.CounterFactory;

import org.I0Itec.zkclient.ZkClient;

public class ZkCounterFactory implements CounterFactory {

    private ZkConfiguration configuration;
    private ZkClient zkClient;

    private ZkCounterFactory(ZkConfiguration configuration) {
        this.configuration = configuration;
        this.zkClient = ZkClientFactory.createClient(this.configuration);
    }

    public static CounterFactory create(Configuration configuration) {
        if (configuration instanceof ZkConfiguration) {
            return new ZkCounterFactory((ZkConfiguration) configuration);
        }

        throw new CounterException("only support zk configuration");
    }

    public Counter createCounter(String counterName) {
        return new ZkCounter(counterName, this.zkClient);
    }

}
