package com.hifun.soul.gameserver.autobattle.msg.internal;

import com.hifun.soul.common.LogReasons.EnergyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.SceneScheduleMessage;
import com.hifun.soul.gameserver.autobattle.AutoBattleStateType;
import com.hifun.soul.gameserver.autobattle.msg.GCEliteAutoBattleResult;
import com.hifun.soul.gameserver.autobattle.msg.GCStageAutoBattleState;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.elitestage.HumanEliteStageManager;
import com.hifun.soul.gameserver.elitestage.template.EliteStageTemplate;
import com.hifun.soul.gameserver.event.BattleWinEvent;
import com.hifun.soul.gameserver.event.EliteStageBattleEvent;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.SimpleCommonItem;
import com.hifun.soul.gameserver.player.state.PlayerState;

/**
 * 精英副本扫荡内部消息
 * @author magicstone
 */
public class EliteAutoBattleInternalMessage extends SceneScheduleMessage {

	private int stageId;
	private Human human;
	
	public EliteAutoBattleInternalMessage(Human human, int stageId) {
		this.human = human;
		this.stageId = stageId;
	}
	
	@Override
	public void execute() {
		if(isCanceled()){
			return;
		}
		
		// 判断是否有足够的体力
		if(SharedConstants.ELITE_ENERGY_NUM > human.getEnergy()){
			human.sendWarningMessage(LangConstants.ENERGY_NOT_ENOUGH);
			human.getHumanAutoBattleManager().cancelAutoBattle();
			return;
		}
		
		// 判断背包是否已满
		if(human.getBagManager().isFull(BagType.MAIN_BAG)){
			human.sendWarningMessage(LangConstants.BAG_IS_FULL);
			human.getHumanAutoBattleManager().cancelAutoBattle();
			return;
		}
		
		// 消耗精力值
		human.setEnergy(human.getEnergy()
				- SharedConstants.ELITE_ENERGY_NUM,EnergyLogReason.BATTLE_WITH_ELITE_USE_ENERGY,"stageId:"+stageId);
		// 攻打精英副本
		EliteStageBattleEvent battleWinEvent = new EliteStageBattleEvent(true);
		this.human.getEventBus().fireEvent(battleWinEvent);
		// 发送战斗胜利事件
		BattleWinEvent battleWin = new BattleWinEvent();
		this.human.getEventBus().fireEvent(battleWin);
		
		HumanEliteStageManager stageManager = human.getHumanEliteStageManager();
		if(!stageManager.getEliteStageStateMap().containsKey(stageId)){
			return;
		}
		
		float rewardRate = GameServerAssist.getStageTemplateManager().getRewardRateByStar(stageManager.getStageStar(stageId));
		stageManager.genRewardItemId(stageId);
		stageManager.onBattleWin(stageId,rewardRate);
		
		EliteStageTemplate template = GameServerAssist.getEliteStageTemplateManager().getEliteStageTemplate(stageId);
		GCEliteAutoBattleResult gcMsg = new GCEliteAutoBattleResult();
		gcMsg.setCoin((int) (template.getCoinNum()*rewardRate));
		gcMsg.setExp((int) (template.getExp()*rewardRate));
		gcMsg.setTechPoint((int) (template.getTechPoint()*rewardRate));
		String[] items = null;
		SimpleCommonItem item = CommonItemBuilder.genSimpleCommonItem(stageManager.getRewardItemId());
		if(item!=null){
			items = new String[1];
			items[0]= item.getName();
		}
		else{
			items = new String[0];
		}
		gcMsg.setItems(items);
		human.sendMessage(gcMsg);
		// 执行完之后remove
		human.getHumanAutoBattleManager().removeAutoBattleTask(this);				
		// 判断背包是否已满,若满则取消扫荡
		if(human.getBagManager().isFull(BagType.MAIN_BAG)){
			human.sendWarningMessage(LangConstants.BAG_IS_FULL);
			human.getHumanAutoBattleManager().cancelAutoBattle();
			return;
		}		
		// 判断自动战斗cd时间是否已经到了
		if(human.getHumanCdManager().getRemainCd(CdType.AUTO_BATTLE) <= 0
				&& human.getHumanAutoBattleManager().isExecuteComplated()){
			// 状态由扫荡中切换到游戏中
			human.getPlayer().transferStateTo(PlayerState.GAMEING);
			
			// 取消完成
			GCStageAutoBattleState gcUpdateMsg = new GCStageAutoBattleState();
			gcUpdateMsg.setState(AutoBattleStateType.INIT.getIndex());
			human.sendMessage(gcUpdateMsg);
		}
	}

}
