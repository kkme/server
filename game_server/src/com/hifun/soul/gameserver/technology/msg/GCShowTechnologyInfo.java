package com.hifun.soul.gameserver.technology.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 返回科技信息
 *
 * @author SevenSoul
 */
@Component
public class GCShowTechnologyInfo extends GCMessage{
	
	/** 默认科技详细信息 */
	private com.hifun.soul.gameserver.technology.msg.TechnologyDetailInfo technologyDetailInfo;

	public GCShowTechnologyInfo (){
	}
	
	public GCShowTechnologyInfo (
			com.hifun.soul.gameserver.technology.msg.TechnologyDetailInfo technologyDetailInfo ){
			this.technologyDetailInfo = technologyDetailInfo;
	}

	@Override
	protected boolean readImpl() {
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
		return MessageType.GC_SHOW_TECHNOLOGY_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_TECHNOLOGY_INFO";
	}

	public com.hifun.soul.gameserver.technology.msg.TechnologyDetailInfo getTechnologyDetailInfo(){
		return technologyDetailInfo;
	}
		
	public void setTechnologyDetailInfo(com.hifun.soul.gameserver.technology.msg.TechnologyDetailInfo technologyDetailInfo){
		this.technologyDetailInfo = technologyDetailInfo;
	}
}