package com.server.cmd.syncBudget;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.server.data.DataController;
import com.server.model.Deal;
import com.server.model.User;
import com.server.model.Wallet;

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
			String data1 = (String) baseRequest.getParameter("dataUser");

			if (data1 != null) {
				JsonParser parser = new JsonParser();
				JsonObject o = (JsonObject) parser.parse(data1);
				String uId = o.get("uID").toString().replaceAll("\"", "");

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
			} else {
				String data2 = (String) baseRequest.getParameter("dataWallet");
				if (data2 != null)
				{
					JsonParser parser1 = new JsonParser();
					JsonObject o1 = (JsonObject) parser1.parse(data2);
					String wId = o1.get("wID").toString().replaceAll("\"", "");
					Wallet wallet = DataController.getwalletIDbyWID(wId);
					
					Wallet w = null;
					String wName = o1.get("TenVi").toString().replaceAll("\"", "");
					String wSoTien = o1.get("SoTien").toString().replaceAll("\"", "");
					String widNguoiDung = o1.get("NguoiDung").toString().replaceAll("\"", "");
					String wLoaiTien = o1.get("LoaiTien").toString().replaceAll("\"", "");
	
					w = new Wallet(wId, wName, wSoTien, widNguoiDung, wLoaiTien);
					
					if(wallet == null){
						DataController.InsertWallet(w);
						out.println("Them moi thanh cong");
						
					}else{
						DataController.UpdateWallet(w);
						out.println("Chinh sua thanh cong");
					}
				}
				else{
					String data3 = (String) baseRequest.getParameter("dataDeal");
					JsonParser parser2 = new JsonParser();
					JsonObject o3 = (JsonObject) parser2.parse(data3);
					String dId = o3.get("idGiaoDich").toString().replaceAll("\"", "");
					List<Deal> deal = DataController.getdealID(dId);

					Deal d = null;
					String TienGiaoDich = o3.get("TienGiaoDich").toString().replaceAll("\"", "");
					String ChiTietGiaoDich = o3.get("ChiTietGiaoDich").toString().replaceAll("\"", "");
					String NgayGiaoDich = o3.get("NgayGiaoDich").toString().replaceAll("\"", "");
					String idVi = o3.get("idVi").toString().replaceAll("\"", "");
					String idNhom = o3.get("idNhom").toString().replaceAll("\"", "");
	
					d = new Deal(dId, TienGiaoDich, ChiTietGiaoDich, NgayGiaoDich,idVi, idNhom);
					if (deal.isEmpty()) {
						DataController.InsertDeal(d);
						out.println("Them user thanh cong");
					} else {
						DataController.UpdateDeal(d);
						out.println("Cap nhat user thanh cong");
	
					}
				}
			}

		} catch (Exception ex) {
			System.out.println("Loi: " + ex);
			out.print("Error: " + ex);
		} finally {
			out.close();
		}

	}

}
