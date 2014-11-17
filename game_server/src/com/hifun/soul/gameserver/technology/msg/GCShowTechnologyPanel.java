package com.hifun.soul.gameserver.technology.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 打开科技面板
 *
 * @author SevenSoul
 */
@Component
public class GCShowTechnologyPanel extends GCMessage{
	
	/** 科技总数 */
	private short totalSize;
	/** 页面索引 */
	private short pageIndex;
	/** 剩余科技点数 */
	private int technologyNum;
	/** 科技列表 */
	private com.hifun.soul.gameserver.technology.msg.TechnologyInfo[] technologyInfos;
	/** 默认科技详细信息 */
	private com.hifun.soul.gameserver.technology.msg.TechnologyDetailInfo technologyDetailInfo;

	public GCShowTechnologyPanel (){
	}
	
	public GCShowTechnologyPanel (
			short totalSize,
			short pageIndex,
			int technologyNum,
			com.hifun.soul.gameserver.technology.msg.TechnologyInfo[] technologyInfos,
			com.hifun.soul.gameserver.technology.msg.TechnologyDetailInfo technologyDetailInfo ){
			this.totalSize = totalSize;
			this.pageIndex = pageIndex;
			this.technologyNum = technologyNum;
			this.technologyInfos = technologyInfos;
			this.technologyDetailInfo = technologyDetailInfo;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		totalSize = readShort();
		pageIndex = readShort();
		technologyNum = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		technologyInfos = new com.hifun.soul.gameserver.technology.msg.TechnologyInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.technology.msg.TechnologyInfo objtechnologyInfos = new com.hifun.soul.gameserver.technology.msg.TechnologyInfo();
			technologyInfos[i] = objtechnologyInfos;
					objtechnologyInfos.setTechnologyId(readInteger());
							objtechnologyInfos.setName(readString());
							objtechnologyInfos.setLevel(readInteger());
							objtechnologyInfos.setIcon(readInteger());
							objtechnologyInfos.setMaxLevelReached(readBoolean());
							objtechnologyInfos.setCostTechPointNum(readInteger());
							objtechnologyInfos.setRoleLevel(readInteger());
				}
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
		writeShort(totalSize);
		writeShort(pageIndex);
		writeInteger(technologyNum);
	writeShort(technologyInfos.length);
	for(int i=0; i<technologyInfos.length; i++){
	com.hifun.soul.gameserver.technology.msg.TechnologyInfo objtechnologyInfos = technologyInfos[i];
				writeInteger(objtechnologyInfos.getTechnologyId());
				writeString(objtechnologyInfos.getName());
				writeInteger(objtechnologyInfos.getLevel());
				writeInteger(objtechnologyInfos.getIcon());
				writeBoolean(objtechnologyInfos.getMaxLevelReached());
				writeInteger(objtechnologyInfos.getCostTechPointNum());
				writeInteger(objtechnologyInfos.getRoleLevel());
	}
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
		return MessageType.GC_SHOW_TECHNOLOGY_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_TECHNOLOGY_PANEL";
	}

	public short getTotalSize(){
		return totalSize;
	}
		
	public void setTotalSize(short totalSize){
		this.totalSize = totalSize;
	}

	public short getPageIndex(){
		return pageIndex;
	}
		
	public void setPageIndex(short pageIndex){
		this.pageIndex = pageIndex;
	}

	public int getTechnologyNum(){
		return technologyNum;
	}
		
	public void setTechnologyNum(int technologyNum){
		this.technologyNum = technologyNum;
	}

	public com.hifun.soul.gameserver.technology.msg.TechnologyInfo[] getTechnologyInfos(){
		return technologyInfos;
	}

	public void setTechnologyInfos(com.hifun.soul.gameserver.technology.msg.TechnologyInfo[] technologyInfos){
		this.technologyInfos = technologyInfos;
	}	

	public com.hifun.soul.gameserver.technology.msg.TechnologyDetailInfo getTechnologyDetailInfo(){
		return technologyDetailInfo;
	}
		
	public void setTechnologyDetailInfo(com.hifun.soul.gameserver.technology.msg.TechnologyDetailInfo technologyDetailInfo){
		this.technologyDetailInfo = technologyDetailInfo;
	}
}