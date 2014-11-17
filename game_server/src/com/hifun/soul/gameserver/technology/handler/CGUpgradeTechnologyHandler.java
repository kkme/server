package com.hifun.soul.gameserver.technology.handler;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.event.TechUpgradeEvent;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.technology.manager.HumanTechnologyManager;
import com.hifun.soul.gameserver.technology.msg.CGUpgradeTechnology;
import com.hifun.soul.gameserver.technology.msg.GCUpgradeTechnology;
import com.hifun.soul.gameserver.technology.msg.TechnologyDetailInfo;
import com.hifun.soul.gameserver.technology.service.TechnologyTemplateManager;
import com.hifun.soul.gameserver.technology.template.TechnologyLevelUpTemplate;

@Component
public class CGUpgradeTechnologyHandler implements
		IMessageHandlerWithType<CGUpgradeTechnology> {
	private Logger logger = Loggers.TECHNOLOGY_LOGGER;
	@Autowired
	private TechnologyTemplateManager technologyTemplateManager;
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_UPGRADE_TECHNOLOGY;
	}

	@Override
	public void execute(CGUpgradeTechnology message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.TECHNOLOGY, true)){
			return;
		}
		
		HumanTechnologyManager humanTechnologyManager = human.getHumanTechnologyManager();
		// 當前科技等級
		int level = humanTechnologyManager.getTechnologyLevel(message.getTechnologyId());
		TechnologyLevelUpTemplate technologyLevelUpTemplate 
			= technologyTemplateManager.getTechnologyLevelUpTemplate(message.getTechnologyId(),level);
		if(technologyLevelUpTemplate == null){
			logger.error("can not find technlogyLevelUpTemplate! humanGuid:" + human.getHumanGuid() + "; technologyId:" + message.getTechnologyId());
			return;
		}
		// 判断是否还有下一级科技等级
		TechnologyDetailInfo technologyDetailInfo = technologyTemplateManager.getTechnologyDetailInfo(message.getTechnologyId(), level+1);
		if(technologyDetailInfo == null){
			return;
		}
		// 判断是否超过角色等级
		if(human.getLevel() < technologyLevelUpTemplate.getRoleLevel()){
			human.sendErrorMessage(LangConstants.ROLE_LEVEL_LIMIT);
			return;
		}
		// 判断是否有足够的科技点
		if(humanTechnologyManager.getTechnologyPoints() < technologyLevelUpTemplate.getCostValue()){
			human.sendErrorMessage(LangConstants.TECHNOLOGY_POINTS_NOT_ENOUGH);
			return;
		}
		// 消耗点数升级科技
		if(humanTechnologyManager.costTechnologyPoints(technologyLevelUpTemplate.getCostValue())){
			humanTechnologyManager.addTechnologyLevel(message.getTechnologyId());
			// 发送科技升级事件
			TechUpgradeEvent techUpgrade = new TechUpgradeEvent();
			human.getEventBus().fireEvent(techUpgrade);
			
			GCUpgradeTechnology gcMsg = new GCUpgradeTechnology();
			gcMsg.setTechnologyId(message.getTechnologyId());
			gcMsg.setTechnologyNum(humanTechnologyManager.getTechnologyPoints());
			gcMsg.setTechnologyDetailInfo(technologyDetailInfo);
			human.sendMessage(gcMsg);
		}
	}

}
