package com.hifun.soul.gameserver.sprite.msg.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.sprite.model.SpritePubInfo;
import com.hifun.soul.gameserver.sprite.model.SpritePubPageInfo;
import com.hifun.soul.gameserver.sprite.msg.CGOpenPubPanel;
import com.hifun.soul.gameserver.sprite.msg.GCFingerGuessing;
import com.hifun.soul.gameserver.sprite.msg.GCOpenPubPanel;
import com.hifun.soul.gameserver.sprite.template.SpriteTemplateManager;

/**
 * 请求打开酒馆面板响应;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGOpenPubPanelHandler implements
		IMessageHandlerWithType<CGOpenPubPanel> {
	@Autowired
	private SpriteTemplateManager templateManager;
	private static final int FIRST_PAGE = 1;

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_PUB_PANEL;
	}

	@Override
	public void execute(CGOpenPubPanel message) {
		Player player = message.getPlayer();
		if (player == null) {
			return;
		}
		Human human = player.getHuman();
		if (human == null) {
			return;
		}
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.SPRITE_PUB, true)) {
			return;
		}
		// 页码
		int pageId = ((message.getPageId() <= 0) ? FIRST_PAGE : message
				.getPageId());
		if (pageId > templateManager.getMaxPage()) {
			return;
		}
		// 等级是否满足
		SpritePubPageInfo pageInfo = templateManager.getPageInfoByPageId(pageId);
		if (pageInfo == null) {
			return;	
		}
		if (pageInfo.getPageOpenLevel() > human.getLevel()) {
			// 提示精灵已经达到满级;
			human.sendErrorMessage(LangConstants.SPRITE_PUB_HUMAN_LEVEL_LIMITED, pageInfo.getPageOpenLevel());
			return;
		}
		if (!human.getHumanSpritePubManager().finishedLashFingerGuess()) {
			// 还有上次的酒没对完,则先转到猜拳界面;
			GCFingerGuessing lastGuessMessage = human
					.getHumanSpritePubManager().buildLastGuessMessage();
			human.sendMessage(lastGuessMessage);
			return;
		}
		// 构建打开酒馆面板的消息;
		juseOpenPub(human, pageId);
		// 新手引导
		human.getHumanGuideManager().showGuide(
				GuideType.OPEN_SPRITE_PUB_PANEL.getIndex());

	}

	/**
	 * 打开酒馆面板;
	 * 
	 * @param human
	 * @param pageId
	 */
	private void juseOpenPub(Human human, int pageId) {
		List<SpritePubInfo> sprites = templateManager
				.getSpritePubInfoByPageId(pageId);
		GCOpenPubPanel openPubMessage = new GCOpenPubPanel();
		openPubMessage.setPageId(pageId);
		openPubMessage.setSpriteList(sprites.toArray(new SpritePubInfo[0]));
		human.sendMessage(openPubMessage);
	}

}
