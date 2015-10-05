package com.server.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.server.model.TypeMoney;
import com.server.model.Wallet;
import com.server.server.ConnectionUtils;

public class TypeMoneyDataController {

	public static TypeMoney gettypeID(String Id) throws SQLException {

		Connection connection = null;
		try {
			connection = ConnectionUtils.getMyConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TypeMoney tp = null;
		Wallet w = null;
		ResultSet rs = null;
		String sqlCommand = "select idTienTe, LoaiTien "
				+ "from vi inner join tiente on vi.idTienTe = tiente.idTienTe "
				+ "where vi.idVi = ?";
		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(sqlCommand);
			// replace "?" by id
			pst.setString(1, Id);
			rs = pst.executeQuery();
			while (rs.next()) {
				String idType = rs.getString("idTienTe");
				String Typename = rs.getString("LoaiTien");
				tp = new TypeMoney(idType, Typename);
			}
		} catch (SQLException e) {
			System.out.println("select error \n" + e.toString());
		}
		return tp;
	}
}
