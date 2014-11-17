package com.hifun.soul.gameserver.sprite.msg.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.sprite.model.SpriteBagCellInfo;
import com.hifun.soul.gameserver.sprite.model.SpriteBuffInfo;
import com.hifun.soul.gameserver.sprite.model.SpriteEuqipSlotInfo;
import com.hifun.soul.gameserver.sprite.model.SpriteInfo;
import com.hifun.soul.gameserver.sprite.msg.CGOpenSpritePanel;
import com.hifun.soul.gameserver.sprite.msg.GCOpenSpritePanel;

/**
 * 打开精灵面板;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGOpenSpritePanelHandler implements
		IMessageHandlerWithType<CGOpenSpritePanel> {

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_SPRITE_PANEL;
	}

	@Override
	public void execute(CGOpenSpritePanel message) {
		// 打开精灵面板
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
				GameFuncType.SPRITE, true)) {
			return;
		}
		// 发送消息
		GCOpenSpritePanel openSpriteMessage = new GCOpenSpritePanel();
		// 1. 获取精灵列表
		List<SpriteInfo> spriteList = human.getHumanSpriteManager()
				.getSpirteList();
		openSpriteMessage.setSpriteList(spriteList.toArray(new SpriteInfo[0]));
		// 2. 获取buff列表
		List<SpriteBuffInfo> buffList = human.getHumanSpriteManager()
				.getBuffList();
		openSpriteMessage.setBuffList(buffList.toArray(new SpriteBuffInfo[0]));
		// 3. 获取背包列表
		List<SpriteBagCellInfo> cellList = human.getHumanSpriteManager()
				.getBagCellList();
		openSpriteMessage.setSpriteBagCellList(cellList
				.toArray(new SpriteBagCellInfo[0]));
		// 4. 装备位信息;
		List<SpriteEuqipSlotInfo> equipSlotList = human.getHumanSpriteManager()
				.getEquipSlotList();
		openSpriteMessage.setEquipSlotList(equipSlotList
				.toArray(new SpriteEuqipSlotInfo[0]));
		// 开格子消费
		openSpriteMessage.setOneCellCrystalCost(GameServerAssist
				.getGameConstants().getPerSpriteBagCellCostCrystal());
		human.sendMessage(openSpriteMessage);
		// 新手引导
		human.getHumanGuideManager().showGuide(
				GuideType.OPEN_SPRITE_PANEL.getIndex());
	}

}
