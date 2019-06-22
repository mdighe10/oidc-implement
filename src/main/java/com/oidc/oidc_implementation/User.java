package com.oidc.oidc_implementation;

import org.json.JSONObject;

import com.nimbusds.jwt.JWT;
import com.nimbusds.oauth2.sdk.token.AccessToken;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;

public class User {

    private final AccessToken accessToken;
    private final JWT idToken;
    private final UserInfo userInfo;
    
    public User(AccessToken accessToken, JWT idToken, UserInfo userInfo) {
        this.accessToken = accessToken;
        this.idToken = idToken;
        this.userInfo = userInfo;
    }

    public AccessToken getAccessToken() {
        return accessToken;
    }
    public JWT getIdToken() { return idToken; }
    
    public UserInfo getuserInfo() { return userInfo; }
    

}
