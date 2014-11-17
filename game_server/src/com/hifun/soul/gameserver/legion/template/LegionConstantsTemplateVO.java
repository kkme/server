package com.hifun.soul.gameserver.legion.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 军团常量模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionConstantsTemplateVO extends TemplateObject {

	/** 创建军团所需等级 */
	@ExcelCellBinding(offset = 1)
	protected int createLegionNeedLevel;

	/** 创建军团所需金币 */
	@ExcelCellBinding(offset = 2)
	protected int createLegionNeedCoin;

	/** 军团名称最大长度 */
	@ExcelCellBinding(offset = 3)
	protected int legionNameMaxLength;

	/**  军团公告最大长度 */
	@ExcelCellBinding(offset = 4)
	protected int legionNoticeMaxLength;

	/** 一次可申请军团数 */
	@ExcelCellBinding(offset = 5)
	protected int canApplyLegionNum;

	/** 响应申请时长 */
	@ExcelCellBinding(offset = 6)
	protected int respondApplyHour;

	/** 荣誉贡献值参数 */
	@ExcelCellBinding(offset = 7)
	protected int honorToContributionParam;

	/** 捐献科技获得贡献 */
	@ExcelCellBinding(offset = 8)
	protected int contributeTechContribution;

	/** 捐献科技获得勋章 */
	@ExcelCellBinding(offset = 9)
	protected int contributeTechMedal;

	/** 捐献科技获得军团资金 */
	@ExcelCellBinding(offset = 10)
	protected int contributeTechLegionCoin;

	/** 捐献科技单次消耗金币 */
	@ExcelCellBinding(offset = 11)
	protected int contributeTechCostCoin;

	/** 刷新任务消耗魔晶 */
	@ExcelCellBinding(offset = 12)
	protected int refreshTaskCostCrystal;

	/** 系统刷新时间点 */
	@ExcelCellBinding(offset = 13)
	protected String systemRefreshTaskTime;

	/** 与系统主题一致收益倍数 */
	@ExcelCellBinding(offset = 14)
	protected int sameThemeMultiple;

	/** 捐献1魔晶获得军团经验 */
	@ExcelCellBinding(offset = 15)
	protected int donateGetLegionExp;

	/** 捐献1魔晶获得个人贡献 */
	@ExcelCellBinding(offset = 16)
	protected int donateGetSelfContri;

	/** 捐献1魔晶获得军团勋章 */
	@ExcelCellBinding(offset = 17)
	protected int donateGetMedal;

	/** 捐献魔晶获得VIP等级 */
	@ExcelCellBinding(offset = 18)
	protected int donateNeedVip;


	public int getCreateLegionNeedLevel() {
		return this.createLegionNeedLevel;
	}

	public void setCreateLegionNeedLevel(int createLegionNeedLevel) {
		if (createLegionNeedLevel == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[创建军团所需等级]createLegionNeedLevel不可以为0");
		}
		this.createLegionNeedLevel = createLegionNeedLevel;
	}
	
	public int getCreateLegionNeedCoin() {
		return this.createLegionNeedCoin;
	}

	public void setCreateLegionNeedCoin(int createLegionNeedCoin) {
		if (createLegionNeedCoin == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[创建军团所需金币]createLegionNeedCoin不可以为0");
		}
		this.createLegionNeedCoin = createLegionNeedCoin;
	}
	
	public int getLegionNameMaxLength() {
		return this.legionNameMaxLength;
	}

	public void setLegionNameMaxLength(int legionNameMaxLength) {
		if (legionNameMaxLength == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[军团名称最大长度]legionNameMaxLength不可以为0");
		}
		this.legionNameMaxLength = legionNameMaxLength;
	}
	
	public int getLegionNoticeMaxLength() {
		return this.legionNoticeMaxLength;
	}

	public void setLegionNoticeMaxLength(int legionNoticeMaxLength) {
		if (legionNoticeMaxLength == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 军团公告最大长度]legionNoticeMaxLength不可以为0");
		}
		this.legionNoticeMaxLength = legionNoticeMaxLength;
	}
	
	public int getCanApplyLegionNum() {
		return this.canApplyLegionNum;
	}

	public void setCanApplyLegionNum(int canApplyLegionNum) {
		if (canApplyLegionNum == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[一次可申请军团数]canApplyLegionNum不可以为0");
		}
		this.canApplyLegionNum = canApplyLegionNum;
	}
	
	public int getRespondApplyHour() {
		return this.respondApplyHour;
	}

	public void setRespondApplyHour(int respondApplyHour) {
		if (respondApplyHour == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[响应申请时长]respondApplyHour不可以为0");
		}
		this.respondApplyHour = respondApplyHour;
	}
	
	public int getHonorToContributionParam() {
		return this.honorToContributionParam;
	}

	public void setHonorToContributionParam(int honorToContributionParam) {
		if (honorToContributionParam < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[荣誉贡献值参数]honorToContributionParam的值不得小于0");
		}
		this.honorToContributionParam = honorToContributionParam;
	}
	
	public int getContributeTechContribution() {
		return this.contributeTechContribution;
	}

	public void setContributeTechContribution(int contributeTechContribution) {
		if (contributeTechContribution < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[捐献科技获得贡献]contributeTechContribution的值不得小于0");
		}
		this.contributeTechContribution = contributeTechContribution;
	}
	
	public int getContributeTechMedal() {
		return this.contributeTechMedal;
	}

	public void setContributeTechMedal(int contributeTechMedal) {
		if (contributeTechMedal == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[捐献科技获得勋章]contributeTechMedal不可以为0");
		}
		this.contributeTechMedal = contributeTechMedal;
	}
	
	public int getContributeTechLegionCoin() {
		return this.contributeTechLegionCoin;
	}

	public void setContributeTechLegionCoin(int contributeTechLegionCoin) {
		if (contributeTechLegionCoin < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[捐献科技获得军团资金]contributeTechLegionCoin的值不得小于0");
		}
		this.contributeTechLegionCoin = contributeTechLegionCoin;
	}
	
	public int getContributeTechCostCoin() {
		return this.contributeTechCostCoin;
	}

	public void setContributeTechCostCoin(int contributeTechCostCoin) {
		if (contributeTechCostCoin == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[捐献科技单次消耗金币]contributeTechCostCoin不可以为0");
		}
		this.contributeTechCostCoin = contributeTechCostCoin;
	}
	
	public int getRefreshTaskCostCrystal() {
		return this.refreshTaskCostCrystal;
	}

	public void setRefreshTaskCostCrystal(int refreshTaskCostCrystal) {
		if (refreshTaskCostCrystal == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[刷新任务消耗魔晶]refreshTaskCostCrystal不可以为0");
		}
		this.refreshTaskCostCrystal = refreshTaskCostCrystal;
	}
	
	public String getSystemRefreshTaskTime() {
		return this.systemRefreshTaskTime;
	}

	public void setSystemRefreshTaskTime(String systemRefreshTaskTime) {
		if (StringUtils.isEmpty(systemRefreshTaskTime)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[系统刷新时间点]systemRefreshTaskTime不可以为空");
		}
		this.systemRefreshTaskTime = systemRefreshTaskTime;
	}
	
	public int getSameThemeMultiple() {
		return this.sameThemeMultiple;
	}

	public void setSameThemeMultiple(int sameThemeMultiple) {
		if (sameThemeMultiple == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[与系统主题一致收益倍数]sameThemeMultiple不可以为0");
		}
		this.sameThemeMultiple = sameThemeMultiple;
	}
	
	public int getDonateGetLegionExp() {
		return this.donateGetLegionExp;
	}

	public void setDonateGetLegionExp(int donateGetLegionExp) {
		if (donateGetLegionExp == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[捐献1魔晶获得军团经验]donateGetLegionExp不可以为0");
		}
		this.donateGetLegionExp = donateGetLegionExp;
	}
	
	public int getDonateGetSelfContri() {
		return this.donateGetSelfContri;
	}

	public void setDonateGetSelfContri(int donateGetSelfContri) {
		if (donateGetSelfContri == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					17, "[捐献1魔晶获得个人贡献]donateGetSelfContri不可以为0");
		}
		this.donateGetSelfContri = donateGetSelfContri;
	}
	
	public int getDonateGetMedal() {
		return this.donateGetMedal;
	}

	public void setDonateGetMedal(int donateGetMedal) {
		if (donateGetMedal < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					18, "[捐献1魔晶获得军团勋章]donateGetMedal的值不得小于0");
		}
		this.donateGetMedal = donateGetMedal;
	}
	
	public int getDonateNeedVip() {
		return this.donateNeedVip;
	}

	public void setDonateNeedVip(int donateNeedVip) {
		if (donateNeedVip < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					19, "[捐献魔晶获得VIP等级]donateNeedVip的值不得小于0");
		}
		this.donateNeedVip = donateNeedVip;
	}
	

	@Override
	public String toString() {
		return "LegionConstantsTemplateVO[createLegionNeedLevel=" + createLegionNeedLevel + ",createLegionNeedCoin=" + createLegionNeedCoin + ",legionNameMaxLength=" + legionNameMaxLength + ",legionNoticeMaxLength=" + legionNoticeMaxLength + ",canApplyLegionNum=" + canApplyLegionNum + ",respondApplyHour=" + respondApplyHour + ",honorToContributionParam=" + honorToContributionParam + ",contributeTechContribution=" + contributeTechContribution + ",contributeTechMedal=" + contributeTechMedal + ",contributeTechLegionCoin=" + contributeTechLegionCoin + ",contributeTechCostCoin=" + contributeTechCostCoin + ",refreshTaskCostCrystal=" + refreshTaskCostCrystal + ",systemRefreshTaskTime=" + systemRefreshTaskTime + ",sameThemeMultiple=" + sameThemeMultiple + ",donateGetLegionExp=" + donateGetLegionExp + ",donateGetSelfContri=" + donateGetSelfContri + ",donateGetMedal=" + donateGetMedal + ",donateNeedVip=" + donateNeedVip + ",]";

	}
}