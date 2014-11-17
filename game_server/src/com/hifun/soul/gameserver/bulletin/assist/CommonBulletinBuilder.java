package com.hifun.soul.gameserver.bulletin.assist;

import com.hifun.soul.gameserver.bulletin.Bulletin;

public class CommonBulletinBuilder {
	
	public static CommonBulletin convertFrom(Bulletin bulletin) {
		CommonBulletin commonBulletin = new CommonBulletin();
		commonBulletin.setContent(bulletin.getContent());
		commonBulletin.setLevel(bulletin.getLevel());
		commonBulletin.setShowTime(bulletin.getShowTime());
		return commonBulletin;
	}
	
	public static CommonBulletin buildCommonBulletin(String content,int level,int showTime){
		CommonBulletin bulletin = new CommonBulletin();
		bulletin.setContent(content);
		bulletin.setLevel(level);
		bulletin.setShowTime(showTime);
		return bulletin;
	}
}
