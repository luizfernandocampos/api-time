package com.devlf.apitime.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.UUID;

import com.devlf.apitime.util.SendMail;
import com.devlf.apitime.vo.UserVO;

public class UserDAO extends GenericDAO {

	public UserDAO(Connection conn) {
		super(conn);
	}
	
	public void save(UserVO userVO) throws Exception {
		if (userVO.getId() == 0) {
			this.insertUser(userVO);
		} else {
			this.updatetUser(userVO);
		}
	}
	
	private void insertUser(UserVO userVO) throws Exception {
        PreparedStatement ps = null;
        try {
        	
    		UUID uuid = UUID.randomUUID();
			String tokenEmail = uuid.toString();
        	
            ps = conn.prepareStatement("INSERT INTO tbuser ( "
                    + "   email, " 
                    + "   name, " 
                    + "   password, "
                    + "   tokenEmail "
                    + " ) VALUES ( "
                    + "   ?, "
                    + "   ?, "
                    + "   ?, "
                    + "   ? "
                    + " ) ");
            ps.setString(1, userVO.getEmail());
            ps.setString(2, userVO.getName());
            ps.setString(3, userVO.getPassword());
            ps.setString(4, tokenEmail);
            ps.executeUpdate();
            
            SendMail.sendEmail("luizfernandocamposdossantos@gmail.com", "teste01", "teste01");

        } finally {
            if(ps != null) {
            	ps.close();
            }
        }
    }
	
	private void updatetUser(UserVO userVO) throws Exception {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("update tbuser set "
                    + "   email = ?, " 
                    + "   name = ?, " 
                    + "   password = ? where id = ? and email = ?");
            ps.setString(1, userVO.getEmail());
            ps.setString(2, userVO.getName());
            ps.setString(3, userVO.getPassword());
            ps.setInt(4, userVO.getId());
            ps.setString(5, userVO.getEmail());
            ps.executeUpdate();

        } finally {
            if(ps != null) {
            	ps.close();
            }
        }
    }


	public String authenticatesUser(String email, String password) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select * from tbuser" + "  where email = ? and password = ?");
			ps.setString(1, email);
			ps.setString(2, password);
			rs = ps.executeQuery();

			UUID uuid = UUID.randomUUID();
			String token = uuid.toString();

			if (rs.next()) {

				UserVO userVO = setUser(rs);
				ps.close();
				ps = conn
						.prepareStatement("update tbuser set lastAcess = ?, token = ? " + "where id = ? and email = ?");
				ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
				ps.setString(2, uuid.toString());
				ps.setInt(3, userVO.getId());
				ps.setString(4, userVO.getEmail());
				ps.executeUpdate();

				return token;
			}

			return null;

		} finally {

			if (rs != null) {
				rs.close();
			}

			if (ps != null) {
				ps.close();
			}

		}
	}

	public void finishSession(String token) throws Exception {
		PreparedStatement ps = null;
		try {

			ps = conn.prepareStatement("update tbuser set lastAcess = ?, token = ? where token = ?");
			ps.setTimestamp(1, null);
			ps.setString(2, null);
			ps.setString(3, token);
			ps.executeUpdate();

		} finally {

			if (ps != null) {
				ps.close();
			}

		}
	}

	public void setLastAcess(UserVO userVO) throws Exception {
		PreparedStatement ps = null;
		try {

			ps = conn.prepareStatement("update tbuser set lastAcess = ? where id = ? and email = ?");
			ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			ps.setInt(2, userVO.getId());
			ps.setString(3, userVO.getEmail());
			ps.executeUpdate();

		} finally {

			if (ps != null) {
				ps.close();
			}

		}
	}

	public UserVO getUserByToken(String token) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("select * from tbuser where token = ?");
			ps.setString(1, token);
			rs = ps.executeQuery();

			if (rs.next()) {
				return this.setUser(rs);
			}

		} finally {

			if (rs != null) {
				rs.close();
			}

			if (ps != null) {
				ps.close();
			}

		}
		return null;
	}

	private UserVO setUser(ResultSet rs) throws Exception {
		UserVO user =  new UserVO();
		user.setEmail(rs.getString("email"));
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setToken(rs.getString("token"));
		user.setLastAcess(rs.getTimestamp("lastAcess"));

		return user;
	}

}
