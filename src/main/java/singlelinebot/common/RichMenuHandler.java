package singlelinebot.common;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.action.URIAction;
import com.linecorp.bot.model.action.URIAction.AltUri;
import com.linecorp.bot.model.richmenu.RichMenu;
import com.linecorp.bot.model.richmenu.RichMenuArea;
import com.linecorp.bot.model.richmenu.RichMenuBounds;
import com.linecorp.bot.model.richmenu.RichMenuSize;

import lombok.extern.slf4j.Slf4j;
import singlelinebot.service.SingleLineBotService;

@Component
@Slf4j
public class RichMenuHandler implements CommandLineRunner{

	private final SingleLineBotService singleLineBotService;
	
	public RichMenuHandler(SingleLineBotService singleLineBotService) {
		this.singleLineBotService=singleLineBotService;
	}
	
	
	
	/**
	 * 建立RichMenu
	 */
	public String createRichMenu() throws InterruptedException, ExecutionException {
		RichMenu richMenu = null;
		try {
			richMenu = RichMenu.builder().name("LYMenu")
					.areas(richMenuAreas())
					.chatBarText("Liy Server")
					.selected(true)
					.size(RichMenuSize.FULL).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return singleLineBotService.getClient().createRichMenu(richMenu).get().getRichMenuId();
	}
	
	/**
	 * 設定line bottun位置及文字
	 */
	private static List<RichMenuArea> richMenuAreas() throws URISyntaxException{
		return Arrays.asList(
				richMenuArea(0,0,"系統管理主頁","https://liff.line.me/2000468524-BGX34p74"),
				richMenuArea(1,0,"個人資料","https://liff.line.me/2000468524-BGX34p74"),
				richMenuArea(2,0,"問題單","https://liff.line.me/2000468524-BGX34p74"),
				richMenuArea(0,1,"客服中心","https://liff.line.me/2000468524-BGX34p74"),
				richMenuArea(1,1,"好友名冊","https://liff.line.me/2000468524-BGX34p74"),
				richMenuArea(2,1,"最新消息","https://liff.line.me/2000468524-BGX34p74"));
	}
	
	private static RichMenuArea richMenuArea(final int xn,final int yn ,String name,String actionUrl) throws URISyntaxException {
		final int X_STEP=883;
		final int Y_STEP=843;
		
		return new RichMenuArea(new RichMenuBounds(X_STEP*xn,Y_STEP*yn,X_STEP,Y_STEP),new  URIAction("LiffLogin", new URI(actionUrl), new AltUri(new URI(actionUrl))));
	}

//	可以開發使用liff修改menu的功能
	
	
	@Override
	public void run(String... args) throws Exception {
//		TODO 判斷arias、richmenu有沒有，若有，就取得arias 、richmenuID存到屬性中保留，在每個使用者加好友時設定；
//		若沒有資料，就creat新的，檢查使用者有沒有menu，若無，就要設定menu。
		createRichMenu();
	}
}
