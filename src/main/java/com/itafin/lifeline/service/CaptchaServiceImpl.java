package com.itafin.lifeline.service;

import java.net.URI;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.itafin.lifeline.utils.LifelineProperties;

import oracle.net.jdbc.nl.InvalidSyntaxException;

@Service
public class CaptchaServiceImpl implements CaptchaService {
	
	@Autowired LifelineProperties lifelineProps;
	
	private static final Logger logger = LoggerFactory.getLogger(CaptchaServiceImpl.class);
	
    private static final String RESPONSE_PATTERN = "[A-Za-z0-9_-]+";    
	
    @Override
	public void processResponse(String response, String clientIp) throws Exception {
    	if(!responseSanityCheck(response)) {
            throw new InvalidSyntaxException("Response contains invalid characters");
        }
    	
     	String secretKey = lifelineProps.getProperties().getProperty("recaptcha.secretkey");
    	URI googleUrl = new URI(lifelineProps.getProperties().getProperty("recaptcha.siteverifyurl"));
    	
    	RestTemplate rest = new RestTemplate();

    	MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.add("secret", secretKey);
        request.add("remoteip", clientIp);
        request.add("response", response);

    	HttpEntity<String> googleResponse = rest.postForEntity(googleUrl, request, String.class);
		
		JSONObject responseJson = new JSONObject(googleResponse.getBody());
		if(!responseJson.getBoolean("success")) {
			logger.error("Failed recaptcha attempt by " + clientIp);
			logger.error("Error Codes: ");
			JSONArray errorCodes = responseJson.getJSONArray("error-codes"); 			
			for(int i = 0; i < errorCodes.length(); i++) {
				logger.error(errorCodes.getString(i));
			}
			throw new Exception("Recaptcha failed validation.");			
		} else {
			logger.info("Successful recaptcha attempt by " + clientIp);
			logger.info( responseJson.getString("challenge_ts") );
			logger.info( responseJson.getString("hostname") );
		}
	}
	
	private boolean responseSanityCheck(String response) {
        return StringUtils.hasText(response) && response.matches(RESPONSE_PATTERN);
    }
}
