package com.devlf.apitime.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.devlf.apitime.enuns.BooleanEnum;
import com.devlf.apitime.enuns.LogTypeEnum;
import com.devlf.apitime.vo.JobLogVO;
import com.devlf.apitime.vo.JobVO;

public class JobDAO extends GenericDAO {

	public JobDAO(Connection conn) {
		super(conn);
	}
	
	private JobVO setJob(ResultSet rs) throws Exception {
		JobVO jobVO = new JobVO();
		
		jobVO.setId(rs.getInt("id"));
		jobVO.setIdUser(rs.getInt("idUser"));
		jobVO.setJobCreateDate(rs.getTimestamp("jobCreateDate"));
		jobVO.setJobDetails(rs.getString("jobDetails"));
		jobVO.setJobFinishDate(rs.getTimestamp("jobFinishDate"));
		jobVO.setJobName(rs.getString("jobName"));
		jobVO.setJobValue(rs.getDouble("jobValue"));
		jobVO.setJobTimer(rs.getLong("jobTimer"));
		jobVO.setLastPauseDate(rs.getTimestamp("lastPauseDate"));
		jobVO.setLastStartDate(rs.getTimestamp("lastStartDate"));
		jobVO.setPlaying(BooleanEnum.YES.getCode().equals(rs.getString("isPlaying")));
		jobVO.setExpectedDateEnd(rs.getTimestamp("expectedDateEnd"));
		return jobVO;
				
	}
	
	private JobLogVO setJobLob(ResultSet rs) throws Exception {
		JobLogVO jobLogVO = new JobLogVO();
		
		jobLogVO.setId(rs.getInt("id"));
		jobLogVO.setId(rs.getInt("idJob"));
		jobLogVO.setJobLogDate(rs.getTimestamp("jobLogDate"));
		jobLogVO.setJobLogType(rs.getInt("jobLogType"));
		
		return jobLogVO;
				
	}
	
	public List<JobLogVO> getAllJobLogs(int idJob) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<JobLogVO> logs = new ArrayList<JobLogVO>();
		
