package com.hifun.soul.gameserver.prison.handler;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.prison.IdentityType;
import com.hifun.soul.gameserver.prison.Prisoner;
import com.hifun.soul.gameserver.prison.manager.GlobalPrisonManager;
import com.hifun.soul.gameserver.prison.msg.CGBuyArrestNum;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;

/**
 * 购买抓捕次数
 * 
 * @author yandajun
 * 
 */
@Component
public class CGBuyArrestNumHandler implements
		IMessageHandlerWithType<CGBuyArrestNum> {

	@Override
	public short getMessageType() {
		return MessageType.CG_BUY_ARREST_NUM;
	}

	@Override
	public void execute(CGBuyArrestNum message) {
		Human human = message.getPlayer().getHuman();
		// 功能是否开启
		if (!GameServerAssist.getGameFuncService().gameFuncIsOpen(human,
				GameFuncType.PRISON, true)) {
			return;
		}
		// 判断身份是否合法
		GlobalPrisonManager manager = GameServerAssist.getGlobalPrisonManager();
		Prisoner arrestor = manager.getPrisoner(human.getHumanGuid());
		if (arrestor == null) {
			return;
		}
		if (arrestor.getIdentityType() == IdentityType.SLAVE.getIndex()) {
			human.sendErrorMessage(LangConstants.PRISON_IDENTITY_ILLEGAL);
			return;
		}
		// 战俘营购买最大抓捕次数受到vip等级的限制
		VipPrivilegeTemplate vipTemplate = GameServerAssist
				.getVipPrivilegeTemplateManager().getVipTemplate(
						human.getVipLevel());
		if (vipTemplate == null) {
			return;
		}
		int totalNum = manager.getPrisoner(human.getHumanGuid())
				.getBuyArrestNum() + 1;
		if (totalNum > vipTemplate.getMaxBuyPrisonArrestTimes()) {
			human.sendErrorMessage(LangConstants.CAN_NOT_BUY_MORE_ARREST_TIMES);
			return;
		}
		GameServerAssist.getGlobalPrisonManager().buyArrestNum(human);
	}

}
