package com.hifun.soul.gameserver.antiindulge.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 客户端返回平台接口所提供的收益倍率
 * 
 * @author SevenSoul
 */
@Component
public class CGUpdateRevenueRateResponse extends CGMessage{
	
	/** 收入倍率 */
	private float revenueRate;
	
	public CGUpdateRevenueRateResponse (){
	}
	
	public CGUpdateRevenueRateResponse (
			float revenueRate ){
			this.revenueRate = revenueRate;
	}
	
	@Override
	protected boolean readImpl() {
		revenueRate = readFloat();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeFloat(revenueRate);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_UPDATE_REVENUE_RATE_RESPONSE;
	}
	
	@Override
	public String getTypeName() {
		return "CG_UPDATE_REVENUE_RATE_RESPONSE";
	}

	public float getRevenueRate(){
		return revenueRate;
	}
		
	public void setRevenueRate(float revenueRate){
		this.revenueRate = revenueRate;
	}

	@Override
	public void execute() {
	}
}