package com.hifun.soul.gameserver.skill;

/**
 * 攻击结果;
 * 
 * @author crazyjohn
 * 
 */
public class EffectResult {
	private static EffectResult NULL_RESULT = new EffectResult();
	private int finalDamage;
	private boolean crit;
	private boolean parry;

	public int getFinalDamage() {
		return finalDamage;
	}

	public void setFinalDamage(int finalDamage) {
		this.finalDamage = finalDamage;
	}

	public boolean isCrit() {
		return crit;
	}

	public void setCrit(boolean crit) {
		this.crit = crit;
	}

	public boolean isParry() {
		return parry;
	}

	public void setParry(boolean parry) {
		this.parry = parry;
	}

	public static EffectResult createNullEffect() {
		return NULL_RESULT;
	}
}
