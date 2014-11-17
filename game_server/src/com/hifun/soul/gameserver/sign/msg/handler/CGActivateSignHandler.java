package com.hifun.soul.gameserver.sign.msg.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.sign.msg.CGActivateSign;
import com.hifun.soul.gameserver.sprite.template.SpriteSignTemplate;
import com.hifun.soul.gameserver.sprite.template.SpriteStarMapTemplate;

/**
 * 请求激活指定的星座;
 * 
 * @author crazyjohn
 * 
 */
@Component
public class CGActivateSignHandler implements
		IMessageHandlerWithType<CGActivateSign> {

	@Override
	public short getMessageType() {
		return MessageType.CG_ACTIVATE_SIGN;
	}

	@Override
	public void execute(CGActivateSign message) {
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
		SpriteSignTemplate signTemplate = GameServerAssist.getTemplateService()
				.get(message.getSignId(), SpriteSignTemplate.class);
		if (signTemplate == null) {
			return;
		}
		// 判断等级是否满足
		SpriteStarMapTemplate template = GameServerAssist.getTemplateService()
				.get(signTemplate.getStarMapId(), SpriteStarMapTemplate.class);
		if (template == null) {
			return;
		}
		if (human.getLevel() < template.getOpenLevel()) {
			return;
		}
		// 激活指定星座
		human.getHumanSignManager().activateSignById(message.getSignId());
	}

}
