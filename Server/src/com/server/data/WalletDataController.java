package com.server.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.server.model.Wallet;
import com.server.server.ConnectionUtils;

public class WalletDataController {

	public static Wallet getwalletID(String iD) throws SQLException {

		Connection connection = null;
		try {
			connection = ConnectionUtils.getMyConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Wallet w = null;
		ResultSet rs = null;
		String sqlCommand = "select * from vi where idVi = ?";
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(sqlCommand);
			// replace "?" by id
			pst.setString(1, iD);
			rs = pst.executeQuery();
			while (rs.next()) {
				String idVi = rs.getString("idVi");
				String tenVi = rs.getString("TenVi");
				String soTien = rs.getString("SoTien");
				String idUser = rs.getString("idNguoiDung");
				String idTien = rs.getString("idTienTe");
				w = new Wallet(idVi, tenVi, soTien, idUser, idTien);
			}
		} catch (SQLException e) {
			System.out.println("select error \n" + e.toString());
		}
		return w;
	}

	public static void DeleteWallet(String iD) throws SQLException {
		Connection connection = null;
		try {
			connection = ConnectionUtils.getMyConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sqlCommand = "DELETE FROM vi WHERE idVi = ?";
		PreparedStatement pst = null;
		pst = connection.prepareStatement(sqlCommand);
		pst.setString(1, iD);
		pst.executeUpdate();

	}

	public static void InsertWallet(Wallet w) throws SQLException {
		Connection connection = null;
		try {
			connection = ConnectionUtils.getMyConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql1 = "insert into vi   value(?, ?, ?, ?, ?)";
		PreparedStatement pst = null;
		pst = connection.prepareStatement(sql1);
		pst.setString(1, w.getwalletID());
		pst.setString(2, w.getwalletName());
		pst.setString(3, w.getMoney());
		pst.setString(4, w.getuserID());
		pst.setString(5, w.getmoneyID());
		pst.executeUpdate();
	}

	public static void UpdateWallet(Wallet w) throws SQLException {
		Connection connection = null;
		try {
			connection = ConnectionUtils.getMyConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql2 = "Update vi" 
				+ " set TenVi = ?, SoTien = ?, idNguoiDung = ?, idTienTe = ?"
				+ " where idVi = ?";
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(sql2);
			pst.setString(1, w.getwalletName());
			pst.setString(2, w.getMoney());
			pst.setString(3, w.getuserID());
			pst.setString(4, w.getmoneyID());
			pst.setString(5, w.getwalletID());
			pst.executeUpdate();
		} catch (SQLException e) {
			System.out.println("update error \n" + e.toString());
		}
	}
}

