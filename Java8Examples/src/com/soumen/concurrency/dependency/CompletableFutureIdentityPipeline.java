package com.soumen.concurrency.dependency;

import com.soumen.concurrency.*;
import com.soumen.concurrency.StatsLedger.StatsEntry;

import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.StreamSupport;



public class CompletableFutureIdentityPipeline {
	private MalformedIdentityRepository malformed; // fire and forget
	private IdentityReader identityReader; 
	private AddressVerifier addressVerifier;
	private PhoneNumberFormatter phoneNumberFormatter;
	private EmailFormatter emailFormatter;
	private IdentityService identityService;
	private StatsLedger statsLedger;
	
	public CompletableFutureIdentityPipeline(MalformedIdentityRepository malformed, IdentityReader identityReader, AddressVerifier addressVerifier,
			PhoneNumberFormatter phoneNumberFormatter, EmailFormatter emailFormatter, IdentityService identityService, StatsLedger statsLedger) {
		this.malformed = malformed;
		this.identityReader = identityReader;
		this.addressVerifier = addressVerifier;
		this.phoneNumberFormatter = phoneNumberFormatter;
		this.emailFormatter = emailFormatter;
		this.identityService = identityService;
		this.statsLedger = statsLedger;
	}

	private ExecutorService pool = Executors.newWorkStealingPool();

	public void process(InputStream input) {
		StreamSupport.stream(
	            new IdentityIterable(input, identityReader).spliterator(), true)
			.forEach((identity) -> {
				System.out.println("Processing identity #" + identity.getId());
				try {
					CompletableFuture<Void> address = CompletableFuture.runAsync(() ->
						validateAddresses(identity),
						pool);
					
					CompletableFuture<Void> phoneNumber = CompletableFuture.runAsync(() ->
						phoneNumberFormatter.format(identity),
						pool);
						
					CompletableFuture<Void> email = CompletableFuture.runAsync(() ->
						emailFormatter.format(identity),
						pool);
		
					CompletableFuture.allOf(address, phoneNumber, email)
						.thenRunAsync(() -> {
							if ( !identityService.persistOrUpdateBestMatch(identity) ) {
								statsLedger.recordEntry(new StatsEntry(identity));
							}
						}, pool)
						.exceptionally((ex) -> {
							malformed.addIdentity(identity, ex.getMessage());
							return null;
						});
				} catch ( Exception e ) {
					malformed.addIdentity(identity, e.getMessage());
				}
			});
	}
	
	private void validateAddresses(Identity identity) {
		addressVerifier.verify(identity.getAddresses());
		
		if ( identity.getAddresses().stream().allMatch(a -> !a.isVerified())) {
			throw new NoValidAddressesException();
		}
	}
}
