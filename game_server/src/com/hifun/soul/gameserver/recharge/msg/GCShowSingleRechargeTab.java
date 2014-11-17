package com.hifun.soul.gameserver.recharge.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应打开单笔充值标签
 *
 * @author SevenSoul
 */
@Component
public class GCShowSingleRechargeTab extends GCMessage{
	
	/** 活动描述 */
	private String activityDesc;
	/** 开始时间 */
	private String startDate;
	/** 结束时间 */
	private String endDate;
	/** 单笔充值档位信息 */
	private com.hifun.soul.gameserver.recharge.msg.SingleRechargeGradeInfo[] rewardGrades;

	public GCShowSingleRechargeTab (){
	}
	
	public GCShowSingleRechargeTab (
			String activityDesc,
			String startDate,
			String endDate,
			com.hifun.soul.gameserver.recharge.msg.SingleRechargeGradeInfo[] rewardGrades ){
			this.activityDesc = activityDesc;
			this.startDate = startDate;
			this.endDate = endDate;
			this.rewardGrades = rewardGrades;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		activityDesc = readString();
		startDate = readString();
		endDate = readString();
		count = readShort();
		count = count < 0 ? 0 : count;
		rewardGrades = new com.hifun.soul.gameserver.recharge.msg.SingleRechargeGradeInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.recharge.msg.SingleRechargeGradeInfo objrewardGrades = new com.hifun.soul.gameserver.recharge.msg.SingleRechargeGradeInfo();
			rewardGrades[i] = objrewardGrades;
					objrewardGrades.setGradeId(readInteger());
							objrewardGrades.setRechargeNum(readInteger());
							objrewardGrades.setRemainRewardNum(readInteger());
								{
	int subCountrewardInfos = readShort();
		com.hifun.soul.gameserver.recharge.msg.SingleRechargeRewardInfo[] subListrewardInfos = new com.hifun.soul.gameserver.recharge.msg.SingleRechargeRewardInfo[subCountrewardInfos];
		objrewardGrades.setRewardInfos(subListrewardInfos);
	for(int jrewardInfos = 0; jrewardInfos < subCountrewardInfos; jrewardInfos++){
						com.hifun.soul.gameserver.recharge.msg.SingleRechargeRewardInfo objrewardInfos = new com.hifun.soul.gameserver.recharge.msg.SingleRechargeRewardInfo();
		subListrewardInfos[jrewardInfos] = objrewardInfos;
						objrewardInfos.setItemNum(readInteger());
									{
	com.hifun.soul.gameserver.item.assist.SimpleCommonItem objcommonItem = new com.hifun.soul.gameserver.item.assist.SimpleCommonItem();
	objrewardInfos.setCommonItem(objcommonItem);
			}
							}
	}
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(activityDesc);
		writeString(startDate);
		writeString(endDate);
	writeShort(rewardGrades.length);
	for(int i=0; i<rewardGrades.length; i++){
	com.hifun.soul.gameserver.recharge.msg.SingleRechargeGradeInfo objrewardGrades = rewardGrades[i];
				writeInteger(objrewardGrades.getGradeId());
				writeInteger(objrewardGrades.getRechargeNum());
				writeInteger(objrewardGrades.getRemainRewardNum());
					com.hifun.soul.gameserver.recharge.msg.SingleRechargeRewardInfo[] rewardInfos_objrewardGrades=objrewardGrades.getRewardInfos();
	writeShort(rewardInfos_objrewardGrades.length);
	for(int jrewardInfos=0; jrewardInfos<rewardInfos_objrewardGrades.length; jrewardInfos++){
					com.hifun.soul.gameserver.recharge.msg.SingleRechargeRewardInfo rewardInfos_objrewardGrades_jrewardInfos = rewardInfos_objrewardGrades[jrewardInfos];
													writeInteger(rewardInfos_objrewardGrades_jrewardInfos.getItemNum());
														com.hifun.soul.gameserver.item.assist.SimpleCommonItem commonItem_rewardInfos_objrewardGrades_jrewardInfos = rewardInfos_objrewardGrades_jrewardInfos.getCommonItem();
											}
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_SINGLE_RECHARGE_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_SINGLE_RECHARGE_TAB";
	}

	public String getActivityDesc(){
		return activityDesc;
	}
		
	public void setActivityDesc(String activityDesc){
		this.activityDesc = activityDesc;
	}

	public String getStartDate(){
		return startDate;
	}
		
	public void setStartDate(String startDate){
		this.startDate = startDate;
	}

	public String getEndDate(){
		return endDate;
	}
		
	public void setEndDate(String endDate){
		this.endDate = endDate;
	}

	public com.hifun.soul.gameserver.recharge.msg.SingleRechargeGradeInfo[] getRewardGrades(){
		return rewardGrades;
	}

	public void setRewardGrades(com.hifun.soul.gameserver.recharge.msg.SingleRechargeGradeInfo[] rewardGrades){
		this.rewardGrades = rewardGrades;
	}	
}