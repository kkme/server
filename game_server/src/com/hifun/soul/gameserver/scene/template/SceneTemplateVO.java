package com.hifun.soul.gameserver.scene.template;

import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelCellBinding;
import com.hifun.soul.core.template.TemplateObject;
import com.hifun.soul.core.util.StringUtils;

/**
 * 区域配置
 * 
 * @author SevenSoul
 */

@ExcelRowBinding
public abstract class SceneTemplateVO extends TemplateObject {

	/**  区域类型 ID */
	@ExcelCellBinding(offset = 1)
	protected int regionId;

	/**  区域名称 */
	@ExcelCellBinding(offset = 2)
	protected String regionName;


	public int getRegionId() {
		return this.regionId;
	}

	public void setRegionId(int regionId) {
		if (regionId == 0) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					2, "[ 区域类型 ID]regionId不可以为0");
		}
		this.regionId = regionId;
	}
	
	public String getRegionName() {
		return this.regionName;
	}

	public void setRegionName(String regionName) {
		if (StringUtils.isEmpty(regionName)) {
			throw new TemplateConfigException(this.getSheetName(), this.getId(),
					3, "[ 区域名称]regionName不可以为空");
		}
		this.regionName = regionName;
	}
	

	@Override
	public String toString() {
		return "SceneTemplateVO[regionId=" + regionId + ",regionName=" + regionName + ",]";

	}
}