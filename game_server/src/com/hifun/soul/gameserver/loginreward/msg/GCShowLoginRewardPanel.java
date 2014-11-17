package com.hifun.soul.gameserver.loginreward.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 打开连续登陆面板
 *
 * @author SevenSoul
 */
@Component
public class GCShowLoginRewardPanel extends GCMessage{
	
	/** 连续登陆天数 */
	private int days;
	/** 剩余领取次数 */
	private int leftTimes;
	/** 总共领取次数 */
	private int totalTimes;
	/** 登陆天数和奖励次数的对应关系 */
	private com.hifun.soul.gameserver.loginreward.LoginRewardTimeInfo[] loginRewardTimeInfos;
	/** 选中的奖品 */
	private com.hifun.soul.gameserver.loginreward.LoginRewardInfo[] selectItems;
	/** 特殊奖品的信息 */
	private com.hifun.soul.gameserver.loginreward.SpecialLoginRewardInfo[] specialLoginRewardInfos;

	public GCShowLoginRewardPanel (){
	}
	
	public GCShowLoginRewardPanel (
			int days,
			int leftTimes,
			int totalTimes,
			com.hifun.soul.gameserver.loginreward.LoginRewardTimeInfo[] loginRewardTimeInfos,
			com.hifun.soul.gameserver.loginreward.LoginRewardInfo[] selectItems,
			com.hifun.soul.gameserver.loginreward.SpecialLoginRewardInfo[] specialLoginRewardInfos ){
			this.days = days;
			this.leftTimes = leftTimes;
			this.totalTimes = totalTimes;
			this.loginRewardTimeInfos = loginRewardTimeInfos;
			this.selectItems = selectItems;
			this.specialLoginRewardInfos = specialLoginRewardInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		days = readInteger();
		leftTimes = readInteger();
		totalTimes = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		loginRewardTimeInfos = new com.hifun.soul.gameserver.loginreward.LoginRewardTimeInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.loginreward.LoginRewardTimeInfo objloginRewardTimeInfos = new com.hifun.soul.gameserver.loginreward.LoginRewardTimeInfo();
			loginRewardTimeInfos[i] = objloginRewardTimeInfos;
					objloginRewardTimeInfos.setDays(readInteger());
							objloginRewardTimeInfos.setTimes(readInteger());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		selectItems = new com.hifun.soul.gameserver.loginreward.LoginRewardInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.loginreward.LoginRewardInfo objselectItems = new com.hifun.soul.gameserver.loginreward.LoginRewardInfo();
			selectItems[i] = objselectItems;
					objselectItems.setIndex(readInteger());
								{
	com.hifun.soul.gameserver.item.assist.SimpleCommonItem objcommonItem = new com.hifun.soul.gameserver.item.assist.SimpleCommonItem();
	objselectItems.setCommonItem(objcommonItem);
				objcommonItem.setUUID(readString());
				objcommonItem.setItemId(readInteger());
				objcommonItem.setName(readString());
				objcommonItem.setDesc(readString());
				objcommonItem.setIcon(readInteger());
			}
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		specialLoginRewardInfos = new com.hifun.soul.gameserver.loginreward.SpecialLoginRewardInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.loginreward.SpecialLoginRewardInfo objspecialLoginRewardInfos = new com.hifun.soul.gameserver.loginreward.SpecialLoginRewardInfo();
			specialLoginRewardInfos[i] = objspecialLoginRewardInfos;
					objspecialLoginRewardInfos.setDays(readInteger());
								{
	com.hifun.soul.gameserver.item.assist.SimpleCommonItem objreward = new com.hifun.soul.gameserver.item.assist.SimpleCommonItem();
	objspecialLoginRewardInfos.setReward(objreward);
				objreward.setUUID(readString());
				objreward.setItemId(readInteger());
				objreward.setName(readString());
				objreward.setDesc(readString());
				objreward.setIcon(readInteger());
			}
							objspecialLoginRewardInfos.setState(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(days);
		writeInteger(leftTimes);
		writeInteger(totalTimes);
	writeShort(loginRewardTimeInfos.length);
	for(int i=0; i<loginRewardTimeInfos.length; i++){
	com.hifun.soul.gameserver.loginreward.LoginRewardTimeInfo objloginRewardTimeInfos = loginRewardTimeInfos[i];
				writeInteger(objloginRewardTimeInfos.getDays());
				writeInteger(objloginRewardTimeInfos.getTimes());
	}
	writeShort(selectItems.length);
	for(int i=0; i<selectItems.length; i++){
	com.hifun.soul.gameserver.loginreward.LoginRewardInfo objselectItems = selectItems[i];
				writeInteger(objselectItems.getIndex());
					com.hifun.soul.gameserver.item.assist.SimpleCommonItem commonItem_objselectItems = objselectItems.getCommonItem();
					writeString(commonItem_objselectItems.getUUID());
					writeInteger(commonItem_objselectItems.getItemId());
					writeString(commonItem_objselectItems.getName());
					writeString(commonItem_objselectItems.getDesc());
					writeInteger(commonItem_objselectItems.getIcon());
			}
	writeShort(specialLoginRewardInfos.length);
	for(int i=0; i<specialLoginRewardInfos.length; i++){
	com.hifun.soul.gameserver.loginreward.SpecialLoginRewardInfo objspecialLoginRewardInfos = specialLoginRewardInfos[i];
				writeInteger(objspecialLoginRewardInfos.getDays());
					com.hifun.soul.gameserver.item.assist.SimpleCommonItem reward_objspecialLoginRewardInfos = objspecialLoginRewardInfos.getReward();
					writeString(reward_objspecialLoginRewardInfos.getUUID());
					writeInteger(reward_objspecialLoginRewardInfos.getItemId());
					writeString(reward_objspecialLoginRewardInfos.getName());
					writeString(reward_objspecialLoginRewardInfos.getDesc());
					writeInteger(reward_objspecialLoginRewardInfos.getIcon());
						writeInteger(objspecialLoginRewardInfos.getState());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_LOGIN_REWARD_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_LOGIN_REWARD_PANEL";
	}

	public int getDays(){
		return days;
	}
		
	public void setDays(int days){
		this.days = days;
	}

	public int getLeftTimes(){
		return leftTimes;
	}
		
	public void setLeftTimes(int leftTimes){
		this.leftTimes = leftTimes;
	}

	public int getTotalTimes(){
		return totalTimes;
	}
		
	public void setTotalTimes(int totalTimes){
		this.totalTimes = totalTimes;
	}

	public com.hifun.soul.gameserver.loginreward.LoginRewardTimeInfo[] getLoginRewardTimeInfos(){
		return loginRewardTimeInfos;
	}

	public void setLoginRewardTimeInfos(com.hifun.soul.gameserver.loginreward.LoginRewardTimeInfo[] loginRewardTimeInfos){
		this.loginRewardTimeInfos = loginRewardTimeInfos;
	}	

	public com.hifun.soul.gameserver.loginreward.LoginRewardInfo[] getSelectItems(){
		return selectItems;
	}

	public void setSelectItems(com.hifun.soul.gameserver.loginreward.LoginRewardInfo[] selectItems){
		this.selectItems = selectItems;
	}	

	public com.hifun.soul.gameserver.loginreward.SpecialLoginRewardInfo[] getSpecialLoginRewardInfos(){
		return specialLoginRewardInfos;
	}

	public void setSpecialLoginRewardInfos(com.hifun.soul.gameserver.loginreward.SpecialLoginRewardInfo[] specialLoginRewardInfos){
		this.specialLoginRewardInfos = specialLoginRewardInfos;
	}	
}