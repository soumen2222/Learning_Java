package com.soumen.queue;

import java.io.Serializable;

import javax.ejb.Local;
import javax.ejb.Remote;


public interface ForecastUpdateRequestQueue {
	@Remote
    public interface R extends ForecastUpdateRequestQueue {   }
    @Local
    public interface L extends ForecastUpdateRequestQueue {   }
    
    public void forecastMessage(Serializable message) throws Exception ;

}
