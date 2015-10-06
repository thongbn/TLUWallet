package com.server.cmd.getBudget;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
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

			JsonParser parser = new JsonParser();
			String data = (String) baseRequest.getParameter("data");
			com.google.gson.JsonObject o = (com.google.gson.JsonObject) parser.parse(data);
			String uId = o.get("uID").toString().replaceAll("\"", "");
			User user = DataController.getUserId(uId);

			List<Wallet> vi = WalletDataController.getwalletID(uId);

			ObjectMapper mapper = new ObjectMapper();
			
			String json = "";
			Map map = new HashMap();
			map.put("User", user);
			map.put("Wallet", vi);
			json = mapper.writeValueAsString(map);
			out.println(json);
			

		} catch (Exception ex) {
			System.out.println("Loi gi: " + ex);
			out.print("Error: " + ex);
		} finally {
			out.close();
		}

	}
	
}