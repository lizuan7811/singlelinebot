package singlelinebot.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@Data
public class LineBotProperties {
	@Value("${line.msgchannel.secret}")
	private String lineMsgChannelSecret;
	
	@Value("${line.msgchannel.token}")
	private String lineMsgChannelToken;
	
	@Value("${line.callback.reply}")
	private String lineCallbackReply;
	
	@Value("${line.callback.push}")
	private String lineCallbackPush;
	
	@Value("${line.user.id}")
	private String lineUserId;
	
}
