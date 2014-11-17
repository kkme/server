package com.hifun.soul.gameserver.costnotify.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 更新消费提示设置
 * 
 * @author SevenSoul
 */
@Component
public class CGUpdateCostNotify extends CGMessage{
	
	/** 消费提示设置信息 */
	private com.hifun.soul.gameserver.costnotify.CostNotifyInfo[] costNotifyInfos;
	
	public CGUpdateCostNotify (){
	}
	
	public CGUpdateCostNotify (
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
			com.hifun.soul.gameserver.costnotify.CostNotifyInfo obj  =new com.hifun.soul.gameserver.costnotify.CostNotifyInfo();
			obj.setCostNotifyType(readInteger());
			obj.setOpen(readBoolean());
			costNotifyInfos[i] = obj;
		}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(costNotifyInfos.length);
		for(int i=0; i<costNotifyInfos.length; i++){
			writeInteger(costNotifyInfos[i].getCostNotifyType());
			writeBoolean(costNotifyInfos[i].getOpen());
		}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_UPDATE_COST_NOTIFY;
	}
	
	@Override
	public String getTypeName() {
		return "CG_UPDATE_COST_NOTIFY";
	}

	public com.hifun.soul.gameserver.costnotify.CostNotifyInfo[] getCostNotifyInfos(){
		return costNotifyInfos;
	}

	public void setCostNotifyInfos(com.hifun.soul.gameserver.costnotify.CostNotifyInfo[] costNotifyInfos){
		this.costNotifyInfos = costNotifyInfos;
	}	

	@Override
	public void execute() {
	}
}