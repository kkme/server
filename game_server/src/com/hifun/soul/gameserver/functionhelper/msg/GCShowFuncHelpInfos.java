package com.hifun.soul.gameserver.functionhelper.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 请求功能助手信息
 *
 * @author SevenSoul
 */
@Component
public class GCShowFuncHelpInfos extends GCMessage{
	
	/** 功能列表 */
	private com.hifun.soul.gameserver.functionhelper.FuncHelperInfo[] funcInfoList;

	public GCShowFuncHelpInfos (){
	}
	
	public GCShowFuncHelpInfos (
			com.hifun.soul.gameserver.functionhelper.FuncHelperInfo[] funcInfoList ){
			this.funcInfoList = funcInfoList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		funcInfoList = new com.hifun.soul.gameserver.functionhelper.FuncHelperInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.functionhelper.FuncHelperInfo objfuncInfoList = new com.hifun.soul.gameserver.functionhelper.FuncHelperInfo();
			funcInfoList[i] = objfuncInfoList;
					objfuncInfoList.setId(readInteger());
							objfuncInfoList.setName(readString());
							objfuncInfoList.setDesc(readString());
							objfuncInfoList.setIcon(readInteger());
							objfuncInfoList.setOpenLevel(readInteger());
							objfuncInfoList.setOpenState(readBoolean());
								{
	int subCountfuncRevenues = readShort();
		com.hifun.soul.gameserver.functionhelper.FuncRevenue[] subListfuncRevenues = new com.hifun.soul.gameserver.functionhelper.FuncRevenue[subCountfuncRevenues];
		objfuncInfoList.setFuncRevenues(subListfuncRevenues);
	for(int jfuncRevenues = 0; jfuncRevenues < subCountfuncRevenues; jfuncRevenues++){
						com.hifun.soul.gameserver.functionhelper.FuncRevenue objfuncRevenues = new com.hifun.soul.gameserver.functionhelper.FuncRevenue();
		subListfuncRevenues[jfuncRevenues] = objfuncRevenues;
						objfuncRevenues.setName(readString());
								objfuncRevenues.setStar(readInteger());
							}
	}
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(funcInfoList.length);
	for(int i=0; i<funcInfoList.length; i++){
	com.hifun.soul.gameserver.functionhelper.FuncHelperInfo objfuncInfoList = funcInfoList[i];
				writeInteger(objfuncInfoList.getId());
				writeString(objfuncInfoList.getName());
				writeString(objfuncInfoList.getDesc());
				writeInteger(objfuncInfoList.getIcon());
				writeInteger(objfuncInfoList.getOpenLevel());
				writeBoolean(objfuncInfoList.getOpenState());
					com.hifun.soul.gameserver.functionhelper.FuncRevenue[] funcRevenues_objfuncInfoList=objfuncInfoList.getFuncRevenues();
	writeShort(funcRevenues_objfuncInfoList.length);
	for(int jfuncRevenues=0; jfuncRevenues<funcRevenues_objfuncInfoList.length; jfuncRevenues++){
					com.hifun.soul.gameserver.functionhelper.FuncRevenue funcRevenues_objfuncInfoList_jfuncRevenues = funcRevenues_objfuncInfoList[jfuncRevenues];
													writeString(funcRevenues_objfuncInfoList_jfuncRevenues.getName());
													writeInteger(funcRevenues_objfuncInfoList_jfuncRevenues.getStar());
									}
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_FUNC_HELP_INFOS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_FUNC_HELP_INFOS";
	}

	public com.hifun.soul.gameserver.functionhelper.FuncHelperInfo[] getFuncInfoList(){
		return funcInfoList;
	}

	public void setFuncInfoList(com.hifun.soul.gameserver.functionhelper.FuncHelperInfo[] funcInfoList){
		this.funcInfoList = funcInfoList;
	}	
}