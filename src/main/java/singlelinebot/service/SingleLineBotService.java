package singlelinebot.service;

import org.springframework.boot.CommandLineRunner;

import com.linecorp.bot.client.LineMessagingClient;

import singlelinebot.entity.LineUserInfo;

public interface SingleLineBotService extends CommandLineRunner {
	
	public LineUserInfo getLineUserInfo(String uid);
	
	public LineMessagingClient getClient();
	
	public void replyTextMessage(String replyToken,String message);
	
	
}
