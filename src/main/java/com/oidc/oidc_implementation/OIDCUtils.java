package com.oidc.oidc_implementation;

import java.util.Properties;
import java.util.UUID;

import com.nimbusds.jwt.JWT;
import com.nimbusds.oauth2.sdk.AuthorizationCode;
import com.nimbusds.oauth2.sdk.id.State;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.oauth2.sdk.token.RefreshToken;
import com.nimbusds.openid.connect.sdk.Nonce;
import com.oidc.utils.PropertiesReader;

public class OIDCUtils {
	
	
	private AuthorizationCode code;
	private JWT idToken;
	private AccessToken accessToken;
	private RefreshToken refreshToken;
	
	public AuthorizationCode getCode() {
		return code;
	}
	public void setCode(AuthorizationCode code2) {
		this.code = code2;
	}
	public JWT getIdToken() {
		return idToken;
	}
	public void setIdToken(JWT idToke) {
		this.idToken = idToke;
	}
	public AccessToken getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(AccessToken accessToke) {
		this.accessToken = accessToke;
	}
	public RefreshToken getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(RefreshToken refreshToken) {
		this.refreshToken = refreshToken;
	}
	
	

}