		try {
			ps = conn.prepareStatement("select * from tbjoblog where idJob = ? order by jobLogDate desc");
			ps.setInt(1, idJob);
			rs = ps.executeQuery();

			while (rs.next()) {
				logs.add(this.setJobLob(rs));
			}
			
			return logs;

		} finally {

			if (rs != null) {
				rs.close();
			}

			if (ps != null) {
				ps.close();
			}

		}
	}
	
	public List<JobVO> getAllJobs(int idUser) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<JobVO> jobs = new ArrayList<JobVO>();
		
		try {
			ps = conn.prepareStatement("select * from tbjob where idUser = ? order by jobCreateDate desc");
			ps.setInt(1, idUser);
			rs = ps.executeQuery();

			while (rs.next()) {
				jobs.add(this.setJob(rs));
			}
			
			return jobs;

		} finally {

			if (rs != null) {
				rs.close();
			}

			if (ps != null) {
				ps.close();
			}

		}
	}
	
	public JobVO getJobByID(int id, int idUser) throws Exception {
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try {
			ps = conn.prepareStatement("select * from tbjob where id = ? and idUser = ?");
			ps.setInt(1, id);
			ps.setInt(2, idUser);
			rs = ps.executeQuery();

			if (rs.next()) {
				return this.setJob(rs);
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

	public void save(JobVO jobVO) throws Exception {
		if (jobVO.getId() == 0) {
			this.insertJob(jobVO);
		} else {
			this.updateJob(jobVO);
		}
	}
	 
	 
	 private void insertJob(JobVO jobVO) throws Exception {
	        PreparedStatement ps = null;
	        try {
	            ps = conn.prepareStatement("INSERT INTO tbJob ( "
	                    + "   idUser, " 
	                    + "   jobName, " 
	                    + "   jobDetails, "
	                    + "   jobCreateDate, "
	                    + "   jobTimer, "
	                    + "   expectedDateEnd, "
	                    + "   jobValue" 
	                    + " ) VALUES ( "
	                    + "   ?, "
	                    + "   ?, "
	                    + "   ?, "
	                    + "   ?, "
	                    + "   ?, "
	                    + "   ?, "
	                    + "   ? "
	                    + " ) ");
	            ps.setInt(1, jobVO.getIdUser());
	            ps.setString(2, jobVO.getJobName());
	            ps.setString(3, jobVO.getJobDetails());
	            ps.setTimestamp(4, new Timestamp((System.currentTimeMillis())));
	            ps.setLong(5, 0);
	            ps.setTimestamp(6, jobVO.getExpectedDateEnd());
	            ps.setDouble(7, jobVO.getJobValue());
	            ps.executeUpdate();

	        } finally {
	            if(ps != null) {
	            	ps.close();
	            }
	        }
	    }
	 
	 public void finishJob(JobVO jobVO) throws Exception {
	        PreparedStatement ps = null;
	        try {
	        	
	        	JobLogVO jobLogVO = new JobLogVO();
			 	jobLogVO.setIdJob(jobVO.getId());
			 	jobLogVO.setJobLogType(LogTypeEnum.FINISH.getCode());
			 	jobLogVO.setJobLogDate(jobVO.getJobFinishDate());
			 	 
			 	this.insertJobLog(jobLogVO);
	        	
	            ps = conn.prepareStatement("UPDATE tbJob set "
	                    + "   jobFinishDate = ?, isPlaying = ?, jobTimer = ? " 
	                    + " WHERE id = ? and idUser = ?");
	            ps.setTimestamp(1, jobVO.getJobFinishDate());
	            ps.setString(2, BooleanEnum.NO.getCode());
	            ps.setLong(3, jobVO.getJobTimer());
	            ps.setInt(4, jobVO.getId());
	            ps.setInt(5, jobVO.getIdUser());
	            ps.executeUpdate();

	        } finally {
	            if(ps != null) {
	            	ps.close();
	            }
	        }
	    }
	 
	 private void updateJob(JobVO jobVO) throws Exception {
	        PreparedStatement ps = null;
	        try {
	            ps = conn.prepareStatement("UPDATE tbJob set "
	                    + "   idUser = ?, " 
	                    + "   jobName = ?, " 
	                    + "   jobDetails = ?, " 
	                    + "   jobValue = ? " 
	                    + " WHERE id = ?");
	            ps.setInt(1, jobVO.getIdUser());
	            ps.setString(2, jobVO.getJobName());
	            ps.setString(3, jobVO.getJobDetails());
	            ps.setDouble(4, jobVO.getJobValue());
	            ps.setInt(5, jobVO.getId());
	            ps.executeUpdate();

	        } finally {
	            if(ps != null) {
	            	ps.close();
	            }
	        }
	    }
	 
		private void insertJobLog(JobLogVO jobLogVO) throws Exception {
			PreparedStatement ps = null;
			try {

				ps = conn.prepareStatement("INSERT tbjoblog (idJob, jobLogType, jobLogDate) values (?,?,?) ");
				ps.setInt(1, jobLogVO.getIdJob());
				ps.setInt(2, jobLogVO.getJobLogType());
				ps.setTimestamp(3, jobLogVO.getJobLogDate());
				ps.executeUpdate();

			} finally {
				if (ps != null) {
					ps.close();
				}
			}

		}
	 
	 
	 private void setJobPause(JobVO jobVO) throws Exception {
	 	PreparedStatement ps = null;
		 
		 try {
			 	JobLogVO jobLogVO = new JobLogVO();
			 	jobLogVO.setIdJob(jobVO.getId());
			 	jobLogVO.setJobLogType(LogTypeEnum.PAUSED.getCode());
			 	jobLogVO.setJobLogDate(jobVO.getLastPauseDate());
			 	 
			 	this.insertJobLog(jobLogVO);
			 	
				ps = conn.prepareStatement("UPDATE tbjob set jobTimer = ?, isPlaying = ?, lastPauseDate = ? where id = ? and idUser = ? ");
				ps.setLong(1,  jobVO.getJobTimer());
				ps.setString(2, BooleanEnum.NO.getCode());
				ps.setTimestamp(3, jobVO.getLastPauseDate());
				ps.setInt(4, jobVO.getId());
				ps.setInt(5, jobVO.getIdUser());
				ps.executeUpdate();
			 
			 
		}finally {
			if(ps != null) {
            	ps.close();
            }
		}
		 
	 }
	 
	 private void setJobPlay(JobVO jobVO) throws Exception {
		 
		 PreparedStatement ps = null;
		 
		 try {
			 
			JobLogVO jobLogVO = new JobLogVO();
		 	jobLogVO.setIdJob(jobVO.getId());
		 	jobLogVO.setJobLogType(LogTypeEnum.STARTED.getCode());
		 	jobLogVO.setJobLogDate(jobVO.getLastStartDate());
			this.insertJobLog(jobLogVO);

 			ps = conn.prepareStatement("UPDATE tbjob set jobTimer = ?, isPlaying = ?, lastStartDate = ? where id = ? and idUser = ? ");
 			ps.setLong(1,  jobVO.getJobTimer());
 			ps.setString(2, BooleanEnum.YES.getCode());
 			ps.setTimestamp(3, jobVO.getLastStartDate());
 			ps.setInt(4, jobVO.getId());
 			ps.setInt(5, jobVO.getIdUser());
 			ps.executeUpdate();
			 
			 
		}finally {
			 if(ps != null) {
            	ps.close();
            }
		}
		 
	 }
	 
	public void setJobEvent(JobVO jobVO) throws Exception {
		PreparedStatement ps = null;
		try {
			if (jobVO.getLastStartDate() != null) {
				
				if (jobVO.getJobFinishDate() != null) {
					this.finishJob(jobVO);
				} if (jobVO.isPlaying()) {
					this.setJobPause(jobVO);
				} else {
					this.setJobPlay(jobVO);
				}

			} else {
				this.setJobPlay(jobVO);
			}

		} finally {
			if (ps != null) {
				ps.close();
			}
		}
	}
	 
	 

}
