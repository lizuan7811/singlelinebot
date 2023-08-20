package singlelinebot.common;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

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
	
	public void createRichMenu() {
		RichMenu richMenu = null;
		try {
			richMenu = RichMenu.builder().name("LYMenu")
					.areas(richMenuAreas())
					.chatBarText("CHAT_BAR_TEXT")
					.selected(true)
					.size(RichMenuSize.FULL).build();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		singleLineBotService.getClient().createRichMenu(richMenu);
	}
	
	private static List<RichMenuArea> richMenuAreas() throws URISyntaxException{
		return Arrays.asList(richMenuArea(0,0,"A"),
				richMenuArea(1,0,"B"),
				richMenuArea(2,0,"C"),
				richMenuArea(0,1,"D"),
				richMenuArea(1,1,"E"),
				richMenuArea(2,1,"F"));
	}
	
	private static RichMenuArea richMenuArea(final int xn,final int yn ,String name) throws URISyntaxException {
		final int X_STEP=883;
		final int Y_STEP=843;
		
		return new RichMenuArea(new RichMenuBounds(X_STEP*xn,Y_STEP*yn,X_STEP,Y_STEP),new  URIAction("LiffLogin", new URI("https://liff.line.me/2000468524-BGX34p74"), new AltUri(new URI("https://liff.line.me/2000468524-BGX34p74"))));
	}

	@Override
	public void run(String... args) throws Exception {
		createRichMenu();
	}
}
