package com.hifun.soul.gameserver.vip.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器返回VIP详情
 *
 * @author SevenSoul
 */
@Component
public class GCDispatchVipInfo extends GCMessage{
	
	/** vip特权内容 */
	private com.hifun.soul.gameserver.vip.model.VipPrivilege vipPrivilege;

	public GCDispatchVipInfo (){
	}
	
	public GCDispatchVipInfo (
			com.hifun.soul.gameserver.vip.model.VipPrivilege vipPrivilege ){
			this.vipPrivilege = vipPrivilege;
	}

	@Override
	protected boolean readImpl() {
		vipPrivilege = new com.hifun.soul.gameserver.vip.model.VipPrivilege();
						vipPrivilege.setMaxBuyAnswerBlessTimes(readInteger());
						vipPrivilege.setMaxBuyEnergyTimes(readInteger());
						vipPrivilege.setMaxArenaBuyTimes(readInteger());
						vipPrivilege.setMaxRefreshEliteStageTimes(readInteger());
						vipPrivilege.setMaxBuyMineFieldNum(readInteger());
						vipPrivilege.setMaxCrystalCollectNum(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(vipPrivilege.getMaxBuyAnswerBlessTimes());
		writeInteger(vipPrivilege.getMaxBuyEnergyTimes());
		writeInteger(vipPrivilege.getMaxArenaBuyTimes());
		writeInteger(vipPrivilege.getMaxRefreshEliteStageTimes());
		writeInteger(vipPrivilege.getMaxBuyMineFieldNum());
		writeInteger(vipPrivilege.getMaxCrystalCollectNum());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_DISPATCH_VIP_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_DISPATCH_VIP_INFO";
	}

	public com.hifun.soul.gameserver.vip.model.VipPrivilege getVipPrivilege(){
		return vipPrivilege;
	}
		
	public void setVipPrivilege(com.hifun.soul.gameserver.vip.model.VipPrivilege vipPrivilege){
		this.vipPrivilege = vipPrivilege;
	}
}