package singlelinebot.message.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Service;

import singlelinebot.message.service.LineBotApiService;
import singlelinebot.properties.LineBotProperties;
@Service
public class LineBotApiServiceImpl implements LineBotApiService {

	private final LineBotProperties lineBotProperties;
	
	public LineBotApiServiceImpl(LineBotProperties lineBotProperties) {
		this.lineBotProperties=lineBotProperties;
	}
	
	@Override
	public boolean checkFromLine(String requestBody, String xLineSignature) {
		SecretKeySpec key=new SecretKeySpec(lineBotProperties.getLineBotChannelSecret().getBytes(),"HmacSHA256");
		Mac mac;
		try {
			mac=Mac.getInstance("HmacSHA256");
			mac.init(key);
			byte[] source=requestBody.getBytes("UTF-8");
			String signature=Base64.encodeBase64String(mac.doFinal(source));
			
			if(signature.equals(xLineSignature)) {
				return true;
			}
		}catch(NoSuchAlgorithmException | InvalidKeyException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}

}
