package singlelinebot.notify;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.event.DocumentEvent.EventType;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.AudioMessageContent;
import com.linecorp.bot.model.event.message.FileMessageContent;
import com.linecorp.bot.model.event.message.ImageMessageContent;
import com.linecorp.bot.model.event.message.LocationMessageContent;
import com.linecorp.bot.model.event.message.StickerMessageContent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.message.VideoMessageContent;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import singlelinebot.properties.LineBotProperties;
import singlelinebot.service.SingleLineBotService;
import singlelinebot.util.HttpUtil;

@Slf4j
@LineMessageHandler
public class EventHandler {

	private Request request = null;

	private final OkHttpClient client = HttpUtil.getOkHttpClient();

	private final LineBotProperties lineBotProperties;

	private String BARETOKEN = Strings.EMPTY;

	private final SingleLineBotService singleLineBotService;
	private DataOutputStream dataOut= null;

	@Autowired
	public EventHandler(LineBotProperties lineBotProperties, SingleLineBotService singleLineBotService) {
		this.lineBotProperties = lineBotProperties;
		this.singleLineBotService = singleLineBotService;
		BARETOKEN = "Bearer " + lineBotProperties.getLineBotChannelToken();
	}

	@EventMapping
	public void textEvent(MessageEvent<TextMessageContent> eventContent) {
		log.debug(">>> textEvent");
		log.debug("{}", eventContent);
		log.debug("{}", eventContent.getMessage());
		String url = getContent(eventContent.getMessage().getId());
		singleLineBotService.replyTextMessage(eventContent.getReplyToken(), url);

	}

	@EventMapping
	public void imageEvent(MessageEvent<ImageMessageContent> eventContent) {
		log.debug(">>> imageEvent");
		log.debug("{}", eventContent);
		log.debug("{}", eventContent.getMessage());
		String url = getContent(eventContent.getMessage().getId());
		singleLineBotService.replyTextMessage(eventContent.getReplyToken(), wrappedUrl(url));
	}

	@EventMapping
	public void locationEvent(MessageEvent<LocationMessageContent> eventContent) {
		log.debug(">>> locationEvent");
		log.debug("{}", eventContent);
		log.debug("{}", eventContent.getMessage());
		String url = getContent(eventContent.getMessage().getId());
		singleLineBotService.replyTextMessage(eventContent.getReplyToken(), url);

	}

	@EventMapping
	public void audioEvent(MessageEvent<AudioMessageContent> eventContent) {
		log.debug(">>> audioEvent");
		log.debug("{}", eventContent);
		log.debug("{}", eventContent.getMessage());
		String url = getContent(eventContent.getMessage().getId());
		singleLineBotService.replyTextMessage(eventContent.getReplyToken(), wrappedUrl(url));

	}

	@EventMapping
	public void videoEvent(MessageEvent<VideoMessageContent> eventContent) {
		log.debug(">>> videoEvent");
		log.debug("{}", eventContent);
		log.debug("{}", eventContent.getMessage());
		String url = getContent(eventContent.getMessage().getId());
		singleLineBotService.replyTextMessage(eventContent.getReplyToken(), wrappedUrl(url));
	}

	@EventMapping
	public void stickerEvent(MessageEvent<StickerMessageContent> eventContent) {
		log.debug(">>> stickerEvent");
		log.debug("{}", eventContent);
		log.debug("{}", eventContent.getMessage());
		String url = getContent(eventContent.getMessage().getId());
		singleLineBotService.replyTextMessage(eventContent.getReplyToken(), url);

	}

	@EventMapping
	public void fileEvent(MessageEvent<FileMessageContent> eventContent) {
		log.debug(">>> fileEvent");
		log.debug("{}", eventContent);
		log.debug("{}", eventContent.getMessage());
		String url = getContent(eventContent.getMessage().getId());
		singleLineBotService.replyTextMessage(eventContent.getReplyToken(), wrappedUrl(url));

	}
	private String wrappedUrl(String url) {
		return "http://localhost:8081/local/getcontent?redirectUrl="+url;
	}

	private String getContent(String messageId) {
		String url = lineBotProperties.getLineBotApiDataBaseUrl()
				+ lineBotProperties.getContentUri().replace("{messageId}", messageId);
//		request = new Request.Builder().url(url).header("Authorization", BARETOKEN).get().build();
//		
//		client.newCall(request).enqueue(new Callback() {
//			@Override
//			public void onFailure(Call arg0, IOException ioe) {
//				log.debug("{}", ioe);
//			}
//
//			@Override
//			public void onResponse(Call arg0, Response resp) throws IOException {
//				log.debug("~~{}", resp);
//			}
//		});
		return url;
	}

//	private OutputStream getContentF(String messageId) {
//
//		String url = lineBotProperties.getLineBotApiDataBaseUrl()
//				+ lineBotProperties.getContentUri().replace("{messageId}", messageId);
//		Request request = new Request.Builder().url(url).header("Authorization", BARETOKEN).get().build();
//
//		client.newCall(request).enqueue(new Callback() {
//			@Override
//			public void onFailure(Call arg0, IOException ioe) {
//				log.debug("{}", ioe);
//			}
//
//			@Override
//			public void onResponse(Call arg0, Response resp) throws IOException {
//				log.debug("~~{}", resp);
//				InputStream is = resp.body().byteStream();
//				ByteArrayOutputStream baos = new ByteArrayOutputStream();
//				byte buffer[] = new byte[512];
//				int length = 0;
//				while ((length = is.read(buffer)) != -1) {
//					baos.write(buffer, 0, length);
//				}
//				// ByteArrayOutputStream轉成位元陣列
//				dataOut=new DataOutputStream(baos);
//				byte data[] = baos.toByteArray();
////				baos.close();
////				is.close();
//			}
//		});
//		return dataOut;
//	}
}
