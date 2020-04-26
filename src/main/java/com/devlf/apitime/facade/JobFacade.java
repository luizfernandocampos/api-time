package com.devlf.apitime.facade;

import java.sql.Connection;
import java.util.List;

import com.devlf.apitime.dao.FactoryDAO;
import com.devlf.apitime.dao.JobDAO;
import com.devlf.apitime.vo.JobVO;

public class JobFacade {
	
	
	public List<JobVO> getAllJobs(int idUser) throws Exception {
		
		Connection conn = null;
		try {

			conn = FactoryDAO.getConnection();
			JobDAO jobDAO = new JobDAO(conn);

			return jobDAO.getAllJobs(idUser);

		} finally {

			if (conn != null) {
				conn.close();
			}
		}
	}

	public void save(JobVO jobVO) throws Exception {

		Connection conn = null;
		try {

			conn = FactoryDAO.getConnection();
			JobDAO jobDAO = new JobDAO(conn);

			jobDAO.save(jobVO);

		} finally {

			if (conn != null) {
				conn.close();
			}
		}

	}
	
		
	public JobVO setJobEvent(JobVO jobVO) throws Exception {
		
		Connection conn = null;
		try {
			
			conn = FactoryDAO.getConnection();
			conn.setAutoCommit(false);
			
			JobDAO jobDAO = new JobDAO(conn);
			
			jobDAO.setJobEvent(jobVO);

			conn.commit();
			
			return jobDAO.getJobByID(jobVO.getId(), jobVO.getIdUser()); 
			

		} catch (Exception e) {
			if (conn != null) {
				conn.rollback();
			}
		} finally {

			if (conn != null) {
				conn.close();
			}
		}
		return null;
	}


}
