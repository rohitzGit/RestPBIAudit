package com.cerner.rest.services;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import com.cerner.rest.filter.AuthenticationRequestFilter;

public class RestServices {
	
	private static final String BASE_TARGET_URL = "http://wservice.cerner.com/remedyrest/v2";
	
	
	Client client = ClientBuilder.newClient().register(new AuthenticationRequestFilter());	
	public Invocation getWorkLogInvocation(String problemId) {
		Invocation invocation = null;
		WebTarget workLog = client.target(BASE_TARGET_URL)
												.path("problems")
												.path("{id}")
												.path("worklogs");
		
		invocation = workLog.resolveTemplate("id", problemId)
											.request(MediaType.APPLICATION_JSON).buildGet();
	/*	if(invocation != null)
		{
			System.out.println("URL : " + workLog + "invocation : " + invocation + "Problem id : " + problemId);
		}
		*/
		return invocation;
	}

}
