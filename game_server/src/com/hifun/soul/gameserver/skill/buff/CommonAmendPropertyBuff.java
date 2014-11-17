package com.hifun.soul.gameserver.skill.buff;

import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;
import com.hifun.soul.gameserver.skill.buff.template.BuffTemplate;
import com.hifun.soul.gameserver.skill.msg.GCStartBuffEffect;

/**
 * 属性加成的buff;
 * 
 * @author crazyjohn
 * 
 */
public class CommonAmendPropertyBuff extends CommonRoundBuff {
	protected int propId;
	protected int value;
	protected int amendType;

	/**
	 * @param target
	 * @param type
	 * @param lifeRound
	 * @param propId
	 * @param value
	 * @param amendType
	 * @param toWho
	 *            加buff的对象;
	 */
	public CommonAmendPropertyBuff(IBattleUnit target, BuffType type,
			BuffTemplate buffTemplate, int lifeRound, int propId, int value,
			int amendType) {
		super(target, type, buffTemplate, lifeRound);
		this.propId = propId;
		this.value = value;
		this.amendType = amendType;
	}

	@Override
	public void onAdd(boolean sendStart) {
		if (sendStart) {
			// 广播给战斗成员;
			IBattleContext context = this.target.getBattleContext();
			if (context == null) {
				return;
			}
			Battle battle = context.getBattle();
			if (battle == null) {
				return;
			}
			// 广播buff效果开始
			GCStartBuffEffect startBuffMsg = new GCStartBuffEffect();
			startBuffMsg.setTargetId(this.target.getUnitGuid());
			BuffInfo buffInfo = this.toBuffInfo();
			StringBuilder sb = new StringBuilder();
			sb.append(template.getBuffDesc());
			if (this.amendType == AmendMethod.ADD.getIndex()) {
				sb.append(type.getBuffAddAmendDesc(Math.abs(value)));
			} else {
				sb.append(Math.abs(value) / 100).append("%");
			}
			buffInfo.setBuffDesc(sb.toString());
			startBuffMsg.setBuffInfo(buffInfo);
			battle.broadcastToBattleUnits(startBuffMsg);
		}
		// 添加属性加成逻辑;
		this.target
				.getBattleContext()
				.getBattleProperty()
				.amendProperty(AmendMethod.valueOf(this.amendType), propId,
						value);
	}

	@Override
	public void onRemove(boolean sendStop) {
		// 解除属性加成逻辑
		this.target
				.getBattleContext()
				.getBattleProperty()
				.amendProperty(AmendMethod.valueOf(this.amendType), propId,
						-value);
		super.onRemove(sendStop);
	}

}
