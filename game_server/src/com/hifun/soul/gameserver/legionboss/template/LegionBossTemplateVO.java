package com.hifun.soul.gameserver.legionboss.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 军团boss战模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionBossTemplateVO extends TemplateObject {

	/**  bossId */
	@ExcelCellBinding(offset = 1)
	protected int bossId;

	/**  奖励军团贡献总值 */
	@ExcelCellBinding(offset = 2)
	protected int totalContribution;

	/**  奖励荣誉总值 */
	@ExcelCellBinding(offset = 3)
	protected int totalHonour;

	/**  击杀奖励id */
	@ExcelCellBinding(offset = 4)
	protected int giftId;

	/**  鼓舞加成攻击力上限 */
	@ExcelCellBinding(offset = 5)
	protected int maxEncourageDamage;

	/**  每次鼓舞加成攻击力 */
	@ExcelCellBinding(offset = 6)
	protected int encourageDamage;

	/**  冥想力鼓舞消耗 */
	@ExcelCellBinding(offset = 7)
	protected int meditation;

	/**  冥想力鼓舞概率(基数10000) */
	@ExcelCellBinding(offset = 8)
	protected int meditationRate;

	/**  魔晶鼓舞消耗 */
	@ExcelCellBinding(offset = 9)
	protected int crystal;

	/**  魔晶鼓舞概率(基数10000) */
	@ExcelCellBinding(offset = 10)
	protected int crystalRate;

	/**  cd(分钟) */
	@ExcelCellBinding(offset = 11)
	protected int cd;

	/**  每次充能量(基数10000) */
	@ExcelCellBinding(offset = 12)
	protected int chargedRate;

	/**  能量上限(基数10000) */
	@ExcelCellBinding(offset = 13)
	protected int maxChargedRate;

	/**  每1%能量对应的伤害值 */
	@ExcelCellBinding(offset = 14)
	protected int chargedDamage;

	/**  充能满之后对应的伤害值 */
	@ExcelCellBinding(offset = 15)
	protected int maxChargedDamage;

	/**  伤害奖励金币值上限系数 */
	@ExcelCellBinding(offset = 16)
	protected int maxCoinRate;

	/**  伤害奖励荣誉值上限系数 */
	@ExcelCellBinding(offset = 17)
	protected int maxHonorRate;

	/**  阶段奖励总上限 */
	@ExcelCellBinding(offset = 18)
	protected int maxTotalStageReward;

	/**  每次阶段奖励上限 */
	@ExcelCellBinding(offset = 19)
	protected int maxSingleStageReward;

	/**  每次阶段奖励下限 */
	@ExcelCellBinding(offset = 20)
	protected int minSingleStageReward;

	/**  是否有累计伤害奖励 */
	@ExcelCellBinding(offset = 21)
	protected boolean hasDamageReward;


	public int getBossId() {
		return this.bossId;
	}

	public void setBossId(int bossId) {
		if (bossId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ bossId]bossId的值不得小于0");
		}
		this.bossId = bossId;
	}
	
	public int getTotalContribution() {
		return this.totalContribution;
	}

	public void setTotalContribution(int totalContribution) {
		if (totalContribution < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 奖励军团贡献总值]totalContribution的值不得小于0");
		}
		this.totalContribution = totalContribution;
	}
	
	public int getTotalHonour() {
		return this.totalHonour;
	}

	public void setTotalHonour(int totalHonour) {
		if (totalHonour < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 奖励荣誉总值]totalHonour的值不得小于0");
		}
		this.totalHonour = totalHonour;
	}
	
	public int getGiftId() {
		return this.giftId;
	}

	public void setGiftId(int giftId) {
		if (giftId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 击杀奖励id]giftId的值不得小于0");
		}
		this.giftId = giftId;
	}
	
	public int getMaxEncourageDamage() {
		return this.maxEncourageDamage;
	}

	public void setMaxEncourageDamage(int maxEncourageDamage) {
		if (maxEncourageDamage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 鼓舞加成攻击力上限]maxEncourageDamage的值不得小于0");
		}
		this.maxEncourageDamage = maxEncourageDamage;
	}
	
	public int getEncourageDamage() {
		return this.encourageDamage;
	}

	public void setEncourageDamage(int encourageDamage) {
		if (encourageDamage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 每次鼓舞加成攻击力]encourageDamage的值不得小于0");
		}
		this.encourageDamage = encourageDamage;
	}
	
	public int getMeditation() {
		return this.meditation;
	}

	public void setMeditation(int meditation) {
		if (meditation < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 冥想力鼓舞消耗]meditation的值不得小于0");
		}
		this.meditation = meditation;
	}
	
	public int getMeditationRate() {
		return this.meditationRate;
	}

	public void setMeditationRate(int meditationRate) {
		if (meditationRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 冥想力鼓舞概率(基数10000)]meditationRate的值不得小于0");
		}
		this.meditationRate = meditationRate;
	}
	
	public int getCrystal() {
		return this.crystal;
	}

	public void setCrystal(int crystal) {
		if (crystal < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 魔晶鼓舞消耗]crystal的值不得小于0");
		}
		this.crystal = crystal;
	}
	
	public int getCrystalRate() {
		return this.crystalRate;
	}

	public void setCrystalRate(int crystalRate) {
		if (crystalRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 魔晶鼓舞概率(基数10000)]crystalRate的值不得小于0");
		}
		this.crystalRate = crystalRate;
	}
	
	public int getCd() {
		return this.cd;
	}

	public void setCd(int cd) {
		if (cd < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ cd(分钟)]cd的值不得小于0");
		}
		this.cd = cd;
	}
	
	public int getChargedRate() {
		return this.chargedRate;
	}

	public void setChargedRate(int chargedRate) {
		if (chargedRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[ 每次充能量(基数10000)]chargedRate的值不得小于0");
		}
		this.chargedRate = chargedRate;
	}
	
	public int getMaxChargedRate() {
		return this.maxChargedRate;
	}

	public void setMaxChargedRate(int maxChargedRate) {
		if (maxChargedRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[ 能量上限(基数10000)]maxChargedRate的值不得小于0");
		}
		this.maxChargedRate = maxChargedRate;
	}
	
	public int getChargedDamage() {
		return this.chargedDamage;
	}

	public void setChargedDamage(int chargedDamage) {
		if (chargedDamage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[ 每1%能量对应的伤害值]chargedDamage的值不得小于0");
		}
		this.chargedDamage = chargedDamage;
	}
	
	public int getMaxChargedDamage() {
		return this.maxChargedDamage;
	}

	public void setMaxChargedDamage(int maxChargedDamage) {
		if (maxChargedDamage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[ 充能满之后对应的伤害值]maxChargedDamage的值不得小于0");
		}
		this.maxChargedDamage = maxChargedDamage;
	}
	
	public int getMaxCoinRate() {
		return this.maxCoinRate;
	}

	public void setMaxCoinRate(int maxCoinRate) {
		if (maxCoinRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[ 伤害奖励金币值上限系数]maxCoinRate不可以为0");
		}
		if (maxCoinRate < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[ 伤害奖励金币值上限系数]maxCoinRate的值不得小于1");
		}
		this.maxCoinRate = maxCoinRate;
	}
	
	public int getMaxHonorRate() {
		return this.maxHonorRate;
	}

	public void setMaxHonorRate(int maxHonorRate) {
		if (maxHonorRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[ 伤害奖励荣誉值上限系数]maxHonorRate的值不得小于0");
		}
		this.maxHonorRate = maxHonorRate;
	}
	
	public int getMaxTotalStageReward() {
		return this.maxTotalStageReward;
	}

	public void setMaxTotalStageReward(int maxTotalStageReward) {
		if (maxTotalStageReward == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[ 阶段奖励总上限]maxTotalStageReward不可以为0");
		}
		this.maxTotalStageReward = maxTotalStageReward;
	}
	
	public int getMaxSingleStageReward() {
		return this.maxSingleStageReward;
	}

	public void setMaxSingleStageReward(int maxSingleStageReward) {
		if (maxSingleStageReward == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					20, "[ 每次阶段奖励上限]maxSingleStageReward不可以为0");
		}
		this.maxSingleStageReward = maxSingleStageReward;
	}
	
	public int getMinSingleStageReward() {
		return this.minSingleStageReward;
	}

	public void setMinSingleStageReward(int minSingleStageReward) {
		if (minSingleStageReward == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[ 每次阶段奖励下限]minSingleStageReward不可以为0");
		}
		this.minSingleStageReward = minSingleStageReward;
	}
	
	public boolean isHasDamageReward() {
		return this.hasDamageReward;
	}

	public void setHasDamageReward(boolean hasDamageReward) {
		this.hasDamageReward = hasDamageReward;
	}
	

	@Override
	public String toString() {
		return "LegionBossTemplateVO[bossId=" + bossId + ",totalContribution=" + totalContribution + ",totalHonour=" + totalHonour + ",giftId=" + giftId + ",maxEncourageDamage=" + maxEncourageDamage + ",encourageDamage=" + encourageDamage + ",meditation=" + meditation + ",meditationRate=" + meditationRate + ",crystal=" + crystal + ",crystalRate=" + crystalRate + ",cd=" + cd + ",chargedRate=" + chargedRate + ",maxChargedRate=" + maxChargedRate + ",chargedDamage=" + chargedDamage + ",maxChargedDamage=" + maxChargedDamage + ",maxCoinRate=" + maxCoinRate + ",maxHonorRate=" + maxHonorRate + ",maxTotalStageReward=" + maxTotalStageReward + ",maxSingleStageReward=" + maxSingleStageReward + ",minSingleStageReward=" + minSingleStageReward + ",hasDamageReward=" + hasDamageReward + ",]";

	}
}