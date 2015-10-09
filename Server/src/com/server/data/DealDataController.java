package com.server.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.server.model.Deal;
import com.server.server.ConnectionUtils;

public class DealDataController {
	public static List<Deal> getdealID(String iDD) throws SQLException {

		Connection connection = null;
		try {
			connection = ConnectionUtils.getMyConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<Deal> dl = new ArrayList<Deal>();
		// Wallet w = null;
		ResultSet rs = null;
		String sqlCommand = "select * "
				+ "from giaodich inner join vi on giaodich.idVi = vi.idVi "
				+ "where vi.idVi = ?";
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(sqlCommand);
			// replace "?" by id
			pst.setString(1, iDD);
			rs = pst.executeQuery();
			while (rs.next()) {

				String idGiaoDich = rs.getString("idGiaoDich");
				String TienGiaoDich = rs.getString("TienGiaoDich");
				String ChiTietGiaoDich = rs.getString("ChiTietGiaoDich");
				String NgayGiaoDich = rs.getString("NgayGiaoDich");
				String idVi = rs.getString("idVi");
				String idNhom = rs.getString("idNhom");

				dl.add(new Deal(idGiaoDich, TienGiaoDich, ChiTietGiaoDich,
						NgayGiaoDich, idVi, idNhom));
			}

		} catch (SQLException e) {
			System.out.println("select error \n" + e.toString());
		}
		return dl;
	}

	public static void DeleteDeal(String iDD) throws SQLException {
		Connection connection = null;
		try {
			connection = ConnectionUtils.getMyConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sqlCommand = "DELETE FROM giaodich WHERE idGiaoDich = ?";
		PreparedStatement pst = null;
		pst = connection.prepareStatement(sqlCommand);
		pst.setString(1, iDD);
		pst.executeUpdate();

	}

	public static void InsertDeal(Deal dl) throws SQLException {
		Connection connection = null;
		try {
			connection = ConnectionUtils.getMyConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql1 = "insert into giaodich values(?, ?, ?, ?, ?, ?)";
		PreparedStatement pst = null;
		pst = connection.prepareStatement(sql1);
		pst.setString(1, dl.getdealID());
		pst.setString(2, dl.getmoneyDeal());
		pst.setString(3, dl.getdealDetail());
		pst.setNString(4, dl.getdate());
		pst.setString(5, dl.getwalletID());
		pst.setString(6, dl.getgroupID());
		pst.executeUpdate();
	}

	public static void UpdateDeal(Deal dl) throws SQLException {
		Connection connection = null;
		try {
			connection = ConnectionUtils.getMyConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql2 = "Update giaodich"
				+ " set TienGiaoDich = ?, ChiTietGiaoDich = ?, NgayGiaoDich = ?, idVi = ?, idNhom = ?"
				+ " where idGiaoDich = ?";
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(sql2);
			pst.setString(1, dl.getdealID());
			pst.setString(2, dl.getmoneyDeal());
			pst.setString(3, dl.getdealDetail());
			pst.setString(4, dl.getdate());
			pst.setString(5, dl.getwalletID());
			pst.setString(6, dl.getgroupID());
			pst.executeUpdate();
		} catch (SQLException e) {
			System.out.println("update error \n" + e.toString());
		}
	}

	public static Deal getdealIDbyDiD(String iDD) throws SQLException {

		Connection connection = null;
		try {
			connection = ConnectionUtils.getMyConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// List<Deal> dl = new ArrayList<Deal>();
		Deal dl = null;
		ResultSet rs = null;
		String sqlCommand = "select * " + "from giaodich "
				+ "where idGiaoDich = ?";
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(sqlCommand);
			// replace "?" by id
			pst.setString(1, iDD);
			rs = pst.executeQuery();
			while (rs.next()) {

				String idGiaoDich = rs.getString("idGiaoDich");
				String TienGiaoDich = rs.getString("TienGiaoDich");
				String ChiTietGiaoDich = rs.getString("ChiTietGiaoDich");
				String NgayGiaoDich = rs.getString("NgayGiaoDich");
				String idVi = rs.getString("idVi");
				String idNhom = rs.getString("idNhom");

				dl = new Deal(idGiaoDich, TienGiaoDich, ChiTietGiaoDich,
						NgayGiaoDich, idVi, idNhom);
			}

		} catch (SQLException e) {
			System.out.println("select error \n" + e.toString());
		}
		return dl;
	}
}
