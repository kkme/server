package com.hifun.soul.gameserver.human.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 功能提示集合
 *
 * @author SevenSoul
 */
@Component
public class GCFuncNotifies extends GCMessage{
	
	/** 功能开放信息列表 */
	private com.hifun.soul.gameserver.human.msg.FuncNotifyInfo[] funcNotifyInfos;

	public GCFuncNotifies (){
	}
	
	public GCFuncNotifies (
			com.hifun.soul.gameserver.human.msg.FuncNotifyInfo[] funcNotifyInfos ){
			this.funcNotifyInfos = funcNotifyInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		funcNotifyInfos = new com.hifun.soul.gameserver.human.msg.FuncNotifyInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.human.msg.FuncNotifyInfo objfuncNotifyInfos = new com.hifun.soul.gameserver.human.msg.FuncNotifyInfo();
			funcNotifyInfos[i] = objfuncNotifyInfos;
					objfuncNotifyInfos.setFuncType(readInteger());
							objfuncNotifyInfos.setNotify(readBoolean());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(funcNotifyInfos.length);
	for(int i=0; i<funcNotifyInfos.length; i++){
	com.hifun.soul.gameserver.human.msg.FuncNotifyInfo objfuncNotifyInfos = funcNotifyInfos[i];
				writeInteger(objfuncNotifyInfos.getFuncType());
				writeBoolean(objfuncNotifyInfos.getNotify());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_FUNC_NOTIFIES;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FUNC_NOTIFIES";
	}

	public com.hifun.soul.gameserver.human.msg.FuncNotifyInfo[] getFuncNotifyInfos(){
		return funcNotifyInfos;
	}

	public void setFuncNotifyInfos(com.hifun.soul.gameserver.human.msg.FuncNotifyInfo[] funcNotifyInfos){
		this.funcNotifyInfos = funcNotifyInfos;
	}	
}