package singlelinebot.linebot.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;


import javax.crypto.Mac;
import javax.crypto.spec.*;
import lombok.extern.slf4j.Slf4j;
import singlelinebot.common.MessageHandler;
import singlelinebot.service.LineBotApiService;

import org.json.*;


@RestController
@Slf4j
public class LineBotController {
	
	private final MessageHandler messageHandler;
	
	private final LineBotApiService lineBotApiService;
	
	public LineBotController(MessageHandler messageHandler,LineBotApiService lineBotApiService) {
		this.messageHandler=messageHandler;
		this.lineBotApiService=lineBotApiService;
	}

	@GetMapping("/hello")
	public String hello() {
		log.debug(">>> hello");
		return "hello";
	}

	@GetMapping("/test")
	public ResponseEntity test() {
		return new ResponseEntity("Hello J A V A", HttpStatus.OK);
	}
	
	@PostMapping("/messaging")
	public ResponseEntity messagingAPI(@RequestHeader("X-Line-Signature")String xLineSignature,@RequestBody String requestBody)throws UnsupportedEncodingException,IOException{
		if(checkFromLine(requestBody,xLineSignature)) {
			log.debug("Message start!");
			JSONObject object=new JSONObject(requestBody);
			for(int i=0;i<object.getJSONArray("events").length();i++) {
				 if(object.getJSONArray("events").getJSONObject(i).getString("type").equals("message")) {
					    messageHandler.doAction(object.getJSONArray("events").getJSONObject(i));
					   }
			}
			return new ResponseEntity<String>("OK",HttpStatus.OK);
		}
		log.debug("Message end!");
		return new ResponseEntity<String>("Not Line Platform",HttpStatus.BAD_GATEWAY);
	}
	
	private boolean checkFromLine(String requestBody,String xLineSignature) {
		
		return lineBotApiService.checkFromLine(requestBody, xLineSignature);
	}
}
