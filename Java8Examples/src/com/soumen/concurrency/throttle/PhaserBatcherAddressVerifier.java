package com.soumen.concurrency.throttle;

import com.soumen.concurrency.Address;
import com.soumen.concurrency.AddressVerifier;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PhaserBatcherAddressVerifier implements AddressVerifier {
	private PhaserBatcher batcher;
	
	public PhaserBatcherAddressVerifier(AddressVerifier delegate, int batchSize, int timeout) {
		batcher = new PhaserBatcher(batchSize, timeout, addresses -> {
			delegate.verify(addresses);
		});
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
	
	public void throttleUp(int by) {
		this.batcher.throttleUp(by);
	}
	
	public void throttleDown(int by) {
		this.batcher.throttleDown(by);
	}
}
