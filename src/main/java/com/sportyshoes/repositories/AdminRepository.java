package com.sportyshoes.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.sportyshoes.dao.AdminDao;
import com.sportyshoes.models.Admin;
import com.sportyshoes.util.DBConnection;

@Repository
public class AdminRepository implements AdminDao {
	private Connection connection;

	private static Logger log = LoggerFactory.getLogger(AdminRepository.class);
	public AdminRepository(DBConnection dbc) {
		this.connection = dbc.getConnection();
	}

	@Override
	public int addAdmin(Admin a) {
		int rows = 0;
		String sql="INSERT INTO admin values(?,?)";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, a.getAdminId());
			ps.setString(2, String.valueOf(a.getPassword()));
			rows = ps.executeUpdate();
		}
		catch(SQLException se) {log.error(se.getLocalizedMessage());}
		catch(Exception ex) {log.error(ex.getLocalizedMessage());}
		log.info("Admin rows added:%d",rows);
		return rows;
	}

	@Override
	public Admin getAdmin(String adminId) {
		Admin a = null;
		String sql="SELECT * FROM admin WHERE adminId=?";
		try {
			PreparedStatement s = connection.prepareStatement(sql);
			s.setString(1, adminId);
			ResultSet rs = s.executeQuery();
			while(rs.next()) {
				a = new Admin(rs.getString("adminId"),rs.getString("password").toCharArray());
			}
		}
		catch(SQLException se) {log.error(se.getLocalizedMessage());}
		catch(Exception ex) {log.error(ex.getLocalizedMessage());}
		return a;
	}

	@Override
	public int updateAdmin(String adminId, Admin a) {
		int rows = 0;
		String sql="UPDATE admin SET password=? WHERE adminId=?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, String.valueOf(a.getPassword().toString()));
			ps.setString(2, a.getAdminId());
			rows = ps.executeUpdate();
		}
		catch(SQLException se) {log.error(se.getLocalizedMessage());}
		catch(Exception ex) {log.error(ex.getLocalizedMessage());}
		log.info("Updated Admin Rows: %d",rows);
		return rows;
	}

	@Override
	public int deleteAdmin(Admin a) {
		int rows = 0;
		String sql="DELETE FROM admin WHERE adminId=?";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, a.getAdminId());
			rows = ps.executeUpdate();
		}
		catch(SQLException se) {log.error(se.getLocalizedMessage());}
		catch(Exception ex) {log.error(ex.getLocalizedMessage());}
		log.info("Admin Rows deleted: %d",rows);
		return rows;
	}

}
