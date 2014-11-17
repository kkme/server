package com.hifun.soul.gameserver.technology.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 返回科技升级之后的变更信息
 *
 * @author SevenSoul
 */
@Component
public class GCUpgradeTechnology extends GCMessage{
	
	/** 科技id */
	private int technologyId;
	/** 剩余科技点数 */
	private int technologyNum;
	/** 科技详细信息 */
	private com.hifun.soul.gameserver.technology.msg.TechnologyDetailInfo technologyDetailInfo;

	public GCUpgradeTechnology (){
	}
	
	public GCUpgradeTechnology (
			int technologyId,
			int technologyNum,
			com.hifun.soul.gameserver.technology.msg.TechnologyDetailInfo technologyDetailInfo ){
			this.technologyId = technologyId;
			this.technologyNum = technologyNum;
			this.technologyDetailInfo = technologyDetailInfo;
	}

	@Override
	protected boolean readImpl() {
		technologyId = readInteger();
		technologyNum = readInteger();
		technologyDetailInfo = new com.hifun.soul.gameserver.technology.msg.TechnologyDetailInfo();
						technologyDetailInfo.setTechnologyId(readInteger());
						technologyDetailInfo.setName(readString());
						technologyDetailInfo.setLevel(readInteger());
						technologyDetailInfo.setIcon(readInteger());
						technologyDetailInfo.setPropAddValue(readInteger());
						technologyDetailInfo.setPropName(readString());
						technologyDetailInfo.setCostValue(readInteger());
						technologyDetailInfo.setRoleLevel(readInteger());
						technologyDetailInfo.setNextPropAddValue(readInteger());
				return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(technologyId);
		writeInteger(technologyNum);
		writeInteger(technologyDetailInfo.getTechnologyId());
		writeString(technologyDetailInfo.getName());
		writeInteger(technologyDetailInfo.getLevel());
		writeInteger(technologyDetailInfo.getIcon());
		writeInteger(technologyDetailInfo.getPropAddValue());
		writeString(technologyDetailInfo.getPropName());
		writeInteger(technologyDetailInfo.getCostValue());
		writeInteger(technologyDetailInfo.getRoleLevel());
		writeInteger(technologyDetailInfo.getNextPropAddValue());
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPGRADE_TECHNOLOGY;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPGRADE_TECHNOLOGY";
	}

	public int getTechnologyId(){
		return technologyId;
	}
		
	public void setTechnologyId(int technologyId){
		this.technologyId = technologyId;
	}

	public int getTechnologyNum(){
		return technologyNum;
	}
		
	public void setTechnologyNum(int technologyNum){
		this.technologyNum = technologyNum;
	}

	public com.hifun.soul.gameserver.technology.msg.TechnologyDetailInfo getTechnologyDetailInfo(){
		return technologyDetailInfo;
	}
		
	public void setTechnologyDetailInfo(com.hifun.soul.gameserver.technology.msg.TechnologyDetailInfo technologyDetailInfo){
		this.technologyDetailInfo = technologyDetailInfo;
	}
}