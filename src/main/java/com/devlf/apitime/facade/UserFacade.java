package com.devlf.apitime.facade;

import java.sql.Connection;
import java.sql.Timestamp;

import com.devlf.apitime.dao.FactoryDAO;
import com.devlf.apitime.dao.UserDAO;
import com.devlf.apitime.util.Util;
import com.devlf.apitime.vo.UserVO;

public class UserFacade {
	
	public void save(UserVO userVO) throws Exception {
		Connection conn = null;
		try {

			conn = FactoryDAO.getConnection();
			UserDAO userDAO = new UserDAO(conn);

			userDAO.save(userVO);

		} finally {

			if (conn != null) {
				conn.close();
			}
		}
	}

	public String authenticatesUser(String email, String password) throws Exception {

		Connection conn = null;
		try {

			conn = FactoryDAO.getConnection();
			UserDAO userDAO = new UserDAO(conn);

			return userDAO.authenticatesUser(email, password);

		} finally {

			if (conn != null) {
				conn.close();
			}
		}

	}

	public UserVO getUserByToken(String token) throws Exception {
		Connection conn = null;
		try {

			conn = FactoryDAO.getConnection();
			UserDAO userDAO = new UserDAO(conn);

			return userDAO.getUserByToken(token);

		} finally {

			if (conn != null) {
				conn.close();
			}
		}

	}

	public void finishSession(String token) throws Exception {

		Connection conn = null;
		try {

			conn = FactoryDAO.getConnection();
			UserDAO userDAO = new UserDAO(conn);

			userDAO.finishSession(token);

		} finally {

			if (conn != null) {
				conn.close();
			}
		}
	}

	public void setLastAcess(UserVO userVO) throws Exception {
		Connection conn = null;
		try {

			conn = FactoryDAO.getConnection();
			UserDAO userDAO = new UserDAO(conn);

			userDAO.setLastAcess(userVO);

		} finally {

			if (conn != null) {
				conn.close();
			}
		}

	}

	public boolean isTokenValido(String token) throws Exception {
		Connection conn = null;
		try {
			conn = FactoryDAO.getConnection();
			UserVO userVO = getUserByToken(token);
			if (userVO != null) {
				if (Util.getMinutesBetweenTimeStamps(new Timestamp(System.currentTimeMillis()),
						userVO.getLastAcess()) <= 20) {

					setLastAcess(userVO);

					return true;
				}
			}
		} finally {

			if (conn != null) {
				conn.close();
			}
		}
		return false;
	}

}
