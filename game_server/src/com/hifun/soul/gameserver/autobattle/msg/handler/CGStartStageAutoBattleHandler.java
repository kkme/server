package com.hifun.soul.gameserver.autobattle.msg.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.autobattle.AutoBattleStateType;
import com.hifun.soul.gameserver.autobattle.manager.HumanAutoBattleManager;
import com.hifun.soul.gameserver.autobattle.msg.CGStartStageAutoBattle;
import com.hifun.soul.gameserver.autobattle.msg.GCStageAutoBattleState;
import com.hifun.soul.gameserver.autobattle.msg.internal.StageAutoBattleInternalMessage;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.cd.manager.HumanCdManager;
import com.hifun.soul.gameserver.gameworld.GameWorld;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.player.state.PlayerState;
import com.hifun.soul.gameserver.stage.manager.HumanStageManager;
import com.hifun.soul.gameserver.stage.service.StageTemplateManager;

@Component
public class CGStartStageAutoBattleHandler implements
		IMessageHandlerWithType<CGStartStageAutoBattle> {
	@Autowired
	private GameWorld gameWorld;
	@Autowired
	private StageTemplateManager templateManager;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_START_STAGE_AUTO_BATTLE;
	}

	@Override
	public void execute(CGStartStageAutoBattle message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		if(message.getStageId() <= 0
				|| message.getTimes() <= 0){
			return;
		}
		// 判断玩家当前是否可以切换到扫荡中的状态
		if(!player.canTransferStateTo(PlayerState.AUTOBATTLEING)){
			return;
		}
		// 判断关卡的星级是否可以扫荡
		HumanAutoBattleManager autoBattleManager = human.getHumanAutoBattleManager();
		HumanStageManager stageManager = human.getHumanStageManager();
		int star = stageManager.getStageStar(message.getStageId());
		if(!templateManager.canAutoBattle(star)){
			return;
		}
		// 判断背包是否已满
		if(human.getBagManager().isFull(BagType.MAIN_BAG)){
			human.sendWarningMessage(LangConstants.BAG_IS_FULL);
			return;
		}
		// 判断是否有足够的体力
		if(message.getTimes()*SharedConstants.STAGE_ENERGY_NUM > human.getEnergy()){
			human.sendErrorMessage(LangConstants.ENERGY_NOT_ENOUGH);
			return;
		}
		// 切换到扫荡中状态
		player.transferStateTo(PlayerState.AUTOBATTLEING);
		// 新建自动战斗的任务
		HumanCdManager cdManager = human.getHumanCdManager();
		long delay = autoBattleManager.getAutoBattleCd(cdManager, star);
		// 添加扫荡cd
		cdManager.addCd(CdType.AUTO_BATTLE, delay*message.getTimes());
		cdManager.snapCdQueueInfo(CdType.AUTO_BATTLE);
		for(int i=1; i<=message.getTimes(); i++){
			StageAutoBattleInternalMessage internalMsg = new StageAutoBattleInternalMessage(human,message.getStageId());
			autoBattleManager.addAutoBattleTask(internalMsg);
			gameWorld.scheduleOnece(internalMsg, delay*i);
		}
		// 更新扫荡状态
		GCStageAutoBattleState gcMsg = new GCStageAutoBattleState();
		gcMsg.setState(AutoBattleStateType.RUNNING.getIndex());
		human.sendMessage(gcMsg);
	}

}
