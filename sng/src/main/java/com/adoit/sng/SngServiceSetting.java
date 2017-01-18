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
    private static final String DEFAULT_NAMESPACE = "/services/sng/v1.0.0/sequence";

    private String namespace;
    private String sequenceName;
    private StorageType storageType;
    private Configuration configuration;

    private SngServiceSetting(Builder builder) {
        this.namespace = builder.namespace;
        this.sequenceName = builder.sequenceName;
        this.storageType = builder.storageType;
        this.configuration = builder.configuration;
    }

    public static Builder createBuilder() {
        return new Builder();
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

    public String getNamespace() {
        return namespace;
    }

    public static class Builder {
        private String namespace;
        private String sequenceName;
        private StorageType storageType;
        private Configuration configuration;

        private Builder() {
        }

        public Builder withNamespace(String namespace) {
            this.namespace = namespace;
            return this;
        }

        public Builder withSequenceName(String sequenceName) {
            this.sequenceName = sequenceName;
            return this;
        }

        public Builder withStorageType(StorageType storageType) {
            this.storageType = storageType;
            return this;
        }

        public Builder withConfiguration(Configuration configuration) {
            this.configuration = configuration;
            return this;
        }

        public SngServiceSetting build() {
            return new SngServiceSetting(this);
        }
    }
}
