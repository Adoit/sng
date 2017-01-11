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

import com.google.common.base.Preconditions;

import com.adoit.sng.Counter;
import com.adoit.sng.CounterException;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkBadVersionException;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于Zookeeper实现的分布式的Counter
 */
public class ZkCounter implements Counter {
    private static final Logger LOG = LoggerFactory.getLogger(ZkCounter.class);
    private static final long DEFAULT_VALUE = 0;
    private volatile boolean initialized = false;
    private ZkClient zkClient;
    private String nodePath;

    /**
     * @param nodePath node的全路径
     * @param zkClient zkClient, 用于连接Zookeeper
     */
    public ZkCounter(String nodePath, ZkClient zkClient) {
        Preconditions.checkArgument(nodePath.startsWith("/"), "node path must start with '/'");
        Preconditions.checkArgument(!nodePath.endsWith("/"), "node path can't end with '/'");
        Preconditions.checkNotNull(zkClient, "zkClient can't be null");

        this.nodePath = nodePath;
        this.zkClient = zkClient;
    }

    public long get() {
        ensureInitialized();
        Long count = zkClient.readData(this.nodePath);
        if (count == null) {
            throw new CounterException("Counter " + this.nodePath + " has null data");
        }
        return count;
    }

    public void reset() {
        resetTo(DEFAULT_VALUE);
    }

    public synchronized void resetTo(long value) {
        ensureInitialized();
        // -1 means update any version
        this.writeData(value);
    }

    public long incrementAndGet() {
        ensureInitialized();
        return incrementAndGetBy(1L);
    }

    public long incrementAndGetBy(long inc) {
        ensureInitialized();
        Stat stat = new Stat();
        while (true) {
            try {
                Long count = zkClient.readData(this.nodePath, stat);
                if (count == null) {
                    throw new CounterException("Counter " + this.nodePath + " has null data");
                }

                Long newCount = count + inc;
                zkClient.writeData(this.nodePath, newCount, stat.getVersion());
                return newCount;
            } catch (ZkBadVersionException e) {
                LOG.debug("Retrying counter");
            }
        }
    }

    public synchronized void start() {
        try {
            if (!zkClient.exists(this.nodePath)) {
                zkClient.createPersistent(this.nodePath, true);
                this.writeData(DEFAULT_VALUE);
            } else {
                Long data = zkClient.readData(this.nodePath);
                if (data == null) {
                    this.writeData(DEFAULT_VALUE);
                }
            }
        } catch (Exception e) {
            initialized = false;
            throw new CounterException(e);
        }
        initialized = true;
    }

    public synchronized void stop() {
        if (initialized) {
            try {
                zkClient.close();
            } catch (ZkInterruptedException e) {
                LOG.error("zkCounter stop failed", e);
                throw new CounterException(e);
            } finally {
                initialized = false;
            }
        }
    }

    private <T extends Object> T writeData(T data) {
        Stat stat = new Stat();
        while (true) {
            try {
                T oldData = zkClient.readData(this.nodePath, stat);
                zkClient.writeData(this.nodePath, data, stat.getVersion());
                return oldData;
            } catch (ZkBadVersionException e) {
                LOG.debug("Retrying writing");
            }
        }
    }

    private void ensureInitialized() {
        if (!initialized) {
            throw new CounterException("counter must be start first.");
        }
    }

}

