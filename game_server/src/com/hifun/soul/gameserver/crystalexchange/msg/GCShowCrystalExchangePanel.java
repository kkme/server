package com.hifun.soul.gameserver.crystalexchange.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 打开魔晶兑换面板
 *
 * @author SevenSoul
 */
@Component
public class GCShowCrystalExchangePanel extends GCMessage{
	
	/** 当天总共次数 */
	private int totalTimes;
	/** 当天使用次数 */
	private int useTimes;
	/** 消耗魔晶 */
	private int crystal;
	/** 获得的金币 */
	private int coin;

	public GCShowCrystalExchangePanel (){
	}
	
	public GCShowCrystalExchangePanel (
			int totalTimes,
			int useTimes,
			int crystal,
			int coin ){
			this.totalTimes = totalTimes;
			this.useTimes = useTimes;
			this.crystal = crystal;
			this.coin = coin;
	}

	@Override
	protected boolean readImpl() {
		totalTimes = readInteger();
		useTimes = readInteger();
		crystal = readInteger();
		coin = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(totalTimes);
		writeInteger(useTimes);
		writeInteger(crystal);
		writeInteger(coin);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_CRYSTAL_EXCHANGE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_CRYSTAL_EXCHANGE_PANEL";
	}

	public int getTotalTimes(){
		return totalTimes;
	}
		
	public void setTotalTimes(int totalTimes){
		this.totalTimes = totalTimes;
	}

	public int getUseTimes(){
		return useTimes;
	}
		
	public void setUseTimes(int useTimes){
		this.useTimes = useTimes;
	}

	public int getCrystal(){
		return crystal;
	}
		
	public void setCrystal(int crystal){
		this.crystal = crystal;
	}

	public int getCoin(){
		return coin;
	}
		
	public void setCoin(int coin){
		this.coin = coin;
	}
}