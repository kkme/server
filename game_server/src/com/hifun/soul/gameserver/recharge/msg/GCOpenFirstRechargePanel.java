package com.hifun.soul.gameserver.recharge.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应打开首充面板
 *
 * @author SevenSoul
 */
@Component
public class GCOpenFirstRechargePanel extends GCMessage{
	
	/** 总价值 */
	private int totalPrice;
	/** 首充奖励信息 */
	private com.hifun.soul.gameserver.recharge.msg.FirstRechargeRewardInfo[] rewardInfos;

	public GCOpenFirstRechargePanel (){
	}
	
	public GCOpenFirstRechargePanel (
			int totalPrice,
			com.hifun.soul.gameserver.recharge.msg.FirstRechargeRewardInfo[] rewardInfos ){
			this.totalPrice = totalPrice;
			this.rewardInfos = rewardInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		totalPrice = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		rewardInfos = new com.hifun.soul.gameserver.recharge.msg.FirstRechargeRewardInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.recharge.msg.FirstRechargeRewardInfo objrewardInfos = new com.hifun.soul.gameserver.recharge.msg.FirstRechargeRewardInfo();
			rewardInfos[i] = objrewardInfos;
					objrewardInfos.setItemNum(readInteger());
								{
	com.hifun.soul.gameserver.item.assist.SimpleCommonItem objcommonItem = new com.hifun.soul.gameserver.item.assist.SimpleCommonItem();
	objrewardInfos.setCommonItem(objcommonItem);
				objcommonItem.setUUID(readString());
				objcommonItem.setItemId(readInteger());
				objcommonItem.setName(readString());
				objcommonItem.setDesc(readString());
				objcommonItem.setIcon(readInteger());
			}
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(totalPrice);
	writeShort(rewardInfos.length);
	for(int i=0; i<rewardInfos.length; i++){
	com.hifun.soul.gameserver.recharge.msg.FirstRechargeRewardInfo objrewardInfos = rewardInfos[i];
				writeInteger(objrewardInfos.getItemNum());
					com.hifun.soul.gameserver.item.assist.SimpleCommonItem commonItem_objrewardInfos = objrewardInfos.getCommonItem();
					writeString(commonItem_objrewardInfos.getUUID());
					writeInteger(commonItem_objrewardInfos.getItemId());
					writeString(commonItem_objrewardInfos.getName());
					writeString(commonItem_objrewardInfos.getDesc());
					writeInteger(commonItem_objrewardInfos.getIcon());
			}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_FIRST_RECHARGE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_FIRST_RECHARGE_PANEL";
	}

	public int getTotalPrice(){
		return totalPrice;
	}
		
	public void setTotalPrice(int totalPrice){
		this.totalPrice = totalPrice;
	}

	public com.hifun.soul.gameserver.recharge.msg.FirstRechargeRewardInfo[] getRewardInfos(){
		return rewardInfos;
	}

	public void setRewardInfos(com.hifun.soul.gameserver.recharge.msg.FirstRechargeRewardInfo[] rewardInfos){
		this.rewardInfos = rewardInfos;
	}	
}