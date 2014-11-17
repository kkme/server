package com.hifun.soul.gameserver.skill.buff;

import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.skill.HpChangedType;
import com.hifun.soul.gameserver.skill.buff.template.BuffTemplate;
import com.hifun.soul.gameserver.skill.msg.GCBattleHpChanged;

/**
 * 治愈效果;
 * 
 * @author crazyjohn
 * 
 */
public class HealBuff extends CommonRoundBuff {
	/** 加血数值 */
	private int value;

	public HealBuff(IBattleUnit target, BuffType type, BuffTemplate template,
			int lifeRound, int value) {
		super(target, type, template, lifeRound);
		this.value = value;
	}

	@Override
	public void beforeAction() {
		// 加血并且给玩家发送消息通知
		IBattleContext context = this.target.getBattleContext();
		if (context == null) {
			return;
		}
		Battle battle = context.getBattle();
		if (battle == null) {
			return;
		}
		// 加血
		this.target
				.getBattleContext()
				.getBattleProperty()
				.setHp(this.target.getBattleContext().getBattleProperty()
						.getHp()
						+ this.value);
		GCBattleHpChanged changedHpMsg = new GCBattleHpChanged();
		changedHpMsg.setChangeHp(-this.value);
		changedHpMsg.setChangeType(HpChangedType.OTHER.getIndex());
		changedHpMsg.setTargetId(this.target.getUnitGuid());
		battle.broadcastToBattleUnits(changedHpMsg);
	}

	@Override
	protected void setBuffDesc(BuffTemplate template, BuffInfo buffInfo) {
		buffInfo.setBuffDesc(template.getBuffDesc() + Math.abs(value));
	}

}
