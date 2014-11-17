package com.hifun.soul.gameserver.sprite.template;

import com.hifun.soul.common.exception.TemplateConfigException;
import com.hifun.soul.core.annotation.ExcelRowBinding;
import com.hifun.soul.gameserver.sprite.model.SpritePubPageInfo;

/**
 * 精靈对酒消耗模版;
 * 
 * @author crazyjohn
 * 
 */
@ExcelRowBinding
public class SpriteCostTemplate extends SpriteCostTemplateVO {

	@Override
	public void check() throws TemplateConfigException {
		// TODO Auto-generated method stub

	}

	public SpritePubPageInfo toInfo() {
		SpritePubPageInfo result = new SpritePubPageInfo();
		result.setPageId(this.getId());
		result.setCommonCostNum(this.getCommonCostNum());
		result.setCommonCostType(this.getCommonCostType());
		result.setPageOpenLevel(this.getPageOpenLevel());
		result.setSuperCostNum(this.getSuperCostNum());
		result.setSuperCostType(this.getSuperCostType());
		result.setSuperFingerGuessOpenLevel(this.getSuperFingerGuessOpenLevel());
		result.setSuperFingerGuessVipOpenLevel(this
				.getSuperFingerGuessVipOpenLevel());
		return result;
	}

}
