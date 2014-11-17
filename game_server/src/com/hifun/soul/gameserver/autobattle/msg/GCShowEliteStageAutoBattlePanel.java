package com.hifun.soul.gameserver.autobattle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 打开精英副本扫荡面板
 *
 * @author SevenSoul
 */
@Component
public class GCShowEliteStageAutoBattlePanel extends GCMessage{
	
	/** 可扫荡的怪物列表 */
	private String[] monsters;
	/** 需要的体力 */
	private int energys;
	/** 需要的时间 */
	private int time;
	/** 评星减少的时间 */
	private int starTimeReduce;
	/** vip减少的时间 */
	private int vipTimeReduce;

	public GCShowEliteStageAutoBattlePanel (){
	}
	
	public GCShowEliteStageAutoBattlePanel (
			String[] monsters,
			int energys,
			int time,
			int starTimeReduce,
			int vipTimeReduce ){
			this.monsters = monsters;
			this.energys = energys;
			this.time = time;
			this.starTimeReduce = starTimeReduce;
			this.vipTimeReduce = vipTimeReduce;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		monsters = new String[count];
		for(int i=0; i<count; i++){
			monsters[i] = readString();
		}
		energys = readInteger();
		time = readInteger();
		starTimeReduce = readInteger();
		vipTimeReduce = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(monsters.length);
	for(int i=0; i<monsters.length; i++){
	String objmonsters = monsters[i];
			writeString(objmonsters);
}
		writeInteger(energys);
		writeInteger(time);
		writeInteger(starTimeReduce);
		writeInteger(vipTimeReduce);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_ELITE_STAGE_AUTO_BATTLE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_ELITE_STAGE_AUTO_BATTLE_PANEL";
	}

	public String[] getMonsters(){
		return monsters;
	}

	public void setMonsters(String[] monsters){
		this.monsters = monsters;
	}	

	public int getEnergys(){
		return energys;
	}
		
	public void setEnergys(int energys){
		this.energys = energys;
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