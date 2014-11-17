package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应创建军团
 *
 * @author SevenSoul
 */
@Component
public class GCCreateLegion extends GCMessage{
	
	/** 军团名称 */
	private String legionName;
	/** 团长名称 */
	private String commanderName;
	/** 军团等级 */
	private int legionLevel;
	/** 军团人数限制 */
	private int memberLimit;
	/** 军团当前人数 */
	private int memberNum;
	/** 经验值 */
	private int experience;

	public GCCreateLegion (){
	}
	
	public GCCreateLegion (
			String legionName,
			String commanderName,
			int legionLevel,
			int memberLimit,
			int memberNum,
			int experience ){
			this.legionName = legionName;
			this.commanderName = commanderName;
			this.legionLevel = legionLevel;
			this.memberLimit = memberLimit;
			this.memberNum = memberNum;
			this.experience = experience;
	}

	@Override
	protected boolean readImpl() {
		legionName = readString();
		commanderName = readString();
		legionLevel = readInteger();
		memberLimit = readInteger();
		memberNum = readInteger();
		experience = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeString(legionName);
		writeString(commanderName);
		writeInteger(legionLevel);
		writeInteger(memberLimit);
		writeInteger(memberNum);
		writeInteger(experience);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CREATE_LEGION;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CREATE_LEGION";
	}

	public String getLegionName(){
		return legionName;
	}
		
	public void setLegionName(String legionName){
		this.legionName = legionName;
	}

	public String getCommanderName(){
		return commanderName;
	}
		
	public void setCommanderName(String commanderName){
		this.commanderName = commanderName;
	}

	public int getLegionLevel(){
		return legionLevel;
	}
		
	public void setLegionLevel(int legionLevel){
		this.legionLevel = legionLevel;
	}

	public int getMemberLimit(){
		return memberLimit;
	}
		
	public void setMemberLimit(int memberLimit){
		this.memberLimit = memberLimit;
	}

	public int getMemberNum(){
		return memberNum;
	}
		
	public void setMemberNum(int memberNum){
		this.memberNum = memberNum;
	}

	public int getExperience(){
		return experience;
	}
		
	public void setExperience(int experience){
		this.experience = experience;
	}
}