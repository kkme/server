package com.hifun.soul.gameserver.human;

import com.hifun.soul.gameserver.function.GameFuncType;

public abstract class AbstractNotifyManager implements INotifyManager {

	private Human human;

	public AbstractNotifyManager(Human human) {
		this.human = human;
	}

	@Override
	public void sendNotify() {
		GameFuncType funcType = getFuncType();
		boolean isNotify = isNotify();
		human.getHumanFunctionManager().sendNotify(funcType, isNotify);
	}

}
