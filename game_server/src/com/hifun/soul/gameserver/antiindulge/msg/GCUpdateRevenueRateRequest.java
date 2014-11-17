package com.hifun.soul.gameserver.antiindulge.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 请求更新收益倍率
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateRevenueRateRequest extends GCMessage{
	

	public GCUpdateRevenueRateRequest (){
	}
	

	@Override
	protected boolean readImpl() {
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_REVENUE_RATE_REQUEST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_REVENUE_RATE_REQUEST";
	}
}