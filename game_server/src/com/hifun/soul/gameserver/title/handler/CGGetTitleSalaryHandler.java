package com.hifun.soul.gameserver.title.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.title.msg.CGGetTitleSalary;
@Component
public class CGGetTitleSalaryHandler implements
		IMessageHandlerWithType<CGGetTitleSalary> {
	@Autowired
	private GameFuncService gameFuncService;

	@Override
	public short getMessageType() {
		return MessageType.CG_GET_TITLE_SALARY;
	}

	@Override
	public void execute(CGGetTitleSalary message) {
		Human human = message.getPlayer().getHuman();
		// 判断功能是否开放
		if (!gameFuncService.gameFuncIsOpen(human, GameFuncType.TITLE, true)) {
			return;
		}
		human.getHumanTitleManager().getTitleSalary();
	}

}
