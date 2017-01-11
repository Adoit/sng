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

public class CounterException extends RuntimeException {

    private static final long serialVersionUID = -9016331684054372536L;

    public CounterException() {
        super();
    }

    public CounterException(String message) {
        super(message);
    }


    public CounterException(String message, Throwable cause) {
        super(message, cause);
    }

    public CounterException(Throwable cause) {
        super(cause);
    }

    protected CounterException(String message, Throwable cause,
                               boolean enableSuppression,
                               boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
