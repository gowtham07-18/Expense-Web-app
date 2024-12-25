package com.gowtham.controller.login;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

@WebServlet("/LoginViewServlet")
public class LoginViewServlet extends HttpServlet {
	private LoginModelServlet loginModel;
	
	public LoginViewServlet(){
		loginModel = new LoginModelServlet(this);
	}
	
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
        response.setContentType("application/json");

        StringBuilder jsonString = new StringBuilder();
        String line;

        while ((line = request.getReader().readLine()) != null) {
            jsonString.append(line);
        }
        
        JSONObject jsonObject = new JSONObject(jsonString.toString());
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        System.out.println(username+" "+password);

        sendJsonToModel(username,password,request,response);
        
    }
    
    public void sendJsonToModel(String username, String password, HttpServletRequest request,HttpServletResponse response) throws IOException {
    	
    	loginModel.getJsonFromView(username, password , request, response);

    }
    
    public void getJsonFromModel(JSONObject resJson, HttpServletResponse response) throws IOException {
	
        PrintWriter out = response.getWriter();
        out.print(resJson.toString());
        out.flush();
        
    }
}
