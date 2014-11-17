package com.hifun.soul.gameserver.rechargetx.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 打开充值面板
 *
 * @author SevenSoul
 */
@Component
public class GCShowRechargePanel extends GCMessage{
	
	/** 当前vip等级 */
	private int vipLevel;
	/** 当前充值金额 */
	private int rechargeNum;
	/** 下一个vip等级 */
	private int nextVipLevel;
	/** 达到下一个vip等级需要再充值的金额 */
	private int needRechargeNum;
	/** 充值档位信息 */
	private com.hifun.soul.gameserver.rechargetx.RechargeInfo[] rechargeInfos;
	/** 充值面板显示的vip信息 */
	private com.hifun.soul.gameserver.rechargetx.VipInfo[] vipInfos;

	public GCShowRechargePanel (){
	}
	
	public GCShowRechargePanel (
			int vipLevel,
			int rechargeNum,
			int nextVipLevel,
			int needRechargeNum,
			com.hifun.soul.gameserver.rechargetx.RechargeInfo[] rechargeInfos,
			com.hifun.soul.gameserver.rechargetx.VipInfo[] vipInfos ){
			this.vipLevel = vipLevel;
			this.rechargeNum = rechargeNum;
			this.nextVipLevel = nextVipLevel;
			this.needRechargeNum = needRechargeNum;
			this.rechargeInfos = rechargeInfos;
			this.vipInfos = vipInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		vipLevel = readInteger();
		rechargeNum = readInteger();
		nextVipLevel = readInteger();
		needRechargeNum = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		rechargeInfos = new com.hifun.soul.gameserver.rechargetx.RechargeInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.rechargetx.RechargeInfo objrechargeInfos = new com.hifun.soul.gameserver.rechargetx.RechargeInfo();
			rechargeInfos[i] = objrechargeInfos;
					objrechargeInfos.setId(readInteger());
							objrechargeInfos.setName(readString());
							objrechargeInfos.setDesc(readString());
							objrechargeInfos.setIcon(readInteger());
							objrechargeInfos.setCrystal(readInteger());
							objrechargeInfos.setPrice(readInteger());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		vipInfos = new com.hifun.soul.gameserver.rechargetx.VipInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.rechargetx.VipInfo objvipInfos = new com.hifun.soul.gameserver.rechargetx.VipInfo();
			vipInfos[i] = objvipInfos;
					objvipInfos.setVipId(readInteger());
							objvipInfos.setVipLevel(readInteger());
							objvipInfos.setVipNeedreChargeNum(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(vipLevel);
		writeInteger(rechargeNum);
		writeInteger(nextVipLevel);
		writeInteger(needRechargeNum);
	writeShort(rechargeInfos.length);
	for(int i=0; i<rechargeInfos.length; i++){
	com.hifun.soul.gameserver.rechargetx.RechargeInfo objrechargeInfos = rechargeInfos[i];
				writeInteger(objrechargeInfos.getId());
				writeString(objrechargeInfos.getName());
				writeString(objrechargeInfos.getDesc());
				writeInteger(objrechargeInfos.getIcon());
				writeInteger(objrechargeInfos.getCrystal());
				writeInteger(objrechargeInfos.getPrice());
	}
	writeShort(vipInfos.length);
	for(int i=0; i<vipInfos.length; i++){
	com.hifun.soul.gameserver.rechargetx.VipInfo objvipInfos = vipInfos[i];
				writeInteger(objvipInfos.getVipId());
				writeInteger(objvipInfos.getVipLevel());
				writeInteger(objvipInfos.getVipNeedreChargeNum());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_RECHARGE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_RECHARGE_PANEL";
	}

	public int getVipLevel(){
		return vipLevel;
	}
		
	public void setVipLevel(int vipLevel){
		this.vipLevel = vipLevel;
	}

	public int getRechargeNum(){
		return rechargeNum;
	}
		
	public void setRechargeNum(int rechargeNum){
		this.rechargeNum = rechargeNum;
	}

	public int getNextVipLevel(){
		return nextVipLevel;
	}
		
	public void setNextVipLevel(int nextVipLevel){
		this.nextVipLevel = nextVipLevel;
	}

	public int getNeedRechargeNum(){
		return needRechargeNum;
	}
		
	public void setNeedRechargeNum(int needRechargeNum){
		this.needRechargeNum = needRechargeNum;
	}

	public com.hifun.soul.gameserver.rechargetx.RechargeInfo[] getRechargeInfos(){
		return rechargeInfos;
	}

	public void setRechargeInfos(com.hifun.soul.gameserver.rechargetx.RechargeInfo[] rechargeInfos){
		this.rechargeInfos = rechargeInfos;
	}	

	public com.hifun.soul.gameserver.rechargetx.VipInfo[] getVipInfos(){
		return vipInfos;
	}

	public void setVipInfos(com.hifun.soul.gameserver.rechargetx.VipInfo[] vipInfos){
		this.vipInfos = vipInfos;
	}	
}