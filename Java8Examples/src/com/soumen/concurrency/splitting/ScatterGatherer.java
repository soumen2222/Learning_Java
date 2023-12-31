package com.soumen.concurrency.splitting;

import com.soumen.concurrency.Identity;

import java.util.concurrent.Callable;

public interface ScatterGatherer {
	public Identity go(Scatterer s, Gatherer g);
	
	public interface Scatterer {
		boolean hasNext();
		Callable<Identity> next();
	}
	
	public interface Gatherer {
		boolean needsMore();
		void gatherResult(Identity i);
		Identity getFinalResult();
	}
}