package com.soumen.open.weather.map.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "pod"  
})
public class OpenApiSys {

	
	private String pod;

    public String getPod ()
    {
        return pod;
    }

    public void setPod (String pod)
    {
        this.pod = pod;
    }

    @Override
    public String toString()
    {
        return "OpenApiSys [pod = "+pod+"]";
    }
}
