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
import org.junit.After;
import org.junit.Before;

public class ZkTestCaseBase {
    protected ZkClient zkClient;

    @Before
    public void setUp() throws Exception {
        zkClient = new ZkClient("localhost:2181");
    }

    @After
    public void tearDown() throws Exception {
        zkClient.close();
    }
}
