package com.soumen.concurrency.dependency;

import com.soumen.concurrency.*;
import com.soumen.concurrency.StatsLedger.StatsEntry;

import java.io.InputStream;
import java.util.stream.StreamSupport;

public class SingleThreadedIdentityPipeline {
	private MalformedIdentityRepository malformed; // fire and forget
	private IdentityReader identityReader; 
	private AddressVerifier addressVerifier;
	private PhoneNumberFormatter phoneNumberFormatter;
	private EmailFormatter emailFormatter;
	private IdentityService identityService;
	private StatsLedger statsLedger;
	
	public SingleThreadedIdentityPipeline(MalformedIdentityRepository malformed, IdentityReader identityReader, AddressVerifier addressVerifier,
			PhoneNumberFormatter phoneNumberFormatter, EmailFormatter emailFormatter, IdentityService identityService, StatsLedger statsLedger) {
		this.malformed = malformed;
		this.identityReader = identityReader;
		this.addressVerifier = addressVerifier;
		this.phoneNumberFormatter = phoneNumberFormatter;
		this.emailFormatter = emailFormatter;
		this.identityService = identityService;
		this.statsLedger = statsLedger;
	}
	
	public void process(InputStream input) {
		StreamSupport.stream(
	            new IdentityIterable(input, identityReader).spliterator(), true)
		.forEach((identity) ->
		{
			System.out.println("Processing identity #" + identity.getId());
			try {
				validateAddresses(identity);
				phoneNumberFormatter.format(identity);
				emailFormatter.format(identity);
	
				if ( !identityService.persistOrUpdateBestMatch(identity) ) {
					statsLedger.recordEntry(new StatsEntry(identity));
				}
			} catch ( Exception e ) {
				malformed.addIdentity(identity, e.getMessage());
			}
		});
	}
	
	protected void validateAddresses(Identity identity) {
		addressVerifier.verify(identity.getAddresses());
		
		if ( identity.getAddresses().stream().allMatch(a -> !a.isVerified())) {
			throw new NoValidAddressesException();
		}
	}
}
