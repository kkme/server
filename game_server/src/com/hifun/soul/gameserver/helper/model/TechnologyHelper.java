package com.hifun.soul.gameserver.helper.model;

import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.helper.HelpStateType;
import com.hifun.soul.gameserver.helper.HelpType;
import com.hifun.soul.gameserver.helper.IHelper;
import com.hifun.soul.gameserver.technology.manager.HumanTechnologyManager;
import com.hifun.soul.gameserver.technology.template.TechnologyLevelUpTemplate;
import com.hifun.soul.gameserver.technology.template.TechnologyTemplate;

public class TechnologyHelper implements IHelper {
	private HumanTechnologyManager manager;
	
	public TechnologyHelper(HumanTechnologyManager manager) {
		this.manager = manager;
	}
	
	@Override
	public int getHelpType() {
		return HelpType.TECHNOLOGY.getIndex();
	}

	@Override
	public int getState() {
		// 功能未开放，返回已经结束
		if(!GameServerAssist.getGameFuncService().gameFuncIsOpen(manager.getOwner(), GameFuncType.TECHNOLOGY, false)){
			return HelpStateType.CLOSED.getIndex();
		}
		
		// 判断当前剩余点是否可以升级任意科技
		if(GameServerAssist.getTechnologyTemplateManager().getSuitableTechnologyTemplate(manager.getOwner().getLevel()).size() <= 0){
			return HelpStateType.CLOSED.getIndex();
		}
		else{
			for(TechnologyTemplate template : GameServerAssist.getTechnologyTemplateManager().getSuitableTechnologyTemplate(manager.getOwner().getLevel())){
				TechnologyLevelUpTemplate technologyLevelUpTemplate 
					= GameServerAssist.getTechnologyTemplateManager().getTechnologyLevelUpTemplate(template.getId(),manager.getTechnologyLevel(template.getId()));
				if(technologyLevelUpTemplate == null){
					continue;
				}
				if(manager.getTechnologyPoints() >= technologyLevelUpTemplate.getCostValue()
						&& manager.getOwner().getLevel() >= technologyLevelUpTemplate.getRoleLevel()){
					return HelpStateType.CAN_START.getIndex();
				}
			}
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
