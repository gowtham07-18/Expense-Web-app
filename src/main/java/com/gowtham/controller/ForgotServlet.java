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

@WebServlet("/ForgotServlet")
public class ForgotServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

        StringBuilder jsonString = new StringBuilder();
        String line;

        while ((line = request.getReader().readLine()) != null) {
            jsonString.append(line);
        }
        
        JSONObject jsonObject = new JSONObject(jsonString.toString());
        
        String email = jsonObject.getString("email");
        String answer = jsonObject.getString("answer");      
        String password = jsonObject.getString("password");
        String retypePassword = jsonObject.getString("retypePassword");
        
        System.out.println(email+" "+answer+" "+password+" "+retypePassword);
        
        JSONObject res = getJson(email,answer,password,retypePassword);

        PrintWriter out = response.getWriter();
        out.print(res.toString());
        out.flush();
	}
	
    public JSONObject getJson(String email, String answer, String password, String retypePassword) {
        DBManager db = DBManager.getInstance();
        JSONObject resJson = new JSONObject();
        
        if(!password.equals(retypePassword)) {
        	resJson.put("success", false);
        	resJson.put("message", "Passwords does not match");
        }
        else if(db.passwordCheck(password)) {
        	resJson.put("success", false);
        	resJson.put("message", "Password already exists!");     
        }
        else if (db.answerCheck(answer, retypePassword, email)) {
            resJson.put("success", true);
            resJson.put("message", "Password resetted succesfully!");
        } else {
            resJson.put("success", false);
            resJson.put("message", "Invalid username or password.");
        }

        return resJson;
    }

}
