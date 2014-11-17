package com.hifun.soul.gameserver.autobattle.msg.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.autobattle.msg.CGShowStageAutoBattlePanel;
import com.hifun.soul.gameserver.autobattle.msg.GCShowStageAutoBattlePanel;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.stage.manager.HumanStageManager;
import com.hifun.soul.gameserver.stage.service.StageTemplateManager;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;

@Component
public class CGShowStageAutoBattlePanelHandler implements
		IMessageHandlerWithType<CGShowStageAutoBattlePanel> {
	@Autowired
	private StageTemplateManager templateManager;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_STAGE_AUTO_BATTLE_PANEL;
	}

	@Override
	public void execute(CGShowStageAutoBattlePanel message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		HumanCdManager cdManager = human.getHumanCdManager();
		HumanStageManager stageManager = human.getHumanStageManager();		
		// 判断该关卡是否可以扫荡
		int star = stageManager.getStageStar(message.getStageId());
		if(!templateManager.canAutoBattle(star)){
			return;
		}
		int vipReduceTime=0;
		VipPrivilegeTemplate template = GameServerAssist.getVipPrivilegeTemplateManager().getVipTemplate(human.getVipLevel());
		if(template != null){
			vipReduceTime = template.getAutoBattleCd();
		}
		int starReduceTime = GameServerAssist.getStageTemplateManager().getReduceCdByStar(star);
		long stageTimeCost = cdManager.getSpendTime(CdType.AUTO_BATTLE,0)-(starReduceTime+vipReduceTime)*TimeUtils.MIN;
		stageTimeCost = stageTimeCost>0 ? stageTimeCost : 0;
		// 单轮扫荡时间
		int time = (int)(stageTimeCost);
		// 通知客户端
		GCShowStageAutoBattlePanel gcMsg = new GCShowStageAutoBattlePanel();
		gcMsg.setEnergy(SharedConstants.STAGE_ENERGY_NUM);
		gcMsg.setTime(time);
		gcMsg.setStarTimeReduce(starReduceTime);
		gcMsg.setVipTimeReduce(vipReduceTime);
		human.sendMessage(gcMsg);
	}

}
