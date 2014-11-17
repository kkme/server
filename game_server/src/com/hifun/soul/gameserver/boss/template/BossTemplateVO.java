package com.hifun.soul.gameserver.boss.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * boss模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class BossTemplateVO extends TemplateObject {

	/**  bossId */
	@ExcelCellBinding(offset = 1)
	protected int bossId;

	/**  奖励金币总值 */
	@ExcelCellBinding(offset = 2)
	protected int totalCoin;

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

	/**  金币鼓舞消耗 */
	@ExcelCellBinding(offset = 7)
	protected int coin;

	/**  金币鼓舞概率(基数10000) */
	@ExcelCellBinding(offset = 8)
	protected int coinRate;

	/**  魔晶鼓舞消耗 */
	@ExcelCellBinding(offset = 9)
	protected int crystal;

	/**  魔晶鼓舞概率(基数10000) */
	@ExcelCellBinding(offset = 10)
	protected int crystalRate;

	/**  灵石鼓舞消耗 */
	@ExcelCellBinding(offset = 11)
	protected int forgeStoneCost;

	/**  灵石鼓舞概率(基数10000) */
	@ExcelCellBinding(offset = 12)
	protected int forgeStoneRate;

	/**  冥想力鼓舞消耗 */
	@ExcelCellBinding(offset = 13)
	protected int meditation;

	/**  冥想力鼓舞概率(基数10000) */
	@ExcelCellBinding(offset = 14)
	protected int meditationRate;

	/**  cd(分钟) */
	@ExcelCellBinding(offset = 15)
	protected int cd;

	/**  每次充能量(基数10000) */
	@ExcelCellBinding(offset = 16)
	protected int chargedRate;

	/**  能量上限(基数10000) */
	@ExcelCellBinding(offset = 17)
	protected int maxChargedRate;

	/**  每1%能量对应的伤害值 */
	@ExcelCellBinding(offset = 18)
	protected int chargedDamage;

	/**  充能满之后对应的伤害值 */
	@ExcelCellBinding(offset = 19)
	protected int maxChargedDamage;

	/**  伤害奖励金币值上限系数 */
	@ExcelCellBinding(offset = 20)
	protected int maxCoinRate;

	/**  伤害奖励荣誉值上限系数 */
	@ExcelCellBinding(offset = 21)
	protected int maxHonourRate;

	/**  阶段奖励总上限 */
	@ExcelCellBinding(offset = 22)
	protected int maxTotalStageReward;

	/**  每次阶段奖励上限 */
	@ExcelCellBinding(offset = 23)
	protected int maxSingleStageReward;

	/**  每次阶段奖励下限 */
	@ExcelCellBinding(offset = 24)
	protected int minSingleStageReward;

	/**  是否有累计伤害奖励 */
	@ExcelCellBinding(offset = 25)
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
	
	public int getTotalCoin() {
		return this.totalCoin;
	}

	public void setTotalCoin(int totalCoin) {
		if (totalCoin < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 奖励金币总值]totalCoin的值不得小于0");
		}
		this.totalCoin = totalCoin;
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
	
	public int getCoin() {
		return this.coin;
	}

	public void setCoin(int coin) {
		if (coin < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 金币鼓舞消耗]coin的值不得小于0");
		}
		this.coin = coin;
	}
	
	public int getCoinRate() {
		return this.coinRate;
	}

	public void setCoinRate(int coinRate) {
		if (coinRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 金币鼓舞概率(基数10000)]coinRate的值不得小于0");
		}
		this.coinRate = coinRate;
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
	
	public int getForgeStoneCost() {
		return this.forgeStoneCost;
	}

	public void setForgeStoneCost(int forgeStoneCost) {
		if (forgeStoneCost < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ 灵石鼓舞消耗]forgeStoneCost的值不得小于0");
		}
		this.forgeStoneCost = forgeStoneCost;
	}
	
	public int getForgeStoneRate() {
		return this.forgeStoneRate;
	}

	public void setForgeStoneRate(int forgeStoneRate) {
		if (forgeStoneRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[ 灵石鼓舞概率(基数10000)]forgeStoneRate的值不得小于0");
		}
		this.forgeStoneRate = forgeStoneRate;
	}
	
	public int getMeditation() {
		return this.meditation;
	}

	public void setMeditation(int meditation) {
		if (meditation < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[ 冥想力鼓舞消耗]meditation的值不得小于0");
		}
		this.meditation = meditation;
	}
	
	public int getMeditationRate() {
		return this.meditationRate;
	}

	public void setMeditationRate(int meditationRate) {
		if (meditationRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[ 冥想力鼓舞概率(基数10000)]meditationRate的值不得小于0");
		}
		this.meditationRate = meditationRate;
	}
	
	public int getCd() {
		return this.cd;
	}

	public void setCd(int cd) {
		if (cd < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[ cd(分钟)]cd的值不得小于0");
		}
		this.cd = cd;
	}
	
	public int getChargedRate() {
		return this.chargedRate;
	}

	public void setChargedRate(int chargedRate) {
		if (chargedRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[ 每次充能量(基数10000)]chargedRate的值不得小于0");
		}
		this.chargedRate = chargedRate;
	}
	
	public int getMaxChargedRate() {
		return this.maxChargedRate;
	}

	public void setMaxChargedRate(int maxChargedRate) {
		if (maxChargedRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[ 能量上限(基数10000)]maxChargedRate的值不得小于0");
		}
		this.maxChargedRate = maxChargedRate;
	}
	
	public int getChargedDamage() {
		return this.chargedDamage;
	}

	public void setChargedDamage(int chargedDamage) {
		if (chargedDamage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[ 每1%能量对应的伤害值]chargedDamage的值不得小于0");
		}
		this.chargedDamage = chargedDamage;
	}
	
	public int getMaxChargedDamage() {
		return this.maxChargedDamage;
	}

	public void setMaxChargedDamage(int maxChargedDamage) {
		if (maxChargedDamage < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					20, "[ 充能满之后对应的伤害值]maxChargedDamage的值不得小于0");
		}
		this.maxChargedDamage = maxChargedDamage;
	}
	
	public int getMaxCoinRate() {
		return this.maxCoinRate;
	}

	public void setMaxCoinRate(int maxCoinRate) {
		if (maxCoinRate == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[ 伤害奖励金币值上限系数]maxCoinRate不可以为0");
		}
		if (maxCoinRate < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[ 伤害奖励金币值上限系数]maxCoinRate的值不得小于1");
		}
		this.maxCoinRate = maxCoinRate;
	}
	
	public int getMaxHonourRate() {
		return this.maxHonourRate;
	}

	public void setMaxHonourRate(int maxHonourRate) {
		if (maxHonourRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					22, "[ 伤害奖励荣誉值上限系数]maxHonourRate的值不得小于0");
		}
		this.maxHonourRate = maxHonourRate;
	}
	
	public int getMaxTotalStageReward() {
		return this.maxTotalStageReward;
	}

	public void setMaxTotalStageReward(int maxTotalStageReward) {
		if (maxTotalStageReward < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					23, "[ 阶段奖励总上限]maxTotalStageReward的值不得小于0");
		}
		this.maxTotalStageReward = maxTotalStageReward;
	}
	
	public int getMaxSingleStageReward() {
		return this.maxSingleStageReward;
	}

	public void setMaxSingleStageReward(int maxSingleStageReward) {
		if (maxSingleStageReward < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					24, "[ 每次阶段奖励上限]maxSingleStageReward的值不得小于0");
		}
		this.maxSingleStageReward = maxSingleStageReward;
	}
	
	public int getMinSingleStageReward() {
		return this.minSingleStageReward;
	}

	public void setMinSingleStageReward(int minSingleStageReward) {
		if (minSingleStageReward < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					25, "[ 每次阶段奖励下限]minSingleStageReward的值不得小于0");
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
		return "BossTemplateVO[bossId=" + bossId + ",totalCoin=" + totalCoin + ",totalHonour=" + totalHonour + ",giftId=" + giftId + ",maxEncourageDamage=" + maxEncourageDamage + ",encourageDamage=" + encourageDamage + ",coin=" + coin + ",coinRate=" + coinRate + ",crystal=" + crystal + ",crystalRate=" + crystalRate + ",forgeStoneCost=" + forgeStoneCost + ",forgeStoneRate=" + forgeStoneRate + ",meditation=" + meditation + ",meditationRate=" + meditationRate + ",cd=" + cd + ",chargedRate=" + chargedRate + ",maxChargedRate=" + maxChargedRate + ",chargedDamage=" + chargedDamage + ",maxChargedDamage=" + maxChargedDamage + ",maxCoinRate=" + maxCoinRate + ",maxHonourRate=" + maxHonourRate + ",maxTotalStageReward=" + maxTotalStageReward + ",maxSingleStageReward=" + maxSingleStageReward + ",minSingleStageReward=" + minSingleStageReward + ",hasDamageReward=" + hasDamageReward + ",]";

	}
}