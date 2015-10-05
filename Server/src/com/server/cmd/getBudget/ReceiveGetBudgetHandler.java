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
import com.server.model.TypeMoney;
import com.server.model.User;
import com.server.model.Wallet;

public class ReceiveGetBudgetHandler extends AbstractHandler {

	public void handle(String arg0, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json;charset=UTF-8");

		PrintWriter out = response.getWriter();

		try {
			
			Gson gson = new Gson();
			String data = (String) baseRequest.getParameter("data");
			JsonParser parser = new JsonParser();
			JsonObject o = (JsonObject) parser.parse(data);
			String uId = o.get("uID").toString().replaceAll("\"", "");
			String wId = o.get("wID").toString().replaceAll("\"", "");
			String mId = o.get("mID").toString().replaceAll("\"", "");
			if (uId != null)
			{
				User user = DataController.getUserId(uId);
				String showUser = gson.toJson(user);
				out.print(showUser);
				
				Wallet wallet = DataController.getwalletID(wId);
				String showWallet = gson.toJson(wallet);
				out.print(showWallet);
				
				TypeMoney typemoney = DataController.gettypeID(mId);
				String showTypeMoney = gson.toJson(typemoney);
				out.print(showTypeMoney);
			} else {
				
				if (wId != null)
				{
					Wallet wallet = DataController.getwalletID(wId);
					String showWallet = gson.toJson(wallet);
					out.print(showWallet);
				} else {
					
					
					TypeMoney typemoney = DataController.gettypeID(mId);
					String showTypeMoney = gson.toJson(typemoney);
					out.print(showTypeMoney);
				}
			
			}
	

		} catch (Exception ex) {
			System.out.println("Loi gi: " + ex);
			out.print("Error: " + ex);
		} finally {
			out.close();
		}

	}
}