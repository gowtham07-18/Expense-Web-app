package com.gowtham.controller.login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.gowtham.util.DBManager;

public class LoginModelServlet {
	private LoginViewServlet loginView;

	LoginModelServlet(LoginViewServlet loginView) {
		this.loginView = loginView;
	}
	
	
	public JSONObject getJson(String username, String password, HttpServletRequest request) {
        DBManager db = DBManager.getInstance();
        JSONObject resJson = new JSONObject();

        if (db.authenticateUser(username, password)) {
        	HttpSession session = request.getSession();
        	session.setAttribute("username", username);
            resJson.put("success", true);
            resJson.put("message", "Welcome "+username);
        } else {
            resJson.put("success", false);
            resJson.put("message", "Invalid username or password.");
        }

        return resJson;
	}

	public void getJsonFromView(String username, String password,HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		JSONObject resJson = getJson(username,password,request);
		loginView.getJsonFromModel(resJson,response);
	}
	
}
