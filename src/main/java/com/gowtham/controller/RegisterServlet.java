package com.gowtham.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.gowtham.util.DBManager;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("application/json");
		
		StringBuilder jsonString = new StringBuilder();
		String line;
		
		while((line = request.getReader().readLine())!=null) {
			jsonString.append(line);
		}
		
		JSONObject jsonObject = new JSONObject(jsonString.toString());
		
        String username = jsonObject.getString("username");
        String fullName = jsonObject.getString("fullName");
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        String phone = jsonObject.getString("phone");
        String answer = jsonObject.getString("answer");
        
        System.out.println(username + fullName+email+password+phone+answer);
		
        JSONObject res = getJson(username,fullName,email,password,phone,answer);
		
        PrintWriter out = response.getWriter();
        out.print(res.toString());
        out.flush();
	}
	
	public JSONObject getJson(String username, String fullName, String email, String password, String phone, String answer) {
		
		DBManager db = DBManager.getInstance();
		JSONObject resJson = new JSONObject();
		
		if(db.checkUsernameandEmail(username,email)) {
			resJson.put("success", false);
            resJson.put("message", "Username or Email already Exists");
		}
		else if(db.fillUser(username,fullName,email,password,phone,answer)) {
			resJson.put("success", true);
            resJson.put("message", "Welcome "+username);
		}else {
            resJson.put("success", false);
            resJson.put("message", "Username or Email alread exits");
        }
		
		return resJson;
	}

}
