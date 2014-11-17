package com.hifun.soul.gameserver.human.occupation.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 角色职业模版
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class CharacterOccupationTemplateVO extends TemplateObject {

	/**  职业类型(1 = 战士; 2 = 游侠; 3 = 法师) */
	@ExcelCellBinding(offset = 1)
	protected int occupationType;

	/**  职业名称图标 */
	@ExcelCellBinding(offset = 2)
	protected int nameIcon;

	/**  职业模型ID;战斗时候使用; */
	@ExcelCellBinding(offset = 3)
	protected int resourceId;

	/**  职业特色 */
	@ExcelCellBinding(offset = 4)
	protected String feature;

	/**  职业系别 */
	@ExcelCellBinding(offset = 5)
	protected String classes;

	/**  天赋1图标id */
	@ExcelCellBinding(offset = 6)
	protected int giftIcon1;

	/**  天赋1名称 */
	@ExcelCellBinding(offset = 7)
	protected String giftName1;

	/**  天赋1详细文字说明 */
	@ExcelCellBinding(offset = 8)
	protected String giftDesc1;

	/**  天赋2图标id */
	@ExcelCellBinding(offset = 9)
	protected int giftIcon2;

	/**  天赋2名称 */
	@ExcelCellBinding(offset = 10)
	protected String giftName2;

	/**  天赋2详细文字说明 */
	@ExcelCellBinding(offset = 11)
	protected String giftDesc2;

	/**  天赋3图标id */
	@ExcelCellBinding(offset = 12)
	protected int giftIcon3;

	/**  天赋3名称 */
	@ExcelCellBinding(offset = 13)
	protected String giftName3;

	/**  天赋3详细文字说明 */
	@ExcelCellBinding(offset = 14)
	protected String giftDesc3;

	/**  出生礼包id */
	@ExcelCellBinding(offset = 15)
	protected int giftId;


	public int getOccupationType() {
		return this.occupationType;
	}

	public void setOccupationType(int occupationType) {
		if (occupationType == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 职业类型(1 = 战士; 2 = 游侠; 3 = 法师)]occupationType不可以为0");
		}
		this.occupationType = occupationType;
	}
	
	public int getNameIcon() {
		return this.nameIcon;
	}

	public void setNameIcon(int nameIcon) {
		if (nameIcon == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 职业名称图标]nameIcon不可以为0");
		}
		this.nameIcon = nameIcon;
	}
	
	public int getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(int resourceId) {
		if (resourceId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					4, "[ 职业模型ID;战斗时候使用;]resourceId不可以为0");
		}
		this.resourceId = resourceId;
	}
	
	public String getFeature() {
		return this.feature;
	}

	public void setFeature(String feature) {
		if (StringUtils.isEmpty(feature)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					5, "[ 职业特色]feature不可以为空");
		}
		this.feature = feature;
	}
	
	public String getClasses() {
		return this.classes;
	}

	public void setClasses(String classes) {
		if (StringUtils.isEmpty(classes)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					6, "[ 职业系别]classes不可以为空");
		}
		this.classes = classes;
	}
	
	public int getGiftIcon1() {
		return this.giftIcon1;
	}

	public void setGiftIcon1(int giftIcon1) {
		if (giftIcon1 == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					7, "[ 天赋1图标id]giftIcon1不可以为0");
		}
		this.giftIcon1 = giftIcon1;
	}
	
	public String getGiftName1() {
		return this.giftName1;
	}

	public void setGiftName1(String giftName1) {
		if (StringUtils.isEmpty(giftName1)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					8, "[ 天赋1名称]giftName1不可以为空");
		}
		this.giftName1 = giftName1;
	}
	
	public String getGiftDesc1() {
		return this.giftDesc1;
	}

	public void setGiftDesc1(String giftDesc1) {
		if (StringUtils.isEmpty(giftDesc1)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					9, "[ 天赋1详细文字说明]giftDesc1不可以为空");
		}
		this.giftDesc1 = giftDesc1;
	}
	
	public int getGiftIcon2() {
		return this.giftIcon2;
	}

	public void setGiftIcon2(int giftIcon2) {
		if (giftIcon2 == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					10, "[ 天赋2图标id]giftIcon2不可以为0");
		}
		this.giftIcon2 = giftIcon2;
	}
	
	public String getGiftName2() {
		return this.giftName2;
	}

	public void setGiftName2(String giftName2) {
		if (StringUtils.isEmpty(giftName2)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					11, "[ 天赋2名称]giftName2不可以为空");
		}
		this.giftName2 = giftName2;
	}
	
	public String getGiftDesc2() {
		return this.giftDesc2;
	}

	public void setGiftDesc2(String giftDesc2) {
		if (StringUtils.isEmpty(giftDesc2)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					12, "[ 天赋2详细文字说明]giftDesc2不可以为空");
		}
		this.giftDesc2 = giftDesc2;
	}
	
	public int getGiftIcon3() {
		return this.giftIcon3;
	}

	public void setGiftIcon3(int giftIcon3) {
		if (giftIcon3 == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					13, "[ 天赋3图标id]giftIcon3不可以为0");
		}
		this.giftIcon3 = giftIcon3;
	}
	
	public String getGiftName3() {
		return this.giftName3;
	}

	public void setGiftName3(String giftName3) {
		if (StringUtils.isEmpty(giftName3)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					14, "[ 天赋3名称]giftName3不可以为空");
		}
		this.giftName3 = giftName3;
	}
	
	public String getGiftDesc3() {
		return this.giftDesc3;
	}

	public void setGiftDesc3(String giftDesc3) {
		if (StringUtils.isEmpty(giftDesc3)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					15, "[ 天赋3详细文字说明]giftDesc3不可以为空");
		}
		this.giftDesc3 = giftDesc3;
	}
	
	public int getGiftId() {
		return this.giftId;
	}

	public void setGiftId(int giftId) {
		if (giftId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					16, "[ 出生礼包id]giftId不可以为0");
		}
		this.giftId = giftId;
	}
	

	@Override
	public String toString() {
		return "CharacterOccupationTemplateVO[occupationType=" + occupationType + ",nameIcon=" + nameIcon + ",resourceId=" + resourceId + ",feature=" + feature + ",classes=" + classes + ",giftIcon1=" + giftIcon1 + ",giftName1=" + giftName1 + ",giftDesc1=" + giftDesc1 + ",giftIcon2=" + giftIcon2 + ",giftName2=" + giftName2 + ",giftDesc2=" + giftDesc2 + ",giftIcon3=" + giftIcon3 + ",giftName3=" + giftName3 + ",giftDesc3=" + giftDesc3 + ",giftId=" + giftId + ",]";

	}
}