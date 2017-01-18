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

import com.adoit.sng.zk.ZkConfiguration;

import org.junit.Test;

import static org.assertj.core.api.Assertions.*;

public class SngServiceTest {

    @Test
    public void testHappyPath() throws Exception {
        Configuration zkConfiguration = new ZkConfiguration.Builder().build();

        SngServiceSetting sngServiceSetting = SngServiceSetting.createBuilder()
                .withNamespace("/services/sng/v1.0.0/sequence")
                .withSequenceName("test")
                .withConfiguration(zkConfiguration)
                .withStorageType(StorageType.ZOOKEEPER)
                .build();
        SngService sngService = SngService.getInstance(sngServiceSetting);
        sngService.start();

        long name1Value = sngService.next("name1");
        assertThat(sngService.next("name1")).isEqualTo(name1Value + 1);
        assertThat(sngService.next("name1", 10)).isEqualTo(name1Value + 11);

        long name2Value = sngService.next("name2");
        assertThat(sngService.next("name2")).isEqualTo(name2Value + 1);
        assertThat(sngService.next("name2", 100)).isEqualTo(name2Value + 101);

        sngService.stop();
    }
}
