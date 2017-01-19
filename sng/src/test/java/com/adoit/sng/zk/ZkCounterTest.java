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

import com.adoit.sng.CounterException;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ZkCounterTest extends ZkTestCaseBase {
    private static final String nodePath = "/services/sng/v1.0.0/sequence";

    @Test
    public void testBadPath() throws Exception {
        final ZkCounter zkCounter = new ZkCounter(nodePath, zkClient);
        try {
            zkCounter.get();
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CounterException.class).hasMessageContaining("start");
        }
        try {
            zkCounter.incrementAndGet();
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CounterException.class).hasMessageContaining("start");
        }
        try {
            zkCounter.incrementAndGetBy(10);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CounterException.class).hasMessageContaining("start");
        }
        try {
            zkCounter.reset();
        } catch (Exception e) {
            assertThat(e).isInstanceOf(CounterException.class).hasMessageContaining("start");
        }

        zkCounter.start();
        try {
            zkCounter.get();
            zkCounter.incrementAndGet();
            zkCounter.incrementAndGetBy(10);
            zkCounter.reset();
        } catch (Exception e) {
            Assertions.fail("bad path unexpected error", e);
        }
        zkCounter.stop();
    }

    @Test
    public void testHappyPath() throws Exception {
        final ZkCounter zkCounter = new ZkCounter(nodePath, zkClient);
        zkCounter.start();
        zkCounter.reset();
        assertThat(zkCounter.get()).isEqualTo(0L);
        zkCounter.resetTo(1L);
        assertThat(zkCounter.get()).isEqualTo(1L);
        zkCounter.reset();
        assertThat(zkCounter.incrementAndGet()).isEqualTo(1L);
        assertThat(zkCounter.incrementAndGetBy(2L)).isEqualTo(3L);
        zkCounter.reset();
        for (int i = 0; i < 10; i++) {
            assertThat(zkCounter.incrementAndGet()).isEqualTo(i + 1);
        }
        assertThat(zkCounter.get()).isEqualTo(10L);
        zkCounter.stop();
    }

    @Test
    public void testConcurrent() throws Exception {
        final int threadsCount = 50;
        final ZkCounter zkCounter = new ZkCounter(nodePath, zkClient);
        zkCounter.start();
        zkCounter.reset();
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    zkCounter.incrementAndGet();
                } catch (Exception e) {
                    fail("concurrent increment and get failed");
                }
            }
        };

        ExecutorService executorService = Executors.newFixedThreadPool(threadsCount);
        for (int i = 0; i < threadsCount; i++) {
            executorService.execute(runnable);
        }
        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
        assertThat(zkCounter.get()).isEqualTo(50);

    }
}
