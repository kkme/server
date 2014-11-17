package com.hifun.soul.gameserver.costnotify.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 更新消费提示设置
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateCostNotify extends GCMessage{
	
	/** 消费提示设置信息 */
	private com.hifun.soul.gameserver.costnotify.CostNotifyInfo[] costNotifyInfos;

	public GCUpdateCostNotify (){
	}
	
	public GCUpdateCostNotify (
			com.hifun.soul.gameserver.costnotify.CostNotifyInfo[] costNotifyInfos ){
			this.costNotifyInfos = costNotifyInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		costNotifyInfos = new com.hifun.soul.gameserver.costnotify.CostNotifyInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.costnotify.CostNotifyInfo objcostNotifyInfos = new com.hifun.soul.gameserver.costnotify.CostNotifyInfo();
			costNotifyInfos[i] = objcostNotifyInfos;
					objcostNotifyInfos.setCostNotifyType(readInteger());
							objcostNotifyInfos.setOpen(readBoolean());
							objcostNotifyInfos.setName(readString());
							objcostNotifyInfos.setDesc(readString());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(costNotifyInfos.length);
	for(int i=0; i<costNotifyInfos.length; i++){
	com.hifun.soul.gameserver.costnotify.CostNotifyInfo objcostNotifyInfos = costNotifyInfos[i];
				writeInteger(objcostNotifyInfos.getCostNotifyType());
				writeBoolean(objcostNotifyInfos.getOpen());
				writeString(objcostNotifyInfos.getName());
				writeString(objcostNotifyInfos.getDesc());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_COST_NOTIFY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_COST_NOTIFY";
	}

	public com.hifun.soul.gameserver.costnotify.CostNotifyInfo[] getCostNotifyInfos(){
		return costNotifyInfos;
	}

	public void setCostNotifyInfos(com.hifun.soul.gameserver.costnotify.CostNotifyInfo[] costNotifyInfos){
		this.costNotifyInfos = costNotifyInfos;
	}	
}