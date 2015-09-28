package com.server.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.server.model.User;
import com.server.server.ConnectionUtils;


public class UserDataController {
	
	public static User getUserId(String id) throws SQLException{
		
		Connection connection = null;
		try {
			connection = ConnectionUtils.getMyConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		User u = null;
		ResultSet rs = null;
        String sqlCommand = "select * from nguoidung where idNguoiDung = ?";
        PreparedStatement pst = null;
        try {
                pst = connection.prepareStatement(sqlCommand);
                // replace "?" by id
                pst.setString(1, id);
                rs = pst.executeQuery();
                while (rs.next()){
                	String iduser = rs.getString("idNguoiDung");
                	String username = rs.getString("UserName");
                	String password = rs.getString("Pass");
                	u = new User(iduser, username, password);
                }
        } catch (SQLException e) {
                System.out.println("select error \n" + e.toString());
        }
        return u;
    }
	
	public static void DeleteUser(String id) throws SQLException {
		Connection connection = null;
		try {
			connection = ConnectionUtils.getMyConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sqlCommand = "DELETE FROM nguoidung WHERE idNguoiDung = ?";
		PreparedStatement pst = null;
		pst = connection.prepareStatement(sqlCommand);
		pst.setString(1, id);
		pst.executeUpdate();
              
	}
	
	public static void InsertUser(User s) throws SQLException {
		Connection connection = null;
		try {
			connection = ConnectionUtils.getMyConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String sql1 = "insert into nguoidung   value(?, ?, ?)";
        PreparedStatement pst = null;
        pst = connection.prepareStatement(sql1);
        pst.setString(1, s.getUserID());
        pst.setString(2, s.getUserName());
        pst.setString(3, s.getPassWord());
        pst.executeUpdate();
	}
}
