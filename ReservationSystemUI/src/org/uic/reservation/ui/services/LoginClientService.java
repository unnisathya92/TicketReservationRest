package org.uic.reservation.ui.services;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;
import org.uic.reservation.ui.dataobjects.ServiceResponse;
import org.uic.reservation.ui.dataobjects.Login;
import org.uic.reservation.ui.utils.CommonUtils;

import com.google.gson.Gson;

public class LoginClientService {

	public String loginUser(Login login) {

		String serviceURL = "http://localhost:8080/reservation-service/sessions";

		ServiceResponse response = new ServiceResponse();
		CommonUtils util = new CommonUtils();
		Gson gson = new Gson();
		HttpResponse httpResponse = null;
		try {
			httpResponse = util.executeClientPost(serviceURL, gson.toJson(login));
		} catch (Exception e) {
			response.setHasErrors(true);
			response.setErrorList(new String[] { e.getMessage() });
			return gson.toJson(response);
		}
		util.updateServiceResponse(response, httpResponse);
		return gson.toJson(response);
	}

}
