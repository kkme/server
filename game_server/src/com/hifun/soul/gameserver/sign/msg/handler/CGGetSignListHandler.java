package com.hifun.soul.gameserver.sign.msg.handler;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.sign.msg.CGGetSignList;
import com.hifun.soul.gameserver.sign.msg.GCGetSignList;
import com.hifun.soul.gameserver.sprite.model.SpriteSignInfo;
import com.hifun.soul.gameserver.sprite.template.SpriteStarMapTemplate;

/**
 * 获取星图列表;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGGetSignListHandler implements
		IMessageHandlerWithType<CGGetSignList> {

	@Override
	public short getMessageType() {
		return MessageType.CG_GET_SIGN_LIST;
	}

	@Override
	public void execute(CGGetSignList message) {
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
		// 判断等级是否满足
		SpriteStarMapTemplate template = GameServerAssist.getTemplateService()
				.get(message.getStarMapId(), SpriteStarMapTemplate.class);
		if (template == null) {
			return;
		}
		if (human.getLevel() < template.getOpenLevel()) {
			return;
		}
		// 前置星图是否激活;
		if (!human.getHumanSignManager().preStarMapAllSignsLight(template)) {
			return;
		}
		// 获取星座列表;
		GCGetSignList signListMessage = new GCGetSignList();
		List<SpriteSignInfo> signList = human.getHumanSignManager()
				.getSignListByStarMapId(message.getStarMapId());
		signListMessage.setSignList(signList.toArray(new SpriteSignInfo[0]));
		signListMessage.setStarMapId(message.getStarMapId());
		human.sendMessage(signListMessage);
	}
}
