package org.uic.reservation.ui.utils;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.uic.reservation.ui.dataobjects.ServiceResponse;
import org.uic.reservation.ui.exceptions.ServiceException;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CommonUtils {
	
	public static final String CHARACTER_SET_ISO_8859_1 = "ISO-8859-1";
	public static final String ERROR_MSG = "ERROR_MSG";

	
	public HttpResponse executeClientPost(String url, String json) throws Exception {
		org.apache.http.client.HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(url);
		request.addHeader(HttpHeaders.ACCEPT,MediaType.APPLICATION_JSON);
		org.apache.http.entity.StringEntity entity = new StringEntity(json, MediaType.APPLICATION_JSON,
				CHARACTER_SET_ISO_8859_1);
		request.setEntity(entity);
		return client.execute(request);
	}
	
	public String readErrorMsg(HttpResponse httpResponse) {
		String errorMessage="Unknown Error";
		try {
			final String responseJsonString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
			final JsonObject jsonObj = new JsonParser().parse(responseJsonString).getAsJsonObject();
			errorMessage = jsonObj.get("errorMessage").toString();
		} catch (ParseException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}return errorMessage;
	}
	
	public void httpResponseStatus(HttpResponse httpResponse) throws IOException,ServiceException{

		int status = httpResponse.getStatusLine().getStatusCode();

		switch (status) {
		case HttpStatus.SC_OK: {
			break;
		}
		case HttpStatus.SC_CREATED: {
			break;
		}
		case HttpStatus.SC_NO_CONTENT: {
			break;
		}
		default: {
			throw new ServiceException(readErrorMsg(httpResponse));
		}
		}
	}
	
	public void updateServiceResponse(ServiceResponse serviceResponse, HttpResponse httpResponse) {
		try {
			httpResponseStatus(httpResponse);
			serviceResponse.setHasErrors(false);
		} catch (ServiceException e) {
			serviceResponse.setHasErrors(true);
			serviceResponse.setErrorList(new String[] { readErrorMsg(httpResponse) });
		} catch (IOException e) {
			serviceResponse.setHasErrors(true);
			serviceResponse.setErrorList(new String[] { e.getMessage() });
		} 
	}

}
