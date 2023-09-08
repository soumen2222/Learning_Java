package com.soumen.concurrency.throttle;

import com.soumen.concurrency.Address;
import com.soumen.concurrency.AddressVerifier;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class CyclicBarrierBatcherAddressVerifier implements AddressVerifier {
	private CyclicBarrierBatcher batcher;
	
	public CyclicBarrierBatcherAddressVerifier(AddressVerifier delegate, int batchSize, int timeout) {
		batcher = new CyclicBarrierBatcher(batchSize, timeout, delegate);
	}
	
	@Override
	public void verify(List<Address> addresses) {
		try {
			batcher.submit(addresses).get();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

}
