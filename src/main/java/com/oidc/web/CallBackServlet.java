package com.oidc.web;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;
import com.nimbusds.oauth2.sdk.token.BearerAccessToken;
import com.nimbusds.openid.connect.sdk.UserInfoRequest;
import com.nimbusds.openid.connect.sdk.UserInfoResponse;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.oidc.oidc_implementation.OIDCProviderConfigurationRequestCreator;
import com.oidc.oidc_implementation.OIDCUtils;
import com.oidc.oidc_implementation.User;
import com.oidc.oidc_implementation.UserInfoRequestCreator;
import com.oidc.token.TokenRequestCreator;

import java.io.IOException;

@WebServlet(urlPatterns = { "/callback" }, loadOnStartup = 1)
public class CallBackServlet extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String code = request.getParameter("code");

		TokenRequestCreator tokenRequestCreator = new TokenRequestCreator();

		OIDCUtils oidcUtils = tokenRequestCreator.requestToken(code);

		if (oidcUtils != null) {

			UserInfoRequestCreator userInfoRequestCreator = new UserInfoRequestCreator();

			UserInfo userInfo = userInfoRequestCreator.requestUserInfo(oidcUtils);

			if (userInfo != null) {
				User user = new User(oidcUtils.getAccessToken(), oidcUtils.getIdToken(), userInfo);

				request.getSession().setAttribute("user", user);
				response.sendRedirect("/oidc-implement/");
			} else {
				response.sendRedirect("/oidc-implement/error");
			}
			// BearerAccessToken bearerAccessToken =
			// userInfoRequestCreator.requestUserInfo(oidcUtils);

			// HTTPResponse httpResponse = new
			// UserInfoRequest(opMetadata.getUserInfoEndpointURI(),
			// bearerAccessToken).toHTTPRequest().send();
			/*
			 * UserInfoResponse userInfoResponse; try { userInfoResponse =
			 * UserInfoResponse.parse(httpResponse);
			 * 
			 * if (! userInfoResponse.indicatesSuccess()) { // The request failed, e.g. due
			 * to invalid or expired token
			 * System.out.println(userInfoResponse.toErrorResponse().getErrorObject().
			 * getCode());
			 * System.out.println(userInfoResponse.toErrorResponse().getErrorObject().
			 * getDescription()); return; }
			 * 
			 * // Extract the claims UserInfo userInfo =
			 * userInfoResponse.toSuccessResponse().getUserInfo();
			 * 
			 * System.out.println("Subject: " + userInfo.getSubject());
			 * System.out.println("Email: " + userInfo.getEmailAddress());
			 * System.out.println("Name: " + userInfo.getName());
			 * 
			 * if (userInfo != null) {
			 * 
			 * response.sendRedirect("/oidc-implement/"); } else {
			 * response.sendRedirect("/oidc-implement/error"); } } catch (ParseException e)
			 * { // TODO Auto-generated catch block e.printStackTrace(); }
			 */

			// UserInfo userInfo =
			// userInfoRequestCreator.getUserInfo((BearerAccessToken)oidcUtils.getAccessToken(),
			// opMetadata.getTokenEndpointURI());

		}

	}
}