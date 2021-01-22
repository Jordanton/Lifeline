package com.itafin.lifeline.service;

public interface CaptchaService {	 
	 public void processResponse(String response, String clientIp) throws Exception;
}
