package com.hifun.soul.gameserver.sprite.msg.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.sprite.SpriteSoulType;
import com.hifun.soul.gameserver.sprite.model.SpritePubInfo;
import com.hifun.soul.gameserver.sprite.msg.CGOpenRecruitPanel;
import com.hifun.soul.gameserver.sprite.msg.GCOpenRecruitPanel;
import com.hifun.soul.gameserver.sprite.template.SpriteRecruitTemplate;
import com.hifun.soul.gameserver.sprite.template.SpriteTemplateManager;

/**
 * 处理打开招募面板;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGOpenRecruitPanelHandler implements
		IMessageHandlerWithType<CGOpenRecruitPanel> {
	@Autowired
	private TemplateService templateService;
	@Autowired
	private SpriteTemplateManager templateManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_RECRUIT_PANEL;
	}

	@Override
	public void execute(CGOpenRecruitPanel message) {
		// 打开招募面板
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
		// 招募类型
		int recruitType = ((message.getRecruitType() <= 0) ? SpriteSoulType.SPRITE_SOUL_GREEN
				.getIndex() : message.getRecruitType());
		SpriteRecruitTemplate recruitTemplate = templateService.get(
				recruitType, SpriteRecruitTemplate.class);
		if (recruitTemplate == null) {
			return;
		}
		// 等级是否满足
		if (human.getLevel() < recruitTemplate.getOpenLevel()) {
			return;
		}
		// 取出指定品质的精灵列表
		List<SpritePubInfo> spriteList = templateManager
				.getSpritePubInfoByQuality(recruitType);
		GCOpenRecruitPanel openMessage = new GCOpenRecruitPanel();
		openMessage.setRecruitType(recruitType);
		openMessage.setSpriteList(spriteList.toArray(new SpritePubInfo[0]));
		human.sendMessage(openMessage);
	}

}
