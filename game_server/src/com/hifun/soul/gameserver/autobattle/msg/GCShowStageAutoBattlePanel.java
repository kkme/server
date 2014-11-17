package com.hifun.soul.gameserver.autobattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 返回关卡单轮扫荡需要的时间和体力
 *
 * @author SevenSoul
 */
@Component
public class GCShowStageAutoBattlePanel extends GCMessage{
	
	/** 需要的体力 */
	private int energy;
	/** 需要的时间 */
	private int time;
	/** 评星减少的时间 */
	private int starTimeReduce;
	/** vip减少的时间 */
	private int vipTimeReduce;

	public GCShowStageAutoBattlePanel (){
	}
	
	public GCShowStageAutoBattlePanel (
			int energy,
			int time,
			int starTimeReduce,
			int vipTimeReduce ){
			this.energy = energy;
			this.time = time;
			this.starTimeReduce = starTimeReduce;
			this.vipTimeReduce = vipTimeReduce;
	}

	@Override
	protected boolean readImpl() {
		energy = readInteger();
		time = readInteger();
		starTimeReduce = readInteger();
		vipTimeReduce = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(energy);
		writeInteger(time);
		writeInteger(starTimeReduce);
		writeInteger(vipTimeReduce);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_STAGE_AUTO_BATTLE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_STAGE_AUTO_BATTLE_PANEL";
	}

	public int getEnergy(){
		return energy;
	}
		
	public void setEnergy(int energy){
		this.energy = energy;
	}

	public int getTime(){
		return time;
	}
		
	public void setTime(int time){
		this.time = time;
	}

	public int getStarTimeReduce(){
		return starTimeReduce;
	}
		
	public void setStarTimeReduce(int starTimeReduce){
		this.starTimeReduce = starTimeReduce;
	}

	public int getVipTimeReduce(){
		return vipTimeReduce;
	}
		
	public void setVipTimeReduce(int vipTimeReduce){
		this.vipTimeReduce = vipTimeReduce;
	}
}