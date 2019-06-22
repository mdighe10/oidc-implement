package com.oidc.token;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nimbusds.jwt.JWT;
import com.nimbusds.oauth2.sdk.*;
import com.nimbusds.oauth2.sdk.auth.ClientAuthentication;
import com.nimbusds.oauth2.sdk.auth.ClientSecretBasic;
import com.nimbusds.oauth2.sdk.auth.Secret;
import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponse;
import com.nimbusds.openid.connect.sdk.OIDCTokenResponseParser;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.oidc.oidc_implementation.OIDCProviderConfigurationRequestCreator;
import com.oidc.oidc_implementation.OIDCUtils;
import com.oidc.utils.PropertiesReader;

public class TokenRequestCreator {

	PropertiesReader propertiesReader = new PropertiesReader();
	public OIDCUtils requestToken(String authorizationCode) {
		
		
	    Properties properties = propertiesReader.getAllProperties();
		
		OIDCUtils oidcUtils = null;

		try {
			// Construct the code grant from the code obtained from the authz endpoint
			// and the original callback URI used at the authorization endpoint
			AuthorizationCode code = new AuthorizationCode(authorizationCode);
			URI callback_url = new URI(properties.getProperty("redirectUri"));
			AuthorizationGrant codeGrant = new AuthorizationCodeGrant(code, callback_url);

			// The credentials to authenticate the client at the token endpoint
			ClientID clientID = new ClientID(properties.getProperty("clientId"));
			Secret clientSecret = new Secret(properties.getProperty("clientSecret"));
			ClientAuthentication clientAuth = new ClientSecretBasic(clientID, clientSecret);

			OIDCProviderConfigurationRequestCreator configurationRequestCreator = new OIDCProviderConfigurationRequestCreator();
			OIDCProviderMetadata opMetadata = configurationRequestCreator.OIDCMetadataBuilder();

			// The token endpoint
			URI tokenEndpoint = new URI(opMetadata.getTokenEndpointURI().toString());

			// Make the token request
			TokenRequest request = new TokenRequest(tokenEndpoint, clientAuth, codeGrant);

			TokenResponse tokenResponse = OIDCTokenResponseParser.parse(request.toHTTPRequest().send());

			if (!tokenResponse.indicatesSuccess()) {
				// We got an error response...
				TokenErrorResponse errorResponse = tokenResponse.toErrorResponse();
				System.out.println("Token Error Response : " + errorResponse.toString());
				return oidcUtils;
			}
			OIDCTokenResponse successResponse = (OIDCTokenResponse) tokenResponse.toSuccessResponse();

			// Get the ID and access token, the server may also return a refresh token
			JWT idToken = successResponse.getOIDCTokens().getIDToken();
			AccessToken accessToken = successResponse.getOIDCTokens().getAccessToken();
			RefreshToken refreshToken = successResponse.getOIDCTokens().getRefreshToken();
			
			oidcUtils = new OIDCUtils();
			
			oidcUtils.setCode(code);
			oidcUtils.setAccessToken(accessToken);
			oidcUtils.setIdToken(idToken);
			oidcUtils.setRefreshToken(refreshToken);
			
			return oidcUtils;

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;

	}

}
