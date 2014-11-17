package com.hifun.soul.gameserver.stage.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.gameserver.stage.model.MapStrongHoldInfo;

/*
 * 关卡据点模版;
 */
@ExcelRowBinding
public class StageStrongholdTemplate extends StageStrongholdTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		// TODO Auto-generated method stub

	}

	/**
	 * 转化为和客户端通信的Info信息;
	 * 
	 * @return
	 */
	public MapStrongHoldInfo toInfo() {
		MapStrongHoldInfo result = new MapStrongHoldInfo();
		result.setBgId(this.getBgId());
		result.setDesc(this.getDesc());
		result.setName(this.getName());
		result.setResourceId(this.getRessourceId());
		result.setStrongholdId(this.getId());
		result.setX(this.getX());
		result.setY(this.getY());
		return result;
	}

}
