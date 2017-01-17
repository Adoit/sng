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

public class SngServiceSetting {
    private String sequenceName;
    private StorageType storageType;
    private Configuration configuration;

    public SngServiceSetting(String sequenceName, StorageType storageType) {
        this(sequenceName, storageType, null);
    }

    public SngServiceSetting(String sequenceName, StorageType storageType, Configuration configuration) {
        this.sequenceName = sequenceName;
        this.storageType = storageType;
        this.configuration = configuration;
    }

    public StorageType getStorageType() {
        return storageType;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public String getSequenceName() {
        return sequenceName;
    }
}
