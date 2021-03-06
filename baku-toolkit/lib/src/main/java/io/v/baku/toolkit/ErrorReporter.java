// Copyright 2015 The Vanadium Authors. All rights reserved.
// Use of this source code is governed by a BSD-style
// license that can be found in the LICENSE file.

package io.v.baku.toolkit;

import android.support.annotation.StringRes;

import java8.lang.FunctionalInterface;

@FunctionalInterface
public interface ErrorReporter {
    void onError(@StringRes int summaryStringId, Throwable t);
}
