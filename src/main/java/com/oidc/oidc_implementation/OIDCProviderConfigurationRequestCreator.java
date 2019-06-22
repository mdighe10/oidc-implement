package com.oidc.oidc_implementation;

import java.io.IOException;
import java.util.Properties;

import com.nimbusds.oauth2.sdk.ParseException;
import com.nimbusds.oauth2.sdk.http.*;
import com.nimbusds.oauth2.sdk.id.Issuer;
import com.nimbusds.openid.connect.sdk.op.*;
import com.oidc.utils.PropertiesReader;

public class OIDCProviderConfigurationRequestCreator {
	
	PropertiesReader propertiesReader = new PropertiesReader();

	public OIDCProviderMetadata OIDCMetadataBuilder() {
		Properties properties = propertiesReader.getAllProperties();

		OIDCProviderMetadata opMetadata = null;
		// The OpenID provider issuer URL
		Issuer issuer = new Issuer(properties.getProperty("Issuer"));

		OIDCProviderConfigurationRequest request = new OIDCProviderConfigurationRequest(issuer);

		// Make HTTP request
		HTTPRequest httpRequest = request.toHTTPRequest();
		HTTPResponse httpResponse;
		try {
			httpResponse = httpRequest.send();

			// Parse OpenID provider metadata
			opMetadata = OIDCProviderMetadata.parse(httpResponse.getContentAsJSONObject());
			assert issuer.equals(opMetadata.getIssuer());

			return opMetadata;
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
