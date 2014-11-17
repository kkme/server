package com.hifun.soul.gameserver.sign.msg.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.sign.msg.CGOpenStarMapPanel;
import com.hifun.soul.gameserver.sign.msg.GCOpenStarMapPanel;
import com.hifun.soul.gameserver.sprite.model.SpriteSignInfo;
import com.hifun.soul.gameserver.sprite.model.SpriteStarMapInfo;

/**
 * 请求打开星图
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGOpenStarMapPanelHandler implements
		IMessageHandlerWithType<CGOpenStarMapPanel> {

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_STAR_MAP_PANEL;
	}

	@Override
	public void execute(CGOpenStarMapPanel message) {
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
				GameFuncType.STAR_MAP, true)) {
			return;
		}
		// 是否可以打开面板;
		if (!human.getHumanSignManager().activateAnyStarMap()) {
			return;
		}
		// 給面板信息
		GCOpenStarMapPanel starMapPanel = new GCOpenStarMapPanel();
		starMapPanel.setStarSoul(human.getStarSoul());
		// 1. 获取星图列表
		List<SpriteStarMapInfo> starMapList = human.getHumanSignManager()
				.getStarMapList();
		starMapPanel.setStarMapList(starMapList
				.toArray(new SpriteStarMapInfo[0]));
		// 2. 当前的星图id
		starMapPanel.setStarMapId(human.getHumanSignManager()
				.getCurrentStarMapInfo().getStarMapId());
		// 3. 星座列表
		List<SpriteSignInfo> signList = human.getHumanSignManager()
				.getCurrentSignList();
		starMapPanel.setSignList(signList.toArray(new SpriteSignInfo[0]));
		human.sendMessage(starMapPanel);
		// 新手引导
		human.getHumanGuideManager().showGuide(
				GuideType.OPEN_STAR_MAP_PANEL.getIndex());
	}

}
