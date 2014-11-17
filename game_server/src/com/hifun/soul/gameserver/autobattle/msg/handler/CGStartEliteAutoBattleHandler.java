package com.hifun.soul.gameserver.autobattle.msg.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.autobattle.AutoBattleStateType;
import com.hifun.soul.gameserver.autobattle.manager.HumanAutoBattleManager;
import com.hifun.soul.gameserver.autobattle.msg.CGStartEliteAutoBattle;
import com.hifun.soul.gameserver.autobattle.msg.GCStageAutoBattleState;
import com.hifun.soul.gameserver.autobattle.msg.internal.EliteAutoBattleInternalMessage;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.elitestage.HumanEliteStageManager;
import com.hifun.soul.gameserver.elitestage.model.EliteStageInfo;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.player.state.PlayerState;

@Component
public class CGStartEliteAutoBattleHandler implements
		IMessageHandlerWithType<CGStartEliteAutoBattle> {
	@Autowired
	private GameWorld gameWorld;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_START_ELITE_AUTO_BATTLE;
	}

	@Override
	public void execute(CGStartEliteAutoBattle message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		// 判断玩家当前是否可以切换到扫荡中的状态
		if(!player.canTransferStateTo(PlayerState.AUTOBATTLEING)){
			return;
		}
		// 判断背包是否已满
		if(human.getBagManager().isFull(BagType.MAIN_BAG)){
			human.sendWarningMessage(LangConstants.BAG_IS_FULL);
			return;
		}
		// 判断是否有足够的体力
		HumanEliteStageManager eliteStageManager = human.getHumanEliteStageManager();
		List<EliteStageInfo> stageInfos = eliteStageManager.getCanAutoBattleEliteStages(message.getEliteStageType());
		if(stageInfos.size()*SharedConstants.ELITE_ENERGY_NUM > human.getEnergy()){
			human.sendErrorMessage(LangConstants.ENERGY_NOT_ENOUGH);
			return;
		}
		// 切换到扫荡中状态
		player.transferStateTo(PlayerState.AUTOBATTLEING);
		// 新建自动战斗的任务
		HumanAutoBattleManager autoBattleManager = human.getHumanAutoBattleManager();
		HumanCdManager cdManager = human.getHumanCdManager();
		long delay = 0;
		Map<Integer,Long> stageCds = new HashMap<Integer,Long>();
		//扫荡时间差，保证扫荡顺序
		int orderTime = 0;
		for(EliteStageInfo stageInfo : stageInfos){
			delay += autoBattleManager.getAutoBattleCd(cdManager, eliteStageManager.getStageStar(stageInfo.getStageId()));
			delay += orderTime;
			stageCds.put(stageInfo.getStageId(), delay);
			orderTime+=10;
		}
		// 添加扫荡cd
		cdManager.addCd(CdType.AUTO_BATTLE, delay);
		cdManager.snapCdQueueInfo(CdType.AUTO_BATTLE);
		for(EliteStageInfo stageInfo : stageInfos){
			EliteAutoBattleInternalMessage internalMsg = new EliteAutoBattleInternalMessage(human,stageInfo.getStageId());
			autoBattleManager.addAutoBattleTask(internalMsg);
			gameWorld.scheduleOnece(internalMsg, stageCds.get(stageInfo.getStageId()));
		}
		// 更新扫荡状态
		GCStageAutoBattleState gcMsg = new GCStageAutoBattleState();
		gcMsg.setState(AutoBattleStateType.RUNNING.getIndex());
		human.sendMessage(gcMsg);
	}

}
