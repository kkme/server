package com.hifun.soul.gameserver.autobattle.msg.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.core.util.TimeUtils;
import com.hifun.soul.gameserver.autobattle.msg.CGShowEliteStageAutoBattlePanel;
import com.hifun.soul.gameserver.autobattle.msg.GCShowEliteStageAutoBattlePanel;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.elitestage.HumanEliteStageManager;
import com.hifun.soul.gameserver.elitestage.model.EliteStageInfo;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.vip.template.VipPrivilegeTemplate;

@Component
public class CGShowEliteStageAutoBattlePanelHandler implements
		IMessageHandlerWithType<CGShowEliteStageAutoBattlePanel> {

	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_ELITE_STAGE_AUTO_BATTLE_PANEL;
	}

	@Override
	public void execute(CGShowEliteStageAutoBattlePanel message) {
		// 打开精英副本扫荡面板
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		HumanEliteStageManager eliteStageManager = human.getHumanEliteStageManager();
		// 符合条件的精英怪列表
		List<EliteStageInfo> stageInfos = eliteStageManager.getCanAutoBattleEliteStages(message.getEliteStageType());
		List<String> monsters = new ArrayList<String>();
		for(EliteStageInfo stageInfo : stageInfos){
			monsters.add(stageInfo.getMonsterName());
		}
		// 攻打精英怪需要的体力
		int energy = monsters.size() * SharedConstants.ELITE_ENERGY_NUM;
		// 自动扫荡需要的时间
		HumanCdManager cdManager = human.getHumanCdManager();		
		long time = 0;
		int starReduceTime=0;
		int vipReduceTime=0;
		VipPrivilegeTemplate template = GameServerAssist.getVipPrivilegeTemplateManager().getVipTemplate(human.getVipLevel());
		if(template != null){
			vipReduceTime = template.getAutoBattleCd();
		}
		for(EliteStageInfo stageInfo : stageInfos){
			int stageStarReduceTime = GameServerAssist.getStageTemplateManager().getReduceCdByStar(eliteStageManager.getStageStar(stageInfo.getStageId()));
			starReduceTime += stageStarReduceTime;
			long stageTimeCost = cdManager.getSpendTime(CdType.AUTO_BATTLE,0)-(stageStarReduceTime+vipReduceTime)*TimeUtils.MIN;
			stageTimeCost = stageTimeCost>0 ? stageTimeCost : 0;
			time += stageTimeCost;
		}
		// 通知客户端
		GCShowEliteStageAutoBattlePanel gcMsg = new GCShowEliteStageAutoBattlePanel();
		gcMsg.setEnergys(energy);
		gcMsg.setTime((int)time);
		gcMsg.setMonsters(monsters.toArray(new String[0]));
		gcMsg.setStarTimeReduce(starReduceTime);
		gcMsg.setVipTimeReduce(vipReduceTime*stageInfos.size());
		human.sendMessage(gcMsg);
	}

}
