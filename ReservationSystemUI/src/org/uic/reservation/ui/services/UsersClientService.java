package org.uic.reservation.ui.services;

import org.apache.http.HttpResponse;
import org.uic.reservation.ui.dataobjects.ServiceResponse;
import org.uic.reservation.ui.dataobjects.Users;
import org.uic.reservation.ui.utils.CommonUtils;

import com.google.gson.Gson;

public class UsersClientService {

	public ServiceResponse signupUser(Users user) {

		String serviceURL = "http://localhost:8080/reservation-service/users";

		ServiceResponse response = new ServiceResponse();
		CommonUtils util = new CommonUtils();
		Gson gson = new Gson();
		HttpResponse httpResponse = null;
		try {
			httpResponse = util.executeClientPost(serviceURL, gson.toJson(user));
		} catch (Exception e) {
			response.setHasErrors(true);
			response.setErrorList(new String[] { e.getMessage() });
			return response;
		}
		util.updateServiceResponse(response, httpResponse);
		return response;
	}

}
