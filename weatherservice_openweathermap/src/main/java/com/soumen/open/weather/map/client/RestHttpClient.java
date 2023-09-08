package com.soumen.open.weather.map.client;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jboss.logging.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RestHttpClient {	
	
	private Logger log = Logger.getLogger(RestHttpClient.class);
	private static final int TIME_OUT_MS = 60000;
	private String restApiBaseHost; 
	public static final String CONTENT_TYPE ="Content-Type";
	public static final String JSON_CONTENT = "application/json";
	
	public RestHttpClient() {	
		restApiBaseHost = System.getProperty("com.soumen.rest.BaseHost");
		if (restApiBaseHost == null) {
			restApiBaseHost = "https://api.openweathermap.org/data/2.5/forecast";
		}
	}
	
	public Object doRestGet(String path, Type returnObjType, 
			List<NameValuePair> urlParameters, List<NameValuePair> headerParameters, String dateFormat){			
		Object returnObj = null;
		CloseableHttpClient httpClient = null;		
		try {
			httpClient = HttpClients.createDefault();
			URI uri = getURI(path, urlParameters);

			HttpGet httpGet = new HttpGet(uri);
			RequestConfig requestConfig = RequestConfig.custom()
					.setSocketTimeout(TIME_OUT_MS)
					.setConnectTimeout(TIME_OUT_MS)
					.build();
			httpGet.setConfig(requestConfig);
			if(null == headerParameters) {
				headerParameters = new ArrayList<>();
				headerParameters.add(new BasicNameValuePair(CONTENT_TYPE, JSON_CONTENT));
			}

			for(NameValuePair header: headerParameters){
				httpGet.addHeader(header.getName(), header.getValue());
			}			
			log.debug("===================Get Message=========================");	
			log.debug(httpGet.getURI());			
			log.debug("===================Get End Message=====================");
			
			CloseableHttpResponse response = httpClient.execute(httpGet);
			returnObj = doRestWebCall(httpClient, response, returnObjType,dateFormat);
		} catch (Exception e) {
			log.error("Exception in rest client doRestGet:"+e.getMessage());
		} finally {
			try {
				if (httpClient != null)
					httpClient.close();
			} catch (IOException e) {
				log.error(e);
			}
		}
		return returnObj;
	}
	
private URI getURI(String path, List<NameValuePair> parameters) throws URISyntaxException {
		URI uri =null;
		if(parameters != null && !parameters.isEmpty()){
			uri= new URIBuilder(restApiBaseHost)
				.addParameters(parameters)
				.build();
		}else{
			uri = new URIBuilder(restApiBaseHost)
				.setPath("/"+path)
				.build();
		}
		return uri;
	}
	
	private Object doRestWebCall(CloseableHttpClient httpclient, 
			CloseableHttpResponse response, Type type,String dateFormat) throws Exception {

		Object returnObj = null;
		
		try {			
			try {
				log.debug("Response:");
				log.debug(response.getProtocolVersion());
				log.debug(response.getStatusLine().getStatusCode());
				log.debug(response.getStatusLine().getReasonPhrase());
				log.debug(response.getStatusLine().toString());			
				
				HttpEntity entity = response.getEntity();
				
				// do something useful with the response body
				// and ensure it is fully consumed

				if (response.getStatusLine().getStatusCode() >= 400) {
					// Application error
					log.debug("ERROR Entity:");
					log.debug(entity.getContentType());
					log.debug(entity.getContentLength());
					String content = EntityUtils.toString(entity);
					
					log.debug(content);
					log.debug(content);
					
					ErrorResponse eResponse = new ErrorResponse();
					eResponse.setCode(""+response.getStatusLine().getStatusCode());
					eResponse.setMessage(content);
					return eResponse;					
				 }
				 
				if (response.getStatusLine().getStatusCode() == 204) {
					//For delete/ put methods we get 204 as success status
					returnObj = 204;
				} else if(response.getStatusLine().getStatusCode() == 202) {
					//For some POST methods we get 202/Accepted as success status
					returnObj = 202;
				} else {
					if (entity == null) {
						throw new Exception("Request Failed: Response contains no content");
					}
					String content = EntityUtils.toString(entity);
										
					if (type != null) {
						String defaultDateFormat = "yyyy-MM-dd'T'HH:mm:ssZ";
						if(dateFormat !=null && !dateFormat.isEmpty())
							defaultDateFormat = dateFormat;
						Gson gson = new GsonBuilder().setDateFormat(defaultDateFormat).create();
						returnObj = gson.fromJson(content, type);
					}
				}
			} finally {
				response.close();
			}
		} catch (Exception e) {
			log.error("Exception in rest clinet doRestWebCall:"+e.getMessage());
		} finally {
			try {
				if (httpclient != null)
					httpclient.close();
			} catch (IOException e) {
				// stuff it
			}
		}
		return returnObj;
	}	

}
