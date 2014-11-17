package com.hifun.soul.gameserver.autobattle.msg.internal;

import com.hifun.soul.common.LogReasons.EnergyLogReason;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.SceneScheduleMessage;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.autobattle.AutoBattleStateType;
import com.hifun.soul.gameserver.autobattle.msg.GCStageAutoBattleResult;
import com.hifun.soul.gameserver.autobattle.msg.GCStageAutoBattleState;
import com.hifun.soul.gameserver.bag.BagType;
import com.hifun.soul.gameserver.cd.CdType;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.event.BattleWinEvent;
import com.hifun.soul.gameserver.event.MonsterDeadEvent;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.item.template.ItemTemplate;
import com.hifun.soul.gameserver.player.state.PlayerState;
import com.hifun.soul.gameserver.stage.template.StageTemplate;
import com.hifun.soul.gameserver.turntable.template.ItemRateData;

/**
 * 关卡扫荡内部消息
 * 
 * @author magicstone
 * 
 */
public class StageAutoBattleInternalMessage extends SceneScheduleMessage {
	private Human human;
	private int stageId;

	public StageAutoBattleInternalMessage(Human human, int stageId) {
		this.human = human;
		this.stageId = stageId;
	}

	@Override
	public void execute() {
		if (isCanceled()) {
			return;
		}

		// 判断是否有足够的体力
		if (SharedConstants.STAGE_ENERGY_NUM > human.getEnergy()) {
			human.sendWarningMessage(LangConstants.ENERGY_NOT_ENOUGH);
			human.getHumanAutoBattleManager().cancelAutoBattle();
			return;
		}

		// 判断背包是否已满
		if (human.getBagManager().isFull(BagType.MAIN_BAG)) {
			human.sendWarningMessage(LangConstants.BAG_IS_FULL);
			human.getHumanAutoBattleManager().cancelAutoBattle();
			return;
		}

		// 消耗精力值
		human.setEnergy(human.getEnergy() - SharedConstants.STAGE_ENERGY_NUM,
				EnergyLogReason.BATTLE_WITH_MAP_MONSTER_USE_ENERGY, "stageId:"
						+ stageId);

		// 战斗关卡对应的模版信息
		StageTemplate stageTemplate = GameServerAssist
				.getStageTemplateManager().getStageTemplate(stageId);
		if (stageTemplate == null) {
			return;
		}

		// 发送怪物死亡计时事件
		MonsterDeadEvent monsterDead = new MonsterDeadEvent();
		monsterDead.setMonsterId(stageTemplate.getMonsterId());
		this.human.getEventBus().fireEvent(monsterDead);

		// 发送战斗胜利事件
		BattleWinEvent battleWin = new BattleWinEvent();
		this.human.getEventBus().fireEvent(battleWin);

		// 获取掉落物品
		ItemRateData itemRate = stageTemplate.getRewardItems().get(0);
		boolean giveItem = false;
		if (itemRate.getItemId() > 0) {
			giveItem = MathUtils.shake(itemRate.getRate()
					/ SharedConstants.PROPERTY_PERCENT_DIVISOR);
		}
		float rewardRate = GameServerAssist.getStageTemplateManager()
				.getRewardRateByStar(
						human.getHumanStageManager().getStageStar(stageId));
		human.getHumanStageManager().getStageReward(
				GameServerAssist.getStageTemplateManager(), stageTemplate,
				rewardRate, giveItem, itemRate.getItemId());

		// 发扫荡结果
		GCStageAutoBattleResult gcMsg = new GCStageAutoBattleResult();
		gcMsg.setCoin((int) (stageTemplate.getRewardCurrencyNum() * rewardRate));
		gcMsg.setExp((int) (stageTemplate.getRewardExperience() * rewardRate));
		if (giveItem && itemRate.getItemId() > 0) {
			ItemTemplate itemTemplate = GameServerAssist
					.getItemTemplateManager().getItemTemplate(
							itemRate.getItemId());
			if (itemTemplate != null) {
				gcMsg.setItems(new String[] { itemTemplate.getName() });
			} else {
				gcMsg.setItems(new String[0]);
			}
		} else {
			gcMsg.setItems(new String[0]);
		}
		human.sendMessage(gcMsg);

		// 执行完之后remove
		human.getHumanAutoBattleManager().removeAutoBattleTask(this);
		// 判断背包是否已满
		if (human.getBagManager().isFull(BagType.MAIN_BAG)) {
			human.sendWarningMessage(LangConstants.BAG_IS_FULL);
			human.getHumanAutoBattleManager().cancelAutoBattle();
			return;
		}
		// 判断自动战斗cd时间是否已经到了
		if (human.getHumanCdManager().getRemainCd(CdType.AUTO_BATTLE) <= 0
				&& human.getHumanAutoBattleManager().isExecuteComplated()) {
			// 状态由扫荡中切换到游戏中
			human.getPlayer().transferStateTo(PlayerState.GAMEING);

			// 取消完成
			GCStageAutoBattleState gcUpdateMsg = new GCStageAutoBattleState();
			gcUpdateMsg.setState(AutoBattleStateType.INIT.getIndex());
			human.sendMessage(gcUpdateMsg);
		}
	}
}
