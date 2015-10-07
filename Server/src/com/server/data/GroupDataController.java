package com.server.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.server.model.Group;
import com.server.model.Deal;
import com.server.server.ConnectionUtils;

public class GroupDataController {
	public static Group getgroupID(String Id) throws SQLException {

		Connection connection = null;
		try {
			connection = ConnectionUtils.getMyConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Group gr = null;
		Deal d = null;
		ResultSet rs = null;
		String sqlCommand = "select * "
				+ "from giaodich inner join nhom on giaodich.idNhom = .idNhom "
				+ "where giaodich.idGiaoDich = ?";
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(sqlCommand);
			// replace "?" by id
			pst.setString(1, Id);
			rs = pst.executeQuery();
			while (rs.next()) {
				String idGroup = rs.getString("idNhom");
				String TypeGroup = rs.getString("LoaiNhom");
				gr = new Group(idGroup, TypeGroup);
			}
		} catch (SQLException e) {
			System.out.println("select error \n" + e.toString());
		}
		return gr;
	}
}
