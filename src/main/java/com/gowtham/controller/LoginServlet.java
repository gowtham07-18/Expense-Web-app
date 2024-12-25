package com.gowtham.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.JSONObject;

import com.gowtham.util.DBManager;

import java.io.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

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

        JSONObject res = getJson(username, password , request);

        PrintWriter out = response.getWriter();
        out.print(res.toString());
        out.flush();
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

}
