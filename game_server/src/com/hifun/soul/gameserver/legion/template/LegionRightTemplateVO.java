package com.hifun.soul.gameserver.legion.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;

/**
 * 军团权限模板
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class LegionRightTemplateVO extends TemplateObject {

	/** 转让军团 */
	@ExcelCellBinding(offset = 1)
	protected int transferLegion;

	/** 审核申请 */
	@ExcelCellBinding(offset = 2)
	protected int checkApply;

	/** 剔除成员 */
	@ExcelCellBinding(offset = 3)
	protected int removeMember;

	/** 编辑公告 */
	@ExcelCellBinding(offset = 4)
	protected int editNotice;


	public int getTransferLegion() {
		return this.transferLegion;
	}

	public void setTransferLegion(int transferLegion) {
		if (transferLegion < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[转让军团]transferLegion的值不得小于0");
		}
		this.transferLegion = transferLegion;
	}
	
	public int getCheckApply() {
		return this.checkApply;
	}

	public void setCheckApply(int checkApply) {
		if (checkApply < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[审核申请]checkApply的值不得小于0");
		}
		this.checkApply = checkApply;
	}
	
	public int getRemoveMember() {
		return this.removeMember;
	}

	public void setRemoveMember(int removeMember) {
		if (removeMember < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[剔除成员]removeMember的值不得小于0");
		}
		this.removeMember = removeMember;
	}
	
	public int getEditNotice() {
		return this.editNotice;
	}

	public void setEditNotice(int editNotice) {
		if (editNotice < 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[编辑公告]editNotice的值不得小于0");
		}
		this.editNotice = editNotice;
	}
	

	@Override
	public String toString() {
		return "LegionRightTemplateVO[transferLegion=" + transferLegion + ",checkApply=" + checkApply + ",removeMember=" + removeMember + ",editNotice=" + editNotice + ",]";

	}
}