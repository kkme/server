package com.hifun.soul.gameserver.faction.msg.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.faction.GlobalFactionManager;
import com.hifun.soul.gameserver.faction.model.FactionSimpleInfo;
import com.hifun.soul.gameserver.faction.msg.CGOpenFactionPanel;
import com.hifun.soul.gameserver.faction.msg.GCOpenSelectFactionPanel;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;

/**
 * 请求打开阵营面板;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGOpenFactionPanelHandler implements
		IMessageHandlerWithType<CGOpenFactionPanel> {
	@Autowired
	private GlobalFactionManager factionManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_FACTION_PANEL;
	}

	@Override
	public void execute(CGOpenFactionPanel message) {
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
				GameFuncType.FACTION, true)) {
			return;
		}
		// 看看角色是否有阵营;有阵营则弹阵营板子,无阵营弹选择板子
		if (factionManager.areadyJoinFaction(human)) {
			showFactionPanel(human);
		} else {
			showSelectFactionPanel(human);
		}

	}

	/**
	 * 展示选择阵营面板;
	 * 
	 * @param human
	 */
	private void showSelectFactionPanel(Human human) {
		GCOpenSelectFactionPanel selectPanel = new GCOpenSelectFactionPanel();
		FactionSimpleInfo brightInfo = factionManager.getBrightInfo();
		selectPanel.setBrightInfo(brightInfo);
		FactionSimpleInfo darkInfo = factionManager.getDarkInfo();
		selectPanel.setDarkInfo(darkInfo);
		selectPanel.setRandomRewardCoin(GameServerAssist.getGameConstants()
				.getRandomSelectFactionRewardCoin());
		human.sendMessage(selectPanel);
	}

	/**
	 * 展示阵营面板;
	 * 
	 * @param human
	 */
	private void showFactionPanel(Human human) {
		// TODO Auto-generated method stub

	}

}
