package com.hifun.soul.gameserver.horoscope.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 占星师赌博模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class HoroscopeGambleTemplateVO extends TemplateObject {

	/**  占星师名字多语言 */
	@ExcelCellBinding(offset = 1)
	protected int nameLangId;

	/**  占星师名字 */
	@ExcelCellBinding(offset = 2)
	protected String name;

	/**  占星师说明多语言 */
	@ExcelCellBinding(offset = 3)
	protected int descLangId;

	/**  占星师说明 */
	@ExcelCellBinding(offset = 4)
	protected String desc;

	/**  消耗货币类型 */
	@ExcelCellBinding(offset = 5)
	protected Short costCurrencyType;

	/**  消耗数量 */
	@ExcelCellBinding(offset = 6)
	protected int costCurrencyNum;

	/**  开启下级占星师概率 */
	@ExcelCellBinding(offset = 7)
	protected int nextRate;

	/**  失败返回货币类型 */
	@ExcelCellBinding(offset = 9)
	protected Short failCurrencyType;

	/**  失败返回货币数量 */
	@ExcelCellBinding(offset = 10)
	protected int failCurrencyNum;

	/**  失败概率 */
	@ExcelCellBinding(offset = 11)
	protected int failRate;

	/**  绿色id */
	@ExcelCellBinding(offset = 12)
	protected String greenIds;

	/**  绿色出现概率 */
	@ExcelCellBinding(offset = 13)
	protected int greenRate;

	/**  蓝色id */
	@ExcelCellBinding(offset = 14)
	protected String blueIds;

	/**  蓝色概率 */
	@ExcelCellBinding(offset = 15)
	protected int blueRate;

	/**  紫色id */
	@ExcelCellBinding(offset = 16)
	protected String purpleIds;

	/**  紫色概率 */
	@ExcelCellBinding(offset = 17)
	protected int purpleRate;

	/**  黄色id */
	@ExcelCellBinding(offset = 18)
	protected String yellowIds;

	/**  黄色出现概率 */
	@ExcelCellBinding(offset = 19)
	protected int yellowRate;

	/**  icon */
	@ExcelCellBinding(offset = 20)
	protected int icon;

	/**  失败之后给予的物品 */
	@ExcelCellBinding(offset = 21)
	protected int failHoroscopeId;


	public int getNameLangId() {
		return this.nameLangId;
	}

	public void setNameLangId(int nameLangId) {
		if (nameLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 占星师名字多语言]nameLangId不可以为0");
		}
		if (nameLangId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 占星师名字多语言]nameLangId的值不得小于1");
		}
		this.nameLangId = nameLangId;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		if (StringUtils.isEmpty(name)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 占星师名字]name不可以为空");
		}
		this.name = name;
	}
	
	public int getDescLangId() {
		return this.descLangId;
	}

	public void setDescLangId(int descLangId) {
		if (descLangId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 占星师说明多语言]descLangId不可以为0");
		}
		if (descLangId < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 占星师说明多语言]descLangId的值不得小于1");
		}
		this.descLangId = descLangId;
	}
	
	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		if (StringUtils.isEmpty(desc)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 占星师说明]desc不可以为空");
		}
		this.desc = desc;
	}
	
	public Short getCostCurrencyType() {
		return this.costCurrencyType;
	}

	public void setCostCurrencyType(Short costCurrencyType) {
		if (costCurrencyType == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 消耗货币类型]costCurrencyType不可以为空");
		}	
		if (costCurrencyType < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 消耗货币类型]costCurrencyType的值不得小于1");
		}
		this.costCurrencyType = costCurrencyType;
	}
	
	public int getCostCurrencyNum() {
		return this.costCurrencyNum;
	}

	public void setCostCurrencyNum(int costCurrencyNum) {
		if (costCurrencyNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 消耗数量]costCurrencyNum不可以为0");
		}
		if (costCurrencyNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 消耗数量]costCurrencyNum的值不得小于1");
		}
		this.costCurrencyNum = costCurrencyNum;
	}
	
	public int getNextRate() {
		return this.nextRate;
	}

	public void setNextRate(int nextRate) {
		if (nextRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 开启下级占星师概率]nextRate的值不得小于0");
		}
		this.nextRate = nextRate;
	}
	
	public Short getFailCurrencyType() {
		return this.failCurrencyType;
	}

	public void setFailCurrencyType(Short failCurrencyType) {
		if (failCurrencyType == null) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 失败返回货币类型]failCurrencyType不可以为空");
		}	
		if (failCurrencyType < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 失败返回货币类型]failCurrencyType的值不得小于1");
		}
		this.failCurrencyType = failCurrencyType;
	}
	
	public int getFailCurrencyNum() {
		return this.failCurrencyNum;
	}

	public void setFailCurrencyNum(int failCurrencyNum) {
		if (failCurrencyNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 失败返回货币数量]failCurrencyNum不可以为0");
		}
		if (failCurrencyNum < 1) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 失败返回货币数量]failCurrencyNum的值不得小于1");
		}
		this.failCurrencyNum = failCurrencyNum;
	}
	
	public int getFailRate() {
		return this.failRate;
	}

	public void setFailRate(int failRate) {
		if (failRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ 失败概率]failRate的值不得小于0");
		}
		this.failRate = failRate;
	}
	
	public String getGreenIds() {
		return this.greenIds;
	}

	public void setGreenIds(String greenIds) {
		if (StringUtils.isEmpty(greenIds)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[ 绿色id]greenIds不可以为空");
		}
		this.greenIds = greenIds;
	}
	
	public int getGreenRate() {
		return this.greenRate;
	}

	public void setGreenRate(int greenRate) {
		if (greenRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[ 绿色出现概率]greenRate的值不得小于0");
		}
		this.greenRate = greenRate;
	}
	
	public String getBlueIds() {
		return this.blueIds;
	}

	public void setBlueIds(String blueIds) {
		if (StringUtils.isEmpty(blueIds)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[ 蓝色id]blueIds不可以为空");
		}
		this.blueIds = blueIds;
	}
	
	public int getBlueRate() {
		return this.blueRate;
	}

	public void setBlueRate(int blueRate) {
		if (blueRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[ 蓝色概率]blueRate的值不得小于0");
		}
		this.blueRate = blueRate;
	}
	
	public String getPurpleIds() {
		return this.purpleIds;
	}

	public void setPurpleIds(String purpleIds) {
		if (StringUtils.isEmpty(purpleIds)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[ 紫色id]purpleIds不可以为空");
		}
		this.purpleIds = purpleIds;
	}
	
	public int getPurpleRate() {
		return this.purpleRate;
	}

	public void setPurpleRate(int purpleRate) {
		if (purpleRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[ 紫色概率]purpleRate的值不得小于0");
		}
		this.purpleRate = purpleRate;
	}
	
	public String getYellowIds() {
		return this.yellowIds;
	}

	public void setYellowIds(String yellowIds) {
		if (StringUtils.isEmpty(yellowIds)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[ 黄色id]yellowIds不可以为空");
		}
		this.yellowIds = yellowIds;
	}
	
	public int getYellowRate() {
		return this.yellowRate;
	}

	public void setYellowRate(int yellowRate) {
		if (yellowRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					20, "[ 黄色出现概率]yellowRate的值不得小于0");
		}
		this.yellowRate = yellowRate;
	}
	
	public int getIcon() {
		return this.icon;
	}

	public void setIcon(int icon) {
		if (icon < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					21, "[ icon]icon的值不得小于0");
		}
		this.icon = icon;
	}
	
	public int getFailHoroscopeId() {
		return this.failHoroscopeId;
	}

	public void setFailHoroscopeId(int failHoroscopeId) {
		if (failHoroscopeId < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					22, "[ 失败之后给予的物品]failHoroscopeId的值不得小于0");
		}
		this.failHoroscopeId = failHoroscopeId;
	}
	

	@Override
	public String toString() {
		return "HoroscopeGambleTemplateVO[nameLangId=" + nameLangId + ",name=" + name + ",descLangId=" + descLangId + ",desc=" + desc + ",costCurrencyType=" + costCurrencyType + ",costCurrencyNum=" + costCurrencyNum + ",nextRate=" + nextRate + ",failCurrencyType=" + failCurrencyType + ",failCurrencyNum=" + failCurrencyNum + ",failRate=" + failRate + ",greenIds=" + greenIds + ",greenRate=" + greenRate + ",blueIds=" + blueIds + ",blueRate=" + blueRate + ",purpleIds=" + purpleIds + ",purpleRate=" + purpleRate + ",yellowIds=" + yellowIds + ",yellowRate=" + yellowRate + ",icon=" + icon + ",failHoroscopeId=" + failHoroscopeId + ",]";

	}
}