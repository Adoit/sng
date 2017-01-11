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

import com.adoit.sng.zk.ZkCounterFactory;

public class CounterFactories {
    public static CounterFactory createFactory(StorageType storageType, Configuration configuration) {
        switch (storageType) {
            case ZOOKEEPER:
                return ZkCounterFactory.create(configuration);
            default:
                throw new IllegalArgumentException(
                        String.format("not supported storage type: [%s]", storageType.toString()));
        }
    }
}
