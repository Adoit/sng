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

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ZkConfigurationTest {

    @Test
    public void testConfiguration() {
        ZkConfiguration.Builder builder = new ZkConfiguration.Builder();
        ZkConfiguration configuration = builder.build();
        assertThat(configuration.getConnectionString()).startsWith("localhost").endsWith("2181");
        assertThat(configuration.getConnectionTiemout()).isEqualTo(ZkConfiguration.DEFAULT_CONNECTION_TIMEOUT);
        assertThat(configuration.getSessionTimeout()).isEqualTo(ZkConfiguration.DEFAULT_SESSION_TIMEOUT);

        configuration = builder
                .setConnectionString("127.0.0.1:2181")
                .setConnectionTiemout(300)
                .setSessionTimeout(500).build();

        assertThat(configuration.getConnectionString()).isEqualTo("127.0.0.1:2181");
        assertThat(configuration.getConnectionTiemout()).isEqualTo(300);
        assertThat(configuration.getSessionTimeout()).isEqualTo(500);
    }

}
