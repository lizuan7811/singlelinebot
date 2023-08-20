package singlelinebot.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class LineBotProperties {	
	@Value("${line.bot.apidatabaseurl}")
	private String lineBotApiDataBaseUrl;
	
	@Value("${line.bot.channelSecret}")
	private String lineBotChannelSecret;
	
	@Value("${line.bot.channelToken}")
	private String lineBotChannelToken;
	
	@Value("${line.bot.replyuri}")
	private String lineBotReplyUri;
	
	@Value("${line.bot.pushuri}")
	private String lineBotPushUri;
	
	@Value("${line.user.id}")
	private String lineUserId;
	
	@Value("${line.bot.contenturi}")
	private String contentUri;


}
