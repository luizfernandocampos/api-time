package com.devlf.apitime.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import com.devlf.apitime.facade.JobFacade;
import com.devlf.apitime.facade.UserFacade;
import com.devlf.apitime.vo.JobVO;
import com.devlf.apitime.vo.UserVO;

@Path("job")
public class JobRest {

	@GET
	@Produces("application/json")
	@Path("getAllJobs")
	public Response getAllJobs(@DefaultValue("") @HeaderParam("token") String token) throws Exception {
		try {
			UserFacade userFacade = new UserFacade();
			UserVO userVO = userFacade.getUserByToken(token);

			if (userVO != null) {
				JobFacade jobFacade = new JobFacade();
				return Response.status(Response.Status.OK).entity(jobFacade.getAllJobs(userVO.getId())).build();

			} else {
				return Response.status(Response.Status.FORBIDDEN).entity("invalid user!").build();
			}

		} catch (Exception e) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
		}
	}
	
	@PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("setJobEvent")
    public Response setJobEvent(
            @HeaderParam("token") String token,
            JobVO jobVO
    )  {
        try {

            UserFacade userFacade = new UserFacade();
            UserVO userVO = userFacade.getUserByToken(token);

            if (userVO != null) {

            	JobFacade jobFacade = new JobFacade();
            	jobVO.setIdUser(userVO.getId());

                return Response.status(Response.Status.OK).entity(jobFacade.setJobEvent(jobVO)).build();
            } else {
                return Response
                        .status(Response.Status.FORBIDDEN)
                        .entity("invalid user")
                        .build();
            }

        } catch (Exception e) {
        	return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }
	
	
	@PUT
    @Consumes("application/json")
    @Produces("application/json")
    @Path("saveJob")
    public Response saveJob(
            @HeaderParam("token") String token,
            JobVO jobVO
    ) throws Exception {
        try {

            UserFacade userFacade = new UserFacade();
            UserVO userVO = userFacade.getUserByToken(token);

            if (userVO != null) {

            	jobVO.setIdUser(userVO.getId());
            	
            	JobFacade jobFacade = new JobFacade();
            	jobFacade.save(jobVO);

                return Response.status(Response.Status.OK).build();
            } else {
                return Response
                        .status(Response.Status.FORBIDDEN)
                        .entity("invalid user")
                        .build();
            }

        } catch (Exception e) {
        	return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }
    }

}
