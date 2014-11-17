package com.hifun.soul.gameserver.legionmine.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.legionmine.LegionMineMember;
import com.hifun.soul.gameserver.legionmine.manager.GlobalLegionMineWarManager;
import com.hifun.soul.gameserver.legionmine.msg.CGLegionMineWarQuit;

@Component
public class CGLegionMineWarQuitHandler implements
		IMessageHandlerWithType<CGLegionMineWarQuit> {
	@Autowired
	private GlobalLegionMineWarManager globalLegionMineWarManager;

	@Override
	public short getMessageType() {
		return MessageType.CG_LEGION_MINE_WAR_QUIT;
	}

	@Override
	public void execute(CGLegionMineWarQuit message) {
		Human human = message.getPlayer().getHuman();
		LegionMineMember mineMember = globalLegionMineWarManager
				.getJoinLegionMineMember(human.getHumanGuid());
		if (mineMember == null) {
			return;
		}
		// 退出返回大本营
		globalLegionMineWarManager.quit(mineMember);
	}

}
