package com.soumen.concurrency.throttle;

import com.soumen.concurrency.Address;

import java.util.List;
import java.util.concurrent.Future;

public interface Batcher {
	Future<?> submit(List<Address> jobs);
}
