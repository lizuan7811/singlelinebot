package singlelinebot.service.Impl;

import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.profile.*;

import lombok.extern.slf4j.Slf4j;
import singlelinebot.entity.LineUserInfo;
import singlelinebot.properties.LineBotProperties;
import singlelinebot.service.SingleLineBotService;

@Slf4j
@Service
public class SingleLineBotServiceImpl implements SingleLineBotService {

	private final LineBotProperties lineBotProperties;

	public SingleLineBotServiceImpl(LineBotProperties lineBotProperties) {
		this.lineBotProperties = lineBotProperties;
	}

	@Override
	public void run(String... args) throws Exception {
		log.debug("Nothing to run");
	}

	@Override
	public LineUserInfo getLineUserInfo(String uid) {
		LineUserInfo lineUserInfo = new LineUserInfo();
		try {
			log.debug(">>> SingleLineBotServiceImpl");
			UserProfileResponse userProfileResponse = getClient().getProfile(uid).get();

			lineUserInfo.setUid(uid);
			lineUserInfo.setDisplayName(userProfileResponse.getDisplayName());
			if(!ObjectUtils.isEmpty(userProfileResponse.getPictureUrl())) {
				lineUserInfo.setPictureUrl(userProfileResponse.getPictureUrl().toString());
			}
		} catch (InterruptedException | ExecutionException e) {
			log.debug("{}",e.getMessage());
		}
		return lineUserInfo;
	}

	@Override
	public LineMessagingClient getClient() {
		return LineMessagingClient.builder(lineBotProperties.getLineBotChannelToken()).build();
	}

	@Override
	public void replyTextMessage(String replyToken, String message) {
		replyMessage(new ReplyMessage(replyToken,new TextMessage(message)));
	}
	
	private void replyMessage(ReplyMessage replyMessage) {
		try {
			getClient().replyMessage(replyMessage).get();
		}catch(Exception e) {
			log.debug(e.getMessage());
		}
	}
}
