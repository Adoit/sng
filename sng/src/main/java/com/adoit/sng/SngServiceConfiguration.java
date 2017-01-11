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

public class SngServiceConfiguration {
    private StorageType storageType;
    private Configuration configuration;

    public SngServiceConfiguration(StorageType storageType, Configuration configuration) {
        this.storageType = storageType;
        this.configuration = configuration;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
