package com.server.cmd.getBudget;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONObject;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.google.gson.JsonParser;
import com.server.data.DataController;
import com.server.data.WalletDataController;
import com.server.model.User;
import com.server.model.Wallet;

public class ReceiveGetBudgetHandler extends AbstractHandler {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void handle(String arg0, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		response.setContentType("application/json;charset=UTF-8");

		PrintWriter out = response.getWriter();

		try {

			// Gson gson = new Gson();
			JsonParser parser = new JsonParser();
			String data = (String) baseRequest.getParameter("data");
			com.google.gson.JsonObject o = (com.google.gson.JsonObject) parser
					.parse(data);
			String uId = o.get("uID").toString().replaceAll("\"", "");
			User user = DataController.getUserId(uId);

			Wallet vi = WalletDataController.getwalletID(uId);

			// Object obj = new Object();
			// JsonElement jsonElement = gson.toJsonTree(obj);
			// jsonElement.getAsJsonObject().addProperty("user",
			// gson.toJson(user));
			// jsonElement.getAsJsonObject().addProperty("wallets",
			// gson.toJson(vi));

			JSONObject obj = new JSONObject();
			JSONObject userJson = new JSONObject();
			userJson.put("UserID", user.getUserID());
			userJson.put("UserName", user.getUserName());
			userJson.put("Password", user.getPassWord());
			JSONObject walletJson = new JSONObject();
			walletJson.put("WalletID", vi.getwalletID());
			walletJson.put("WalletName", vi.getwalletName());
			walletJson.put("Money", vi.getMoney());
			walletJson.put("MoneyType", vi.getmoneyID());
			Collection mangVi = new ArrayList();
			mangVi.add(walletJson);

			obj.put("User", userJson);
			obj.put("Wallet", mangVi);

			String showUser = obj.toString();
			// String showUser = gson.toJson(vi);
			out.print(showUser);

		} catch (Exception ex) {
			System.out.println("Loi gi: " + ex);
			out.print("Error: " + ex);
		} finally {
			out.close();
		}

	}
}