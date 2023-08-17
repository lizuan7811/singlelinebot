package singlelinebot.message.service;

import org.springframework.stereotype.Service;


public interface LineBotApiService {
	
	boolean checkFromLine(String requestBody,String xLineSignature);

}
