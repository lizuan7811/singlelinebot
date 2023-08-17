package singlelinebot.notify;

import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import singlelinebot.service.SingleLineBotService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@LineMessageHandler
public class DefaultHandler {
	
	private final SingleLineBotService singleLineBotService;
	
	public DefaultHandler(SingleLineBotService singleLineBotService) {
		this.singleLineBotService=singleLineBotService;
	}

	@EventMapping
	public void defaultEvent(Event event) {
		log.debug(">>> default Event: {}",event);
	}
//	第一次加入好友，會執行這個訊息
	@EventMapping
	public void followEvent(FollowEvent event)throws Exception{
		log.debug(">>> UserId: {}",event.getSource().getUserId());
		log.debug(">>> ReplyToken: {}",event.getReplyToken());

		singleLineBotService.getLineUserInfo(event.getSource().getUserId());
		singleLineBotService.replyTextMessage(event.getReplyToken(),"測試使用TEST!");
	}
	
	
}
