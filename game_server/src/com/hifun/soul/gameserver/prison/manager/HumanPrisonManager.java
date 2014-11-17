package com.hifun.soul.gameserver.prison.manager;

import com.hifun.soul.common.LogReasons.ExperienceLogReason;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.event.EventType;
import com.hifun.soul.gameserver.event.IEvent;
import com.hifun.soul.gameserver.event.IEventListener;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.ILoginManager;
import com.hifun.soul.gameserver.prison.Prisoner;

/**
 * 角色战俘营管理器
 * 
 * @author yandajun
 * 
 */
public class HumanPrisonManager implements ILoginManager, IEventListener {
	private Human human;

	private GlobalPrisonManager globalPrisonManager;

	public HumanPrisonManager(Human human) {
		this.human = human;
		this.human.registerLoginManager(this);
		globalPrisonManager = GameServerAssist.getGlobalPrisonManager();
		this.human.getEventBus().addListener(EventType.LEVEL_UP_EVENT, this);
	}

	@Override
	public void onLogin() {
		// 玩家第一次登陆的时候就生成战俘营信息，属于自由人
		Prisoner prisoner = globalPrisonManager.getPrisoner(human
				.getHumanGuid());
		if (prisoner == null) {
			globalPrisonManager.createPrisoner(human);
		}
		// 如果在战俘营有离线经验，添加上
		prisoner = globalPrisonManager.getPrisoner(human.getHumanGuid());
		if (prisoner.getOffLineExperience() != 0) {
			human.addExperience(prisoner.getOffLineExperience(), true,
					ExperienceLogReason.PRISON_GOT, "");
			prisoner.setOffLineExperience(0);
			globalPrisonManager.updatePrisoner(prisoner);
		}
	}

	@Override
	public void onEvent(IEvent event) {
		// 角色升级更新战俘营角色信息
		Prisoner prisoner = globalPrisonManager.getPrisoner(human
				.getHumanGuid());
		if (prisoner != null) {
			prisoner.setHumanLevel(human.getLevel());
			globalPrisonManager.updatePrisoner(prisoner);
		}
	}
}
