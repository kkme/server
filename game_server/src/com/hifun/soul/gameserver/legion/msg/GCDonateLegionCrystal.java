package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应捐献军团魔晶
 *
 * @author SevenSoul
 */
@Component
public class GCDonateLegionCrystal extends GCMessage{
	
	/** 军团经验 */
	private int legionExperience;
	/** 个人贡献 */
	private int selfContribution;
	/** 军团勋章 */
	private int legionMedal;

	public GCDonateLegionCrystal (){
	}
	
	public GCDonateLegionCrystal (
			int legionExperience,
			int selfContribution,
			int legionMedal ){
			this.legionExperience = legionExperience;
			this.selfContribution = selfContribution;
			this.legionMedal = legionMedal;
	}

	@Override
	protected boolean readImpl() {
		legionExperience = readInteger();
		selfContribution = readInteger();
		legionMedal = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(legionExperience);
		writeInteger(selfContribution);
		writeInteger(legionMedal);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_DONATE_LEGION_CRYSTAL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_DONATE_LEGION_CRYSTAL";
	}

	public int getLegionExperience(){
		return legionExperience;
	}
		
	public void setLegionExperience(int legionExperience){
		this.legionExperience = legionExperience;
	}

	public int getSelfContribution(){
		return selfContribution;
	}
		
	public void setSelfContribution(int selfContribution){
		this.selfContribution = selfContribution;
	}

	public int getLegionMedal(){
		return legionMedal;
	}
		
	public void setLegionMedal(int legionMedal){
		this.legionMedal = legionMedal;
	}
}