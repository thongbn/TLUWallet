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
			JsonParser parser = new JsonParser();
<<<<<<< HEAD
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
	
=======
			String data = (String) baseRequest.getParameter("data");

			if (data != null) {
				JsonObject o = (JsonObject) parser.parse(data);

				String uId = o.get("uID").toString().replaceAll("\"", "");
				User user = DataController.getUserId(uId);
				String showUser = gson.toJson(user);
				out.print(showUser);

				String wId = o.get("wID").toString().replaceAll("\"", "");
				Wallet wallet = DataController.getwalletID(wId);
				String showWallet = gson.toJson(wallet);
				out.print(showWallet);

				String mId = o.get("mID").toString().replaceAll("\"", "");
				TypeMoney typemoney = DataController.gettypeID(mId);
				String showTypeMoney = gson.toJson(typemoney);
				out.print(showTypeMoney);

			}

			else {

				String data1 = (String) baseRequest.getParameter("dataUser");
				if (data1 != null) {
					JsonObject o1 = (JsonObject) parser.parse(data1);
					String uId = o1.get("uID").toString().replaceAll("\"", "");
					User user = DataController.getUserId(uId);
					String showUser = gson.toJson(user);
					out.print(showUser);
				} else {

					String data2 = (String) baseRequest
							.getParameter("dataWallet");

					if (data2 != null) {
						JsonObject o2 = (JsonObject) parser.parse(data2);
						String wId = o2.get("wID").toString()
								.replaceAll("\"", "");
						Wallet wallet = DataController.getwalletID(wId);
						String showWallet = gson.toJson(wallet);
						out.print(showWallet);
					} else {
						String data3 = (String) baseRequest
								.getParameter("dataMoney");
						JsonObject o3 = (JsonObject) parser.parse(data3);
						String mId = o3.get("mID").toString()
								.replaceAll("\"", "");
						TypeMoney typemoney = DataController.gettypeID(mId);
						String showTypeMoney = gson.toJson(typemoney);
						out.print(showTypeMoney);
					}

				}

			}
>>>>>>> 20eb1609a8e8e6ce18fe5beb35827f392c85d04e

		} catch (Exception ex) {
			System.out.println("Loi gi: " + ex);
			out.print("Error: " + ex);
		} finally {
			out.close();
		}

	}
}