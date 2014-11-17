package com.hifun.soul.gameserver.compass;

import java.util.HashMap;
import java.util.Map;

import com.hifun.soul.core.enums.IndexedEnum;

/**
 * 腾讯平台类型;
 * 
 * @author crazyjohn
 * 
 */
public enum QQDomainType implements IndexedEnum {
	/** 空间 */
	QZONE(1, "qzone"), 
	/** 朋友 */
	PENGYOU(2, "");

	private int type;
	private String desc;
	private static Map<String, QQDomainType> plats = new HashMap<String, QQDomainType>();
	
	static {
		for (QQDomainType each : QQDomainType.values()) {
			plats.put(each.getPlatForm(), each);
		}
	}

	QQDomainType(int type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	@Override
	public int getIndex() {
		return type;
	}
	
	public String getPlatForm() {
		return this.desc;
	}
	
	public static QQDomainType platOf(String plat) {
		return plats.get(plat);
	}
}
