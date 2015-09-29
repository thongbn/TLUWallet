package com.server.cmd.syncBudget;

//import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

//import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.server.data.DataController;
import com.server.model.User;

public class ReceiveSyncBugetHandler extends AbstractHandler {

	@Override
	public void handle(String arg0, Request baseRequest,
			HttpServletRequest arg2, HttpServletResponse response)
			throws IOException {

		response.setContentType("application/json;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		baseRequest.setHandled(true);
		PrintWriter out = response.getWriter();

		try {
			// Gson gson = new Gson();
			String data = (String) baseRequest.getParameter("data");
			JsonParser parser = new JsonParser();
			JsonObject o = (JsonObject) parser.parse(data);
			String uId = o.get("ID").toString().replaceAll("\"", "");

			User user = DataController.getUserId(uId);
			User u = null;
			String uName = o.get("Name").toString().replaceAll("\"", "");
			String uPass = o.get("Pass").toString().replaceAll("\"", "");
			
			u = new User(uId, uName, uPass);
			if (user == null) {
				DataController.InsertUser(u);
				out.println("Them user thanh cong");
			} else {
				DataController.UpdateUser(u);
				out.println("Cap nhat user thanh cong");

			}

			// String hienthi = gson.toJson(user);
			// out.print(hienthi);

		} catch (Exception ex) {
			System.out.println("Loi: " + ex);
			out.print("Error: " + ex);
		} finally {
			out.close();
		}

	}
	// response.getWriter().println(
	// callURL("http://localhost:8080/budget/getBudget/*"));
	//
	// }
	//
	// private String callURL(String myURL) {
	//
	// StringBuilder sb = new StringBuilder();
	// URLConnection urlConn = null;
	// InputStreamReader in = null;
	// try {
	// URL url = new URL(myURL);
	// urlConn = url.openConnection();
	// if (urlConn != null)
	// urlConn.setReadTimeout(60 * 1000);
	// if (urlConn != null && urlConn.getInputStream() != null) {
	// in = new InputStreamReader(urlConn.getInputStream(),
	// Charset.defaultCharset());
	// BufferedReader bufferedReader = new BufferedReader(in);
	// if (bufferedReader != null) {
	// int cp;
	// while ((cp = bufferedReader.read()) != -1) {
	// sb.append((char) cp);
	// }
	// bufferedReader.close();
	// }
	// }
	// in.close();
	// } catch (Exception e) {
	// throw new RuntimeException("Exception while calling URL:" + myURL,
	// e);
	// }
	//
	// return sb.toString();
	//
	// }

}
