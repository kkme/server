package com.hifun.soul.gameserver.skill.buff;

import com.hifun.soul.gameserver.battle.Battle;
import com.hifun.soul.gameserver.battle.IBattleUnit;
import com.hifun.soul.gameserver.battle.context.IBattleContext;
import com.hifun.soul.gameserver.battle.counter.AbstractRoudListener;
import com.hifun.soul.gameserver.skill.buff.template.BuffTemplate;
import com.hifun.soul.gameserver.skill.msg.GCStartBuffEffect;
import com.hifun.soul.gameserver.skill.msg.GCStopBuffEffect;
import com.hifun.soul.gameserver.skill.msg.GCUpdateBuffState;

/**
 * 基本的基于回合的buff;
 * 
 * @author crazyjohn
 * 
 */
public class CommonRoundBuff extends AbstractRoudListener implements IBuff {
	protected IBattleUnit target;
	protected BuffType type;
	/** buff的生存回合数 */
	protected int lifeRound;
	/** buff模版 */
	protected BuffTemplate template;
	private int buffId;

	public CommonRoundBuff(IBattleUnit target, BuffType type, BuffTemplate buffTemplate, int lifeRound) {
		this.target = target;
		this.type = type;
		this.lifeRound = lifeRound;
		// buff模版赋值;
		template = buffTemplate;
		// Id赋值
		this.buffId = target.getBattleContext().getBuffManager()
				.generateBuffId();
	}

	@Override
	public BuffType getType() {
		return type;
	}

	@Override
	public void onRoundFinished() {
		if (this.getActionTimes() % 2 != 0) {
			this.times++;
			return;
		}
		if (this.lifeRound > 0) {
			this.lifeRound--;
			// 广播给client
			GCUpdateBuffState updateBuffMsg = new GCUpdateBuffState();
			updateBuffMsg.setBuffId(this.buffId);
			updateBuffMsg.setBuffType(this.type.getIndex());
			updateBuffMsg.setTargetId(this.target.getUnitGuid());
			target.getBattleContext().getBattle()
					.broadcastToBattleUnits(updateBuffMsg);
		}
	}

	@Override
	public void onRemove(boolean sendStop) {
		if (sendStop) {
			IBattleContext context = this.target.getBattleContext();
			if (context == null) {
				return;
			}
			Battle battle = context.getBattle();
			if (battle == null) {
				return;
			}
			// 广播buff效果结束
			GCStopBuffEffect stopBuffMsg = new GCStopBuffEffect();
			stopBuffMsg.setTargetId(this.target.getUnitGuid());
			stopBuffMsg.setBuffType(this.type.getIndex());
			stopBuffMsg.setBuffId(buffId);
			battle.broadcastToBattleUnits(stopBuffMsg);
		}
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
			startBuffMsg.setBuffInfo(this.toBuffInfo());
			battle.broadcastToBattleUnits(startBuffMsg);
		}
	}

	protected void setBuffDesc(BuffTemplate template, BuffInfo buffInfo) {
		buffInfo.setBuffDesc(template.getBuffDesc());
	}

	@Override
	public int getLeftRound() {
		return this.lifeRound;
	}

	@Override
	public boolean needRemove() {
		return this.lifeRound <= 0;
	}

	@Override
	public void beforeAction() {
		// default do nothing

	}

	@Override
	public int getOverlyingCount() {
		return template.getOverlyingCount();
	}

	@Override
	public boolean isOverlapable() {
		return this.template.getOverlyingCount() > 1;
	}

	@Override
	public int getBuffId() {
		return buffId;
	}

	@Override
	public IBattleUnit getTarget() {
		return this.target;
	}

	@Override
	public BuffInfo toBuffInfo() {
		BuffInfo buffInfo = new BuffInfo();
		setBuffDesc(template, buffInfo);
		buffInfo.setBuffName(template.getBuffName());
		buffInfo.setBuffSelfType(template.getBuffSelfType());
		buffInfo.setBuffType(this.type.getIndex());
		buffInfo.setRound(this.lifeRound);
		buffInfo.setBuffId(buffId);
		buffInfo.setBuffResourceId(this.template.getBuffResourceId());
		return buffInfo;
	}

	@Override
	public BuffTemplate getBuffTemplate() {
		return template;
	}

}
