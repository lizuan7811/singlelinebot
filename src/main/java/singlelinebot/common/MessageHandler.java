package singlelinebot.common;

import java.io.IOException;

import org.json.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import singlelinebot.properties.LineBotProperties;

@Component
@Slf4j
public class MessageHandler {

	private OkHttpClient client = new OkHttpClient();

	@Autowired
	private LineBotProperties lineBotProperties;

	public void doAction(JSONObject event) {
		switch (event.getJSONObject("message").getString("type")) {
		case "text":
			text(event.getString("replyToken"), event.getJSONObject("message").getString("text"));
			break;
		case "sticker":
			sticker(event.getString("replyToken"), event.getJSONObject("message").getString("packageId"),event.getJSONObject("message").getString("stickerId"));
			break;
		}
	}
	
	private void text(String replyToken, String text) {
		  JSONObject body = new JSONObject();
		  JSONArray messages = new JSONArray();
		  JSONObject message = new JSONObject();
		  message.put("type", "text");
		  message.put("text", text);
		  messages.put(message);
		  body.put("replyToken", replyToken);
		  body.put("messages", messages);
		  sendLinePlatform(body);
		 }
		 private void sticker(String replyToken, String packageId, String stickerId) {
		  JSONObject body = new JSONObject();
		  JSONArray messages = new JSONArray();
		  JSONObject message = new JSONObject();
		  message.put("type", "sticker");
		  message.put("packageId", packageId);
		  message.put("stickerId", stickerId);
		  messages.put(message);
		  body.put("replyToken", replyToken);
		  body.put("messages", messages);
		  sendLinePlatform(body);
		 }

		 public void sendLinePlatform(JSONObject json) {
			 Request request=new Request.Builder().url("https://api.line.me/v2/bot/message/reply").header("Authorization","Bearer {"+lineBotProperties.getLineMsgChannelToken()+"}").post(RequestBody.create(MediaType.parse("application/json;charset=utf-8"),json.toString())).build();
			 client.newCall(request).enqueue(new Callback() {

				@Override
				public void onFailure(Call arg0, IOException arg1) {
					log.debug("{}",arg1);
				}

				@Override
				public void onResponse(Call arg0, Response arg1) throws IOException {
					log.debug("{}",arg1);
				}
			 });
		 }
}
