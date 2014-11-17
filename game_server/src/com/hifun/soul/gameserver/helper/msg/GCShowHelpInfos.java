package com.hifun.soul.gameserver.helper.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 返回小助手信息
 *
 * @author SevenSoul
 */
@Component
public class GCShowHelpInfos extends GCMessage{
	
	/** 小助手信息 */
	private com.hifun.soul.gameserver.helper.HelpInfo[] helpInfoList;
	/** 是否需要打开板子 */
	private boolean isNeedOpen;

	public GCShowHelpInfos (){
	}
	
	public GCShowHelpInfos (
			com.hifun.soul.gameserver.helper.HelpInfo[] helpInfoList,
			boolean isNeedOpen ){
			this.helpInfoList = helpInfoList;
			this.isNeedOpen = isNeedOpen;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		helpInfoList = new com.hifun.soul.gameserver.helper.HelpInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.helper.HelpInfo objhelpInfoList = new com.hifun.soul.gameserver.helper.HelpInfo();
			helpInfoList[i] = objhelpInfoList;
					objhelpInfoList.setHelpType(readInteger());
							objhelpInfoList.setState(readInteger());
							objhelpInfoList.setUsedTimes(readInteger());
							objhelpInfoList.setTotalTimes(readInteger());
				}
		isNeedOpen = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(helpInfoList.length);
	for(int i=0; i<helpInfoList.length; i++){
	com.hifun.soul.gameserver.helper.HelpInfo objhelpInfoList = helpInfoList[i];
				writeInteger(objhelpInfoList.getHelpType());
				writeInteger(objhelpInfoList.getState());
				writeInteger(objhelpInfoList.getUsedTimes());
				writeInteger(objhelpInfoList.getTotalTimes());
	}
		writeBoolean(isNeedOpen);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_HELP_INFOS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_HELP_INFOS";
	}

	public com.hifun.soul.gameserver.helper.HelpInfo[] getHelpInfoList(){
		return helpInfoList;
	}

	public void setHelpInfoList(com.hifun.soul.gameserver.helper.HelpInfo[] helpInfoList){
		this.helpInfoList = helpInfoList;
	}	

	public boolean getIsNeedOpen(){
		return isNeedOpen;
	}
		
	public void setIsNeedOpen(boolean isNeedOpen){
		this.isNeedOpen = isNeedOpen;
	}
}