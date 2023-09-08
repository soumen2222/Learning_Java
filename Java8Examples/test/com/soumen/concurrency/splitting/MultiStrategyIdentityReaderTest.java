package com.soumen.concurrency.splitting;

import com.soumen.concurrency.Identity;
import com.soumen.concurrency.IdentityReader;
import com.soumen.concurrency.MalformedIdentityRepository;
import com.soumen.concurrency.RandomIdentityReader;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

public class MultiStrategyIdentityReaderTest {
	private MalformedIdentityRepository malformed = new MalformedIdentityRepository() {
		@Override
		public void addIdentity(Identity identity, String reason) {}

		@Override
		public void addIdentity(InputStream message, String reason) {}
	};

	private List<IdentityReader> readers = Arrays.asList(
			new RandomIdentityReader(1), new RandomIdentityReader(2),
			new RandomIdentityReader(3), new RandomIdentityReader(4),
			new RandomIdentityReader(5));

	private InputStream bais;

	private static final Integer NUM_IDENTITIES = 5;

	@Before
	public void setUp() {
		bais = new ByteArrayInputStream("125452342351245234512351234521452".getBytes());
	}

	@Test
	public void testSingleThreaded() {
		ScatterGatherer sg = new SingleThreadedScatterGatherer();
		MultiStrategyIdentityReader reader = new MultiStrategyIdentityReader(readers, sg, malformed);
		readIdentities(sg, reader);
	}

	@Test
	public void testExecutorService() {
		ScatterGatherer sg = new ExecutorServiceScatterGatherer();
		MultiStrategyIdentityReader reader = new MultiStrategyIdentityReader(readers, sg, malformed);
		readIdentities(sg, reader);
	}

	@Test
	public void testExecutorCompletionService() {
		ScatterGatherer sg = new ExecutorCompletionServiceScatterGatherer();
		MultiStrategyIdentityReader reader = new MultiStrategyIdentityReader(readers, sg, malformed);
		readIdentities(sg, reader);
	}
	
	private void readIdentities(ScatterGatherer sg, MultiStrategyIdentityReader reader) {
		for (int i = 0; i < NUM_IDENTITIES; i++) {
			System.out.println("Identity #" + i);
			reader.read(bais);
		}
	}
}
