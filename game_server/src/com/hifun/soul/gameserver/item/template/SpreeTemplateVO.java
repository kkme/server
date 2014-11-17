package com.hifun.soul.gameserver.item.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.template.ExcelCollectionMapping;
import java.util.List;

/**
 * 礼包模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class SpreeTemplateVO extends TemplateObject {

	/** 礼包id */
	@ExcelCellBinding(offset = 1)
	protected int itemCount;

	/** 金币数量 */
	@ExcelCellBinding(offset = 2)
	protected int coinNum;

	/** 金币出现的权重 */
	@ExcelCellBinding(offset = 3)
	protected int coinWeight;

	/** 魔晶数量 */
	@ExcelCellBinding(offset = 4)
	protected int crystalNum;

	/** 魔晶出现的权重 */
	@ExcelCellBinding(offset = 5)
	protected int crystalWeight;

	/**  所含物品 */
	@ExcelCollectionMapping(clazz = com.hifun.soul.gameserver.item.template.SpreeItemInfo.class, collectionNumber = "6,7,8;9,10,11;12,13,14;15,16,17;18,19,20")
	protected List<com.hifun.soul.gameserver.item.template.SpreeItemInfo> items;

	/** 经验数量 */
	@ExcelCellBinding(offset = 21)
	protected int expNum;

	/** 经验的权重 */
	@ExcelCellBinding(offset = 22)
	protected int expWeight;

	/** 荣誉数量 */
	@ExcelCellBinding(offset = 23)
	protected int honourNum;

	/** 荣誉的权重 */
	@ExcelCellBinding(offset = 24)
	protected int honourWeight;

	/** 技能点数量 */
	@ExcelCellBinding(offset = 25)
	protected int skillPointNum;

	/** 技能点的权重 */
	@ExcelCellBinding(offset = 26)
	protected int skillPointWeight;

	/** 科技点数量 */
	@ExcelCellBinding(offset = 27)
	protected int techPointNum;

	/** 科技点的权重 */
	@ExcelCellBinding(offset = 28)
	protected int techPointWeight;

	/** 星运id */
	@ExcelCellBinding(offset = 29)
	protected String horoscopeIds;

	/** 星运数量 */
	@ExcelCellBinding(offset = 30)
	protected String horoscopeNums;

	/** 星运的权重 */
	@ExcelCellBinding(offset = 31)
	protected int horoscopeWeight;

	/** 精灵id */
	@ExcelCellBinding(offset = 32)
	protected String spriteIds;

	/** 精灵数量 */
	@ExcelCellBinding(offset = 33)
	protected String spriteNums;

	/** 精灵的权重 */
	@ExcelCellBinding(offset = 34)
	protected int spriteWeight;

	/** 培养币数量 */
	@ExcelCellBinding(offset = 35)
	protected int trainCoinNum;

	/** 培养币出现的权重 */
	@ExcelCellBinding(offset = 36)
	protected int trainCoinWeight;

	/** 灵气值数量 */
	@ExcelCellBinding(offset = 37)
	protected int auraNum;

	/** 灵气值出现的权重 */
	@ExcelCellBinding(offset = 38)
	protected int auraWeight;

	/** 星魂数量 */
	@ExcelCellBinding(offset = 39)
	protected int starSoulNum;

	/** 星魂出现的权重 */
	@ExcelCellBinding(offset = 40)
	protected int starSoulWeight;


	public int getItemCount() {
		return this.itemCount;
	}

	public void setItemCount(int itemCount) {
		if (itemCount == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[礼包id]itemCount不可以为0");
		}
		this.itemCount = itemCount;
	}
	
	public int getCoinNum() {
		return this.coinNum;
	}

	public void setCoinNum(int coinNum) {
		if (coinNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[金币数量]coinNum的值不得小于0");
		}
		this.coinNum = coinNum;
	}
	
	public int getCoinWeight() {
		return this.coinWeight;
	}

	public void setCoinWeight(int coinWeight) {
		if (coinWeight < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[金币出现的权重]coinWeight的值不得小于0");
		}
		this.coinWeight = coinWeight;
	}
	
	public int getCrystalNum() {
		return this.crystalNum;
	}

	public void setCrystalNum(int crystalNum) {
		if (crystalNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[魔晶数量]crystalNum的值不得小于0");
		}
		this.crystalNum = crystalNum;
	}
	
	public int getCrystalWeight() {
		return this.crystalWeight;
	}

	public void setCrystalWeight(int crystalWeight) {
		if (crystalWeight < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[魔晶出现的权重]crystalWeight的值不得小于0");
		}
		this.crystalWeight = crystalWeight;
	}
	
	public List<com.hifun.soul.gameserver.item.template.SpreeItemInfo> getItems() {
		return this.items;
	}

	public void setItems(List<com.hifun.soul.gameserver.item.template.SpreeItemInfo> items) {
		if (items == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 所含物品]items不可以为空");
		}	
		this.items = items;
	}
	
	public int getExpNum() {
		return this.expNum;
	}

	public void setExpNum(int expNum) {
		if (expNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					22, "[经验数量]expNum的值不得小于0");
		}
		this.expNum = expNum;
	}
	
	public int getExpWeight() {
		return this.expWeight;
	}

	public void setExpWeight(int expWeight) {
		if (expWeight < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					23, "[经验的权重]expWeight的值不得小于0");
		}
		this.expWeight = expWeight;
	}
	
	public int getHonourNum() {
		return this.honourNum;
	}

	public void setHonourNum(int honourNum) {
		if (honourNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					24, "[荣誉数量]honourNum的值不得小于0");
		}
		this.honourNum = honourNum;
	}
	
	public int getHonourWeight() {
		return this.honourWeight;
	}

	public void setHonourWeight(int honourWeight) {
		if (honourWeight < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					25, "[荣誉的权重]honourWeight的值不得小于0");
		}
		this.honourWeight = honourWeight;
	}
	
	public int getSkillPointNum() {
		return this.skillPointNum;
	}

	public void setSkillPointNum(int skillPointNum) {
		if (skillPointNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					26, "[技能点数量]skillPointNum的值不得小于0");
		}
		this.skillPointNum = skillPointNum;
	}
	
	public int getSkillPointWeight() {
		return this.skillPointWeight;
	}

	public void setSkillPointWeight(int skillPointWeight) {
		if (skillPointWeight < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					27, "[技能点的权重]skillPointWeight的值不得小于0");
		}
		this.skillPointWeight = skillPointWeight;
	}
	
	public int getTechPointNum() {
		return this.techPointNum;
	}

	public void setTechPointNum(int techPointNum) {
		if (techPointNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					28, "[科技点数量]techPointNum的值不得小于0");
		}
		this.techPointNum = techPointNum;
	}
	
	public int getTechPointWeight() {
		return this.techPointWeight;
	}

	public void setTechPointWeight(int techPointWeight) {
		if (techPointWeight < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					29, "[科技点的权重]techPointWeight的值不得小于0");
		}
		this.techPointWeight = techPointWeight;
	}
	
	public String getHoroscopeIds() {
		return this.horoscopeIds;
	}

	public void setHoroscopeIds(String horoscopeIds) {
		this.horoscopeIds = horoscopeIds;
	}
	
	public String getHoroscopeNums() {
		return this.horoscopeNums;
	}

	public void setHoroscopeNums(String horoscopeNums) {
		this.horoscopeNums = horoscopeNums;
	}
	
	public int getHoroscopeWeight() {
		return this.horoscopeWeight;
	}

	public void setHoroscopeWeight(int horoscopeWeight) {
		if (horoscopeWeight < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					32, "[星运的权重]horoscopeWeight的值不得小于0");
		}
		this.horoscopeWeight = horoscopeWeight;
	}
	
	public String getSpriteIds() {
		return this.spriteIds;
	}

	public void setSpriteIds(String spriteIds) {
		this.spriteIds = spriteIds;
	}
	
	public String getSpriteNums() {
		return this.spriteNums;
	}

	public void setSpriteNums(String spriteNums) {
		this.spriteNums = spriteNums;
	}
	
	public int getSpriteWeight() {
		return this.spriteWeight;
	}

	public void setSpriteWeight(int spriteWeight) {
		if (spriteWeight < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					35, "[精灵的权重]spriteWeight的值不得小于0");
		}
		this.spriteWeight = spriteWeight;
	}
	
	public int getTrainCoinNum() {
		return this.trainCoinNum;
	}

	public void setTrainCoinNum(int trainCoinNum) {
		if (trainCoinNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					36, "[培养币数量]trainCoinNum的值不得小于0");
		}
		this.trainCoinNum = trainCoinNum;
	}
	
	public int getTrainCoinWeight() {
		return this.trainCoinWeight;
	}

	public void setTrainCoinWeight(int trainCoinWeight) {
		if (trainCoinWeight < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					37, "[培养币出现的权重]trainCoinWeight的值不得小于0");
		}
		this.trainCoinWeight = trainCoinWeight;
	}
	
	public int getAuraNum() {
		return this.auraNum;
	}

	public void setAuraNum(int auraNum) {
		if (auraNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					38, "[灵气值数量]auraNum的值不得小于0");
		}
		this.auraNum = auraNum;
	}
	
	public int getAuraWeight() {
		return this.auraWeight;
	}

	public void setAuraWeight(int auraWeight) {
		if (auraWeight < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					39, "[灵气值出现的权重]auraWeight的值不得小于0");
		}
		this.auraWeight = auraWeight;
	}
	
	public int getStarSoulNum() {
		return this.starSoulNum;
	}

	public void setStarSoulNum(int starSoulNum) {
		if (starSoulNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					40, "[星魂数量]starSoulNum的值不得小于0");
		}
		this.starSoulNum = starSoulNum;
	}
	
	public int getStarSoulWeight() {
		return this.starSoulWeight;
	}

	public void setStarSoulWeight(int starSoulWeight) {
		if (starSoulWeight < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					41, "[星魂出现的权重]starSoulWeight的值不得小于0");
		}
		this.starSoulWeight = starSoulWeight;
	}
	

	@Override
	public String toString() {
		return "SpreeTemplateVO[itemCount=" + itemCount + ",coinNum=" + coinNum + ",coinWeight=" + coinWeight + ",crystalNum=" + crystalNum + ",crystalWeight=" + crystalWeight + ",items=" + items + ",expNum=" + expNum + ",expWeight=" + expWeight + ",honourNum=" + honourNum + ",honourWeight=" + honourWeight + ",skillPointNum=" + skillPointNum + ",skillPointWeight=" + skillPointWeight + ",techPointNum=" + techPointNum + ",techPointWeight=" + techPointWeight + ",horoscopeIds=" + horoscopeIds + ",horoscopeNums=" + horoscopeNums + ",horoscopeWeight=" + horoscopeWeight + ",spriteIds=" + spriteIds + ",spriteNums=" + spriteNums + ",spriteWeight=" + spriteWeight + ",trainCoinNum=" + trainCoinNum + ",trainCoinWeight=" + trainCoinWeight + ",auraNum=" + auraNum + ",auraWeight=" + auraWeight + ",starSoulNum=" + starSoulNum + ",starSoulWeight=" + starSoulWeight + ",]";

	}
}