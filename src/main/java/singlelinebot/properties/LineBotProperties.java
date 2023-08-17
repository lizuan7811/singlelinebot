package singlelinebot.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class LineBotProperties {
	@Value("${line.bot.channelSecret}")
	private String lineMsgChannelSecret;
	
	@Value("${line.bot.channelToken}")
	private String lineMsgChannelToken;
	
	@Value("${line.callback.reply}")
	private String lineCallbackReply;
	
	@Value("${line.callback.push}")
	private String lineCallbackPush;
	
	@Value("${line.user.id}")
	private String lineUserId;
	
}
