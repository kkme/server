package com.hifun.soul.gameserver.sprite.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 精灵模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class SpriteCostTemplateVO extends TemplateObject {

	/**  页签开启等级 */
	@ExcelCellBinding(offset = 1)
	protected int pageOpenLevel;

	/**  高级对酒开启等级 */
	@ExcelCellBinding(offset = 2)
	protected int superFingerGuessOpenLevel;

	/**  高级对酒vip开启等级 */
	@ExcelCellBinding(offset = 3)
	protected int superFingerGuessVipOpenLevel;

	/**  普通对酒消耗货币类型 */
	@ExcelCellBinding(offset = 4)
	protected int commonCostType;

	/**  普通对酒消耗货币数量 */
	@ExcelCellBinding(offset = 5)
	protected int commonCostNum;

	/**  高级对酒消耗货币类型 */
	@ExcelCellBinding(offset = 6)
	protected int superCostType;

	/**  高级对酒消耗货币类型 */
	@ExcelCellBinding(offset = 7)
	protected int superCostNum;

	/**  胜利0次返回比例 */
	@ExcelCellBinding(offset = 8)
	protected int zeroTimeReturnRate;

	/**  胜利1次返回比例 */
	@ExcelCellBinding(offset = 9)
	protected int oneTimeReturnRate;

	/**  胜利2次返回比例 */
	@ExcelCellBinding(offset = 10)
	protected int twoTimesReturnRate;


	public int getPageOpenLevel() {
		return this.pageOpenLevel;
	}

	public void setPageOpenLevel(int pageOpenLevel) {
		if (pageOpenLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 页签开启等级]pageOpenLevel的值不得小于0");
		}
		this.pageOpenLevel = pageOpenLevel;
	}
	
	public int getSuperFingerGuessOpenLevel() {
		return this.superFingerGuessOpenLevel;
	}

	public void setSuperFingerGuessOpenLevel(int superFingerGuessOpenLevel) {
		if (superFingerGuessOpenLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 高级对酒开启等级]superFingerGuessOpenLevel的值不得小于0");
		}
		this.superFingerGuessOpenLevel = superFingerGuessOpenLevel;
	}
	
	public int getSuperFingerGuessVipOpenLevel() {
		return this.superFingerGuessVipOpenLevel;
	}

	public void setSuperFingerGuessVipOpenLevel(int superFingerGuessVipOpenLevel) {
		if (superFingerGuessVipOpenLevel < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 高级对酒vip开启等级]superFingerGuessVipOpenLevel的值不得小于0");
		}
		this.superFingerGuessVipOpenLevel = superFingerGuessVipOpenLevel;
	}
	
	public int getCommonCostType() {
		return this.commonCostType;
	}

	public void setCommonCostType(int commonCostType) {
		if (commonCostType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 普通对酒消耗货币类型]commonCostType的值不得小于0");
		}
		this.commonCostType = commonCostType;
	}
	
	public int getCommonCostNum() {
		return this.commonCostNum;
	}

	public void setCommonCostNum(int commonCostNum) {
		if (commonCostNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 普通对酒消耗货币数量]commonCostNum的值不得小于0");
		}
		this.commonCostNum = commonCostNum;
	}
	
	public int getSuperCostType() {
		return this.superCostType;
	}

	public void setSuperCostType(int superCostType) {
		if (superCostType < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 高级对酒消耗货币类型]superCostType的值不得小于0");
		}
		this.superCostType = superCostType;
	}
	
	public int getSuperCostNum() {
		return this.superCostNum;
	}

	public void setSuperCostNum(int superCostNum) {
		if (superCostNum < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 高级对酒消耗货币类型]superCostNum的值不得小于0");
		}
		this.superCostNum = superCostNum;
	}
	
	public int getZeroTimeReturnRate() {
		return this.zeroTimeReturnRate;
	}

	public void setZeroTimeReturnRate(int zeroTimeReturnRate) {
		if (zeroTimeReturnRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 胜利0次返回比例]zeroTimeReturnRate的值不得小于0");
		}
		this.zeroTimeReturnRate = zeroTimeReturnRate;
	}
	
	public int getOneTimeReturnRate() {
		return this.oneTimeReturnRate;
	}

	public void setOneTimeReturnRate(int oneTimeReturnRate) {
		if (oneTimeReturnRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 胜利1次返回比例]oneTimeReturnRate的值不得小于0");
		}
		this.oneTimeReturnRate = oneTimeReturnRate;
	}
	
	public int getTwoTimesReturnRate() {
		return this.twoTimesReturnRate;
	}

	public void setTwoTimesReturnRate(int twoTimesReturnRate) {
		if (twoTimesReturnRate < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 胜利2次返回比例]twoTimesReturnRate的值不得小于0");
		}
		this.twoTimesReturnRate = twoTimesReturnRate;
	}
	

	@Override
	public String toString() {
		return "SpriteCostTemplateVO[pageOpenLevel=" + pageOpenLevel + ",superFingerGuessOpenLevel=" + superFingerGuessOpenLevel + ",superFingerGuessVipOpenLevel=" + superFingerGuessVipOpenLevel + ",commonCostType=" + commonCostType + ",commonCostNum=" + commonCostNum + ",superCostType=" + superCostType + ",superCostNum=" + superCostNum + ",zeroTimeReturnRate=" + zeroTimeReturnRate + ",oneTimeReturnRate=" + oneTimeReturnRate + ",twoTimesReturnRate=" + twoTimesReturnRate + ",]";

	}
}