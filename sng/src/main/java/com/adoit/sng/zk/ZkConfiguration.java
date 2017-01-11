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

public class ZkConfiguration implements Configuration {
    public static final String DEFAULT_CONNECTION_STRING = "localhost:2181";
    public static final int DEFAULT_SESSION_TIMEOUT = 30000;
    public static final int DEFAULT_CONNECTION_TIMEOUT = Integer.MAX_VALUE;

    private String connectionString;
    private int sessionTimeout;
    private int connectionTiemout;

    public String getConnectionString() {
        return connectionString;
    }

    public int getSessionTimeout() {
        return sessionTimeout;
    }

    public int getConnectionTiemout() {
        return connectionTiemout;
    }

    public static class Builder {
        private String connectionString = DEFAULT_CONNECTION_STRING;
        private int sessionTimeout = DEFAULT_SESSION_TIMEOUT;
        private int connectionTiemout = DEFAULT_CONNECTION_TIMEOUT;

        public Builder setConnectionString(String connectionString) {
            this.connectionString = connectionString;
            return this;
        }

        public Builder setSessionTimeout(int sessionTimeout) {
            this.sessionTimeout = sessionTimeout;
            return this;
        }

        public Builder setConnectionTiemout(int connectionTiemout) {
            this.connectionTiemout = connectionTiemout;
            return this;
        }

        public ZkConfiguration build() {
            ZkConfiguration zkConfiguration = new ZkConfiguration();
            zkConfiguration.connectionString = this.connectionString;
            zkConfiguration.sessionTimeout = this.sessionTimeout;
            zkConfiguration.connectionTiemout = this.connectionTiemout;

            return zkConfiguration;
        }
    }
}
