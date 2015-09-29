package com.server.cmd.getBudget;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.server.data.DataController;
import com.server.model.User;



public class ReceiveGetBudgetHandler extends AbstractHandler {

	public void handle(String arg0, Request baseRequest, HttpServletRequest request,
			HttpServletResponse response) throws IOException{
		response.setContentType("application/json;charset=UTF-8");

		PrintWriter out = response.getWriter();

		try
		{

			Gson gson = new Gson();
			String data = (String) baseRequest.getParameter("data");
			JsonParser parser = new JsonParser();
			JsonObject o = (JsonObject)parser.parse(data);
			String uId = o.get("ID").toString().replaceAll("\"", "");
//			User u = null;
//			String uName = o.get("Name").toString();
//			String uPass = o.get("Pass").toString();
//			u = new User(uId, uName, uPass);
//			DataController.InsertUser(u);
			User user = DataController.getUserId(uId);
			String hienthi = gson.toJson(user);
			out.print(hienthi);
			
			

		}
		catch (Exception ex)
		{
			System.out.println("Loi gi: " + ex);
			out.print("Error: " + ex);
		}
		finally
		{
			out.close();
		}


	}
}