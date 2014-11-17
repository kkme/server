package com.hifun.soul.gameserver.elitestage.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.elitestage.EliteStageTemplateManager;
import com.hifun.soul.gameserver.elitestage.HumanEliteStageManager;
import com.hifun.soul.gameserver.elitestage.model.EliteStageInfo;
import com.hifun.soul.gameserver.elitestage.msg.CGOpenEliteStagePanel;
import com.hifun.soul.gameserver.elitestage.msg.GCEliteStageTypeList;
import com.hifun.soul.gameserver.elitestage.msg.GCOpenEliteStagePanel;
import com.hifun.soul.gameserver.elitestage.template.EliteStageTypeTemplate;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;

@Component
public class CGOpenEliteStagePanelHandler implements IMessageHandlerWithType<CGOpenEliteStagePanel> {
	@Autowired
	private EliteStageTemplateManager templateManager;
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_OPEN_ELITE_STAGE_PANEL;
	}

	@Override
	public void execute(CGOpenEliteStagePanel message) {
		if(message.getPlayer()==null){
			return;
		}
		Human human = message.getPlayer().getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.ELITE, true)){
			return;
		}
		HumanEliteStageManager manager = human.getHumanEliteStageManager(); 
		int stageTypeId = manager.getCurrentStageTypeId();
		if (stageTypeId == 0) {
			// 精英副本类型未开启
			return;
		}
		// 发送列表和刷新计数器
		GCEliteStageTypeList gcTypeListMsg = new GCEliteStageTypeList();
		gcTypeListMsg.setEliteStageTypeList(manager.getEliteStageTypeList());
		human.sendMessage(gcTypeListMsg);
		// 发送精英副本消息
		EliteStageInfo[] eliteStages = manager.getVisibleEliteStages(stageTypeId);
		EliteStageTypeTemplate stageTypeTemplate = templateManager.getEliteStageTypeTempalte(stageTypeId);
		GCOpenEliteStagePanel gcMsg = new GCOpenEliteStagePanel();
		gcMsg.setStageTypeId(stageTypeId);
		gcMsg.setCurrentStageId(manager.getCurrentStageId(stageTypeId));
		gcMsg.setStageTypeDesc(stageTypeTemplate.getDesc());
		gcMsg.setEliteStages(eliteStages);
		gcMsg.setTotalStar(manager.getTotalStar());
		int totalCount = GameServerAssist.getVipPrivilegeTemplateManager().getMaxRefreshEliteStageTimes(human.getVipLevel());
		int refreshStateNum = manager.getRefreshStateNum();
		int remainCount = totalCount>refreshStateNum ? totalCount-refreshStateNum : 0;		
		gcMsg.setTotalCount(totalCount);
		gcMsg.setRemainCount(remainCount);
		gcMsg.setCurrencyNum(GameServerAssist.getEliteStageTemplateManager().getRefreshStageStateCost(refreshStateNum + 1));
		human.sendMessage(gcMsg);
		human.getHumanGuideManager().showGuide(GuideType.OPEN_ELITE_PANEL.getIndex());
	}

}
