package com.hifun.soul.gameserver.guide;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * 新手引导
 * 
 * @author magicstone
 *
 */
public class Guide {
	
	/** 引导类型 */
	private int _guideType;
	/** 引导步骤 */
	private List<GuideStep> _guideStepList = Lists.newArrayList();

	/**
	 * 类参数构造器
	 * 
	 * @param guideType
	 * 
	 */
	public Guide(int guideType) {
		this._guideType = guideType;
	}

	/**
	 * 获取引导类型 Id
	 * 
	 * @return
	 */
	public int getGuideType() {
		return this._guideType;
	}

	/**
	 * 获取引导步骤
	 * 
	 * @return
	 */
	public List<GuideStep> getGuideStepList() {
		return this._guideStepList;
	}
}
