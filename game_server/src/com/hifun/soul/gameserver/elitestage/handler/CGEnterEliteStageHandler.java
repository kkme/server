package com.hifun.soul.gameserver.elitestage.handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.elitestage.EliteStageTemplateManager;
import com.hifun.soul.gameserver.elitestage.HumanEliteStageManager;
import com.hifun.soul.gameserver.elitestage.model.EliteStageInfo;
import com.hifun.soul.gameserver.elitestage.msg.CGEnterEliteStage;
import com.hifun.soul.gameserver.elitestage.msg.GCEliteStageTypeList;
import com.hifun.soul.gameserver.elitestage.msg.GCEnterEliteStage;
import com.hifun.soul.gameserver.elitestage.template.EliteStageTypeTemplate;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;

/**
 * 进入精英副本
 * 
 * @author magicstone
 *
 */
@Component
public class CGEnterEliteStageHandler implements IMessageHandlerWithType<CGEnterEliteStage> {
	@Autowired
	private EliteStageTemplateManager templateManager;
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_ENTER_ELITE_STAGE;
	}

	@Override
	public void execute(CGEnterEliteStage message) {
		if(message.getPlayer()==null){
			return;
		}
		Human human = message.getPlayer().getHuman();
		if(human == null){
			return;
		}
		int stageTypeId = message.getStageTypeId();
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.ELITE, true)){
			return;
		}
		HumanEliteStageManager manager = human.getHumanEliteStageManager(); 
		if(stageTypeId==0){//首次打开
			stageTypeId = manager.getCurrentStageTypeId();
			if(stageTypeId==0){
				//精英副本类型未开启
				return;
			}
			//发送列表和刷新计数器
			GCEliteStageTypeList gcTypeListMsg = new GCEliteStageTypeList();
			gcTypeListMsg.setEliteStageTypeList(manager.getEliteStageTypeList());
			human.sendMessage(gcTypeListMsg);			
		}
		else{
			manager.setCurrentStageTypeId(stageTypeId);
		}
		EliteStageInfo[] eliteStages = manager.getVisibleEliteStages(stageTypeId);
		EliteStageTypeTemplate stageTypeTemplate = templateManager.getEliteStageTypeTempalte(stageTypeId);
		GCEnterEliteStage gcMsg = new GCEnterEliteStage();
		gcMsg.setStageTypeId(stageTypeId);
		gcMsg.setStageTypeDesc(stageTypeTemplate.getDesc());
		gcMsg.setCurrentStageId(manager.getCurrentStageId(stageTypeId)); //当前未使用该属性
		gcMsg.setEliteStages(eliteStages);
		gcMsg.setTotalStar(manager.getTotalStar());
		human.sendMessage(gcMsg);
		// 更新一下刷新的状态
		manager.sendRefreshChallengeStateInfo();
	}

}
