package com.server.data;

import java.sql.SQLException;
import com.server.model.TypeMoney;
import com.server.model.User;
import com.server.model.Wallet;
import com.server.model.Deal;
import java.util.List;

public class DataController {
	static TypeMoneyDataController tDC;
	static UserDataController uDC;
	static WalletDataController wDC;
	static DealDataController dDC;
	public DataController(){
		uDC = new UserDataController();
		wDC = new WalletDataController();
		tDC = new TypeMoneyDataController();
	}
	//get-------------
	@SuppressWarnings("static-access")
	public static User getUserId(String id) throws SQLException {
		return uDC.getUserId(id);
	}
	@SuppressWarnings("static-access")
	public static  List<Wallet> getwalletID(String iD) throws SQLException {
		return wDC.getwalletID(iD);
	}
	@SuppressWarnings("static-access")
	public static TypeMoney gettypeID(String Id) throws SQLException {
		return tDC.gettypeID(Id);
	}
	@SuppressWarnings("static-access")
	public static List<Deal> getdealID(String iDD) throws SQLException {
		return dDC.getdealID(iDD);
	}
	//---------------- get
	
	@SuppressWarnings("static-access")
	public static void DeleteUser(String id) throws SQLException {	
		try {
			uDC.DeleteUser(id);
			System.out.println("Success");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	@SuppressWarnings("static-access")
	public static void DeleteWallet(String iD) throws SQLException {	
		try {
			wDC.DeleteWallet(iD);
			System.out.println("Success");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	@SuppressWarnings("static-access")
	public static void DeleteDeal(String iDD) throws SQLException {	
		try {
			dDC.DeleteDeal(iDD);
			System.out.println("Success");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	@SuppressWarnings("static-access")
	public static void InsertUser(User u) throws SQLException {	
		try {
			uDC.InsertUser(u);
			 System.out.println("insert success");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	@SuppressWarnings("static-access")
	public static void InsertWallet(Wallet w) throws SQLException {	
		try {
			wDC.InsertWallet(w);
			 System.out.println("insert success");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	@SuppressWarnings("static-access")
	public static void InsertDeal(Deal dl) throws SQLException {	
		try {
			dDC.InsertDeal(dl);
			 System.out.println("insert success");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	@SuppressWarnings("static-access")
	public static void UpdateUser(User s) throws SQLException {	
		try {
			uDC.UpdateUser(s);
			System.out.println("update success");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	@SuppressWarnings("static-access")
	public static void UpdateWallet(Wallet w) throws SQLException {	
		try {
			wDC.UpdateWallet(w);
			System.out.println("update success");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
	@SuppressWarnings("static-access")
	public static void UpdateDeal(Deal dl) throws SQLException {	
		try {
			dDC.UpdateDeal(dl);
			System.out.println("update success");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}
}
