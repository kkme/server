package com.hifun.soul.gameserver.loginreward.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 领取奖励
 *
 * @author SevenSoul
 */
@Component
public class GCGetReward extends GCMessage{
	
	/** 次数 */
	private int times;
	/** 选中的奖品 */
	private com.hifun.soul.gameserver.loginreward.LoginRewardInfo selectItem;

	public GCGetReward (){
	}
	
	public GCGetReward (
			int times,
			com.hifun.soul.gameserver.loginreward.LoginRewardInfo selectItem ){
			this.times = times;
			this.selectItem = selectItem;
	}

	@Override
	protected boolean readImpl() {
		times = readInteger();
		selectItem = new com.hifun.soul.gameserver.loginreward.LoginRewardInfo();
						selectItem.setIndex(readInteger());
							{
	com.hifun.soul.gameserver.item.assist.SimpleCommonItem objcommonItem = new com.hifun.soul.gameserver.item.assist.SimpleCommonItem();
	selectItem.setCommonItem(objcommonItem);
				objcommonItem.setUUID(readString());
				objcommonItem.setItemId(readInteger());
				objcommonItem.setName(readString());
				objcommonItem.setDesc(readString());
				objcommonItem.setIcon(readInteger());
			}
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(times);
		writeInteger(selectItem.getIndex());
			com.hifun.soul.gameserver.item.assist.SimpleCommonItem commonItem_selectItem = selectItem.getCommonItem();
					writeString(commonItem_selectItem.getUUID());
					writeInteger(commonItem_selectItem.getItemId());
					writeString(commonItem_selectItem.getName());
					writeString(commonItem_selectItem.getDesc());
					writeInteger(commonItem_selectItem.getIcon());
				return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_GET_REWARD;
	}
	
	@Override
	public String getTypeName() {
		return "GC_GET_REWARD";
	}

	public int getTimes(){
		return times;
	}
		
	public void setTimes(int times){
		this.times = times;
	}

	public com.hifun.soul.gameserver.loginreward.LoginRewardInfo getSelectItem(){
		return selectItem;
	}
		
	public void setSelectItem(com.hifun.soul.gameserver.loginreward.LoginRewardInfo selectItem){
		this.selectItem = selectItem;
	}
}