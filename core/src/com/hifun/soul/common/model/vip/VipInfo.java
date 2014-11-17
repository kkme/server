package com.hifun.soul.common.model.vip;

import java.util.Arrays;


public class VipInfo {
	
	private String vipName;
	
	private String[] vipDescs;
	
	public String getVipName() {
		return vipName;
	}

	public void setVipName(String vipName) {
		this.vipName = vipName;
	}
	
	public String[] getVipDescs() {
		return vipDescs;
	}

	public void setVipDescs(String[] vipDescs) {
		this.vipDescs = vipDescs;
	}

	@Override
	public String toString() {
		return "VipInfo [name=" + vipName 
				+ ", baseAttrs=" + Arrays.toString(vipDescs) + "]";
	}
}
