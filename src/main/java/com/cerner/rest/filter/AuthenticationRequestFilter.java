package com.cerner.rest.filter;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthenticationRequestFilter implements ClientRequestFilter{
	
	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		
		/*~*~*~*~* Cert User *~*~*~*~*/
		//requestContext.getHeaders().add("Authorization", "Basic UFIwMzE1NDE6U2hhZG93Y29uNEAy");
		
		/*~*~*~*~* Prod User *~*~*~*~*/
		requestContext.getHeaders().add("Authorization", "Basic U1d4U2VydmljZTpBXzA5XzE4XzEy");
		
	}

}
