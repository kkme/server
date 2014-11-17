package com.hifun.soul.gameserver.helper;

public class HelperConverter {

	public HelpInfo converter(IHelper helper) {
		HelpInfo helpInfo = new HelpInfo();
		helpInfo.setHelpType(helper.getHelpType());
		helpInfo.setUsedTimes(helper.getUsedTimes());
		helpInfo.setState(helper.getState());
		helpInfo.setTotalTimes(helper.getTotalTimes());
		return helpInfo;
	}
}
