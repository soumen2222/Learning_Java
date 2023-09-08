package com.soumen.concurrency;

import java.io.InputStream;

public interface IdentityReader {
	public Identity read(InputStream is);
}
