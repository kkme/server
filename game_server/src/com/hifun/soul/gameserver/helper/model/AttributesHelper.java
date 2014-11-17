package com.hifun.soul.gameserver.helper.model;

import com.hifun.soul.gameserver.helper.HelpStateType;
import com.hifun.soul.gameserver.helper.HelpType;
import com.hifun.soul.gameserver.helper.IHelper;
import com.hifun.soul.gameserver.role.properties.manager.HumanPropertyManager;

public class AttributesHelper implements IHelper {

	private HumanPropertyManager manager;
	
	public AttributesHelper(HumanPropertyManager manager) {
		this.manager = manager;
	}
	
	@Override
	public int getHelpType() {
		return HelpType.ATTRIBUTES.getIndex();
	}

	@Override
	public int getState() {
		// 判断是否有剩余次数
		if(manager.getOwner().getUnDistributePropertyPoint() > 0){
			return HelpStateType.CAN_START.getIndex();
		}
		else{
			return HelpStateType.CLOSED.getIndex();
		}
	}

	@Override
	public int getUsedTimes() {
		return 0;
	}

	@Override
	public int getTotalTimes() {
		return 0;
	}

}
