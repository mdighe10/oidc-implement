package com.oidc.oidc_implementation;

import java.io.IOException;
import java.net.URI;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.BearerAccessToken;
import com.nimbusds.openid.connect.sdk.UserInfoRequest;
import com.nimbusds.openid.connect.sdk.UserInfoResponse;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;

public class UserInfoRequestCreator {

	
	public UserInfo requestUserInfo(OIDCUtils oidcUtils) {
		
		OIDCProviderConfigurationRequestCreator configurationRequestCreator = new OIDCProviderConfigurationRequestCreator();
		OIDCProviderMetadata opMetadata = configurationRequestCreator.OIDCMetadataBuilder();

		URI userInfoEndpoint = opMetadata.getUserInfoEndpointURI(); // The UserInfoEndpoint of the OpenID provider
		//return (BearerAccessToken) oidcUtils.getAccessToken();
		
		try {
			HTTPResponse httpResponse = new UserInfoRequest(userInfoEndpoint, (BearerAccessToken) oidcUtils.getAccessToken()).toHTTPRequest().send();
			
			UserInfoResponse userInfoResponse = UserInfoResponse.parse(httpResponse);

			if (!userInfoResponse.indicatesSuccess()) {
				// The request failed, e.g. due to invalid or expired token
				System.out.println(userInfoResponse.toErrorResponse().getErrorObject().getCode());
				System.out.println(userInfoResponse.toErrorResponse().getErrorObject().getDescription());
				return null;
			}

			// Extract the claims
			UserInfo userInfo = userInfoResponse.toSuccessResponse().getUserInfo();
			System.out.println("Subject: " + userInfo.getSubject());
			System.out.println("Email: " + userInfo.getEmailAddress());
			System.out.println("Name: " + userInfo.getName());
			
			return userInfo;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public UserInfo getUserInfo(BearerAccessToken accessToken, URI userInfoEndpoint) {

		// Make the request
		try {
			HTTPResponse httpResponse = new UserInfoRequest(userInfoEndpoint, accessToken).toHTTPRequest().send();

			// Parse the response
			UserInfoResponse userInfoResponse = UserInfoResponse.parse(httpResponse);

			if (!userInfoResponse.indicatesSuccess()) {
				// The request failed, e.g. due to invalid or expired token
				System.out.println(userInfoResponse.toErrorResponse().getErrorObject().getCode());
				System.out.println(userInfoResponse.toErrorResponse().getErrorObject().getDescription());
				return null;
			}

			// Extract the claims
			UserInfo userInfo = userInfoResponse.toSuccessResponse().getUserInfo();
			System.out.println("Subject: " + userInfo.getSubject());
			System.out.println("Email: " + userInfo.getEmailAddress());
			System.out.println("Name: " + userInfo.getName());

			return userInfo;
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
