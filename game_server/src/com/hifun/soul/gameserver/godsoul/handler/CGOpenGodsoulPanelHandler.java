package com.hifun.soul.gameserver.godsoul.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.godsoul.info.GodsoulBuffInfo;
import com.hifun.soul.gameserver.godsoul.msg.CGOpenGodsoulPanel;
import com.hifun.soul.gameserver.godsoul.msg.EquipBitInfo;
import com.hifun.soul.gameserver.godsoul.msg.GCOpenGodsoulPanel;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;

/**
 * 打开神魄面板
 * 
 * @author yandajun
 * 
 */
@Component
public class CGOpenGodsoulPanelHandler implements
		IMessageHandlerWithType<CGOpenGodsoulPanel> {
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_GODSOUL_PANEL;
	}

	@Override
	public void execute(CGOpenGodsoulPanel message) {
		if (message.getPlayer() == null) {
			return;
		}
		Human human = message.getPlayer().getHuman();
		if (human == null) {
			return;
		}
		// 判断功能是否开放
		if (!gameFuncService.gameFuncIsOpen(human, GameFuncType.GODSOUL, true)) {
			return;
		}
		// 获得装备位列表信息
		List<EquipBitInfo> equipBitInfoList = human.getHumanGodsoulManager()
				.generateEquipBitInfoList();
		GCOpenGodsoulPanel gcMsg = new GCOpenGodsoulPanel();
		gcMsg.setEquipBitInfos(equipBitInfoList.toArray(new EquipBitInfo[0]));
		List<GodsoulBuffInfo> buffInfoList = human.getHumanGodsoulManager()
				.generateEquipBitBuffInfoList();
		gcMsg.setGodsoulBuffInfos(buffInfoList.toArray(new GodsoulBuffInfo[0]));
		message.getPlayer().getHuman().sendMessage(gcMsg);
		// 新手引导
		human.getHumanGuideManager().showGuide(
				GuideType.OPEN_GODSOUL_PANEL.getIndex());
	}

}
