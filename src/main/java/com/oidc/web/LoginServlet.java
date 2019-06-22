package com.oidc.web;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nimbusds.oauth2.sdk.id.ClientID;
import com.nimbusds.openid.connect.sdk.op.OIDCProviderMetadata;
import com.oidc.oidc_implementation.*;
import com.oidc.utils.PropertiesReader;

import static java.net.URLEncoder.encode;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Properties;
import java.util.UUID;

@WebServlet(urlPatterns = {"/login"}, loadOnStartup = 1)
public class LoginServlet extends HttpServlet {

	PropertiesReader propertiesReader = new PropertiesReader();
	
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
    	Properties properties = propertiesReader.getAllProperties();

    	OIDCProviderConfigurationRequestCreator configurationRequestCreator = new OIDCProviderConfigurationRequestCreator();
    	OIDCProviderMetadata opMetadata = configurationRequestCreator.OIDCMetadataBuilder();
    	
    	// The client identifier provisioned by the server
    	ClientID clientID = new ClientID(properties.getProperty("clientId"));
    	
    	// The client secret provisioned by the server
    	String callback_url = properties.getProperty("redirectUri");
    	
    	// The Scope to ask to Authorization Server
    	String Scope = properties.getProperty("scope");
    	
    	// state is a value used to maintain state between the request and the callback. Actually not used in this application.
        String state = UUID.randomUUID().toString();

        String URL = String.format("%s?client_id=%s&redirect_uri=%s&response_type=%s&scope=%s&state=%s&nonce=%s",
        		opMetadata.getAuthorizationEndpointURI(), clientID, encoded(callback_url), "code", encoded(Scope), encoded(state),
                "somecorrelationnonce");
        
        response.sendRedirect(URL);
    }
    
    
    
    /**
     * Set utf-8-encoding for string.
     */
    private static String encoded(String s) {
        try {
            return encode(s, Charset.forName("UTF-8").name());
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }
}