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

import org.I0Itec.zkclient.ZkClient;

public class ZkClientFactory {

    private ZkClientFactory() {
    }

    public static ZkClient createClient(ZkConfiguration zkConfiguration) {
        return new ZkClient(
                zkConfiguration.getConnectionString(),
                zkConfiguration.getSessionTimeout(),
                zkConfiguration.getConnectionTiemout());

    }
}
