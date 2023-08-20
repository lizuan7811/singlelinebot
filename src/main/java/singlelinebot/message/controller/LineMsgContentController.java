package singlelinebot.message.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import singlelinebot.properties.LineBotProperties;
import singlelinebot.service.SingleLineBotService;
import singlelinebot.util.HttpUtil;

@Controller
@Slf4j
@RequestMapping("/local/")
public class LineMsgContentController {

	private final OkHttpClient client = HttpUtil.getOkHttpClient();
	private final LineBotProperties lineBotProperties;
	private final SingleLineBotService singleLineBotService;
	private OutputStream baos = null;

	private String BARETOKEN = Strings.EMPTY;

	@Autowired
	public LineMsgContentController(LineBotProperties lineBotProperties, SingleLineBotService singleLineBotService) {
		this.lineBotProperties = lineBotProperties;
		this.singleLineBotService = singleLineBotService;
		BARETOKEN = "Bearer " + lineBotProperties.getLineBotChannelToken();
	}

	@GetMapping("/getcontent")
	public void linkContent(@RequestParam(value = "redirectUrl", required = true) String redirectUrl) {
		saveContent(redirectUrl);
	}

	private void saveContent(String redirectUrl) {
//		
//		String url = lineBotProperties.getLineBotApiDataBaseUrl()
//				+ lineBotProperties.getContentUri().replace("{messageId}", messageId);
		Request request = new Request.Builder().url(redirectUrl).header("Authorization", BARETOKEN).get().build();

		client.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call arg0, IOException ioe) {
				log.debug("{}", ioe);
			}

			@Override
			public void onResponse(Call arg0, Response resp) throws IOException {
				log.debug("~~{}", resp);
				String filePath = "C:/Users/Lizuan/Desktop/TESTFOLDER/testdata.mp4";
				try (InputStream is = resp.body().byteStream();
						FileOutputStream baos = new FileOutputStream(filePath);) {
					byte buffer[] = new byte[512];
					int length = 0;
					while ((length = is.read(buffer)) != -1) {
						baos.write(buffer, 0, length);
					}
				} catch (Exception e) {
					log.debug(">>> error: {}", e.getMessage());
				}
			}
		});
	}
}
