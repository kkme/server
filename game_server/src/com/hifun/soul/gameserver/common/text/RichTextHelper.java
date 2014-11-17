package com.hifun.soul.gameserver.common.text;

import com.hifun.soul.gameserver.util.ColorUtils;

public class RichTextHelper {
	
	/**
	 * 为物品和人物添加链接
	 * @param text 链接的内容
	 * @param type 1 角色； 2可叠加物品；3、不可叠加物品(可能含有特殊属性,必须通过该物品所属角色去查询)
	 * @param key 角色id/物品itemid/物品uuid
	 * @return
	 */
	public static String addLink(String text,int type,String humanId,String params,String textColor){
		if(text==null || text.length()==0){
			return "";
		}
		if(textColor == null || textColor.length()==0){
			textColor = ColorUtils.RGB_YELLOW;
		}		
		String linkedContent = String.format("<font color=\"#%s\"><u><a href=\"event:%d,%s,%s\">%s</a></u></font>",textColor,type,humanId,params,text);			
		return linkedContent;
	}
}
