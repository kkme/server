package com.hifun.soul.gameserver.legion;

import com.hifun.soul.gameserver.role.properties.amend.AmendMethod;

/**
 * 军团科技加成
 * 
 * @author yandajun
 * 
 */
public class LegionTechnologyAmend {
	private AmendMethod amendMethod;
	private int amendValue;

	public AmendMethod getAmendMethod() {
		return amendMethod;
	}

	public void setAmendMethod(AmendMethod amendMethod) {
		this.amendMethod = amendMethod;
	}

	public int getAmendValue() {
		return amendValue;
	}

	public void setAmendValue(int amendValue) {
		this.amendValue = amendValue;
	}

}
