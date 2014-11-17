package com.hifun.soul.gameserver.mars.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应打开战神之巅排行榜面板
 *
 * @author SevenSoul
 */
@Component
public class GCOpenMarsRankPanel extends GCMessage{
	
	/** 光明阵营今日杀戮 */
	private int brightTodayKillValue;
	/** 黑暗阵营今日杀戮 */
	private int darkTodayKillValue;
	/** 光明阵营列表  */
	private com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo[] marsBrightPlayerInfos;
	/** 黑暗阵营列表  */
	private com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo[] marsDarkPlayerInfos;

	public GCOpenMarsRankPanel (){
	}
	
	public GCOpenMarsRankPanel (
			int brightTodayKillValue,
			int darkTodayKillValue,
			com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo[] marsBrightPlayerInfos,
			com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo[] marsDarkPlayerInfos ){
			this.brightTodayKillValue = brightTodayKillValue;
			this.darkTodayKillValue = darkTodayKillValue;
			this.marsBrightPlayerInfos = marsBrightPlayerInfos;
			this.marsDarkPlayerInfos = marsDarkPlayerInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		brightTodayKillValue = readInteger();
		darkTodayKillValue = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		marsBrightPlayerInfos = new com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo objmarsBrightPlayerInfos = new com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo();
			marsBrightPlayerInfos[i] = objmarsBrightPlayerInfos;
					objmarsBrightPlayerInfos.setPlayerId(readLong());
							objmarsBrightPlayerInfos.setPlayerName(readString());
							objmarsBrightPlayerInfos.setPlayerLevel(readInteger());
							objmarsBrightPlayerInfos.setOccupationType(readInteger());
							objmarsBrightPlayerInfos.setTodayKillValue(readInteger());
				}
		count = readShort();
		count = count < 0 ? 0 : count;
		marsDarkPlayerInfos = new com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo objmarsDarkPlayerInfos = new com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo();
			marsDarkPlayerInfos[i] = objmarsDarkPlayerInfos;
					objmarsDarkPlayerInfos.setPlayerId(readLong());
							objmarsDarkPlayerInfos.setPlayerName(readString());
							objmarsDarkPlayerInfos.setPlayerLevel(readInteger());
							objmarsDarkPlayerInfos.setOccupationType(readInteger());
							objmarsDarkPlayerInfos.setTodayKillValue(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(brightTodayKillValue);
		writeInteger(darkTodayKillValue);
	writeShort(marsBrightPlayerInfos.length);
	for(int i=0; i<marsBrightPlayerInfos.length; i++){
	com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo objmarsBrightPlayerInfos = marsBrightPlayerInfos[i];
				writeLong(objmarsBrightPlayerInfos.getPlayerId());
				writeString(objmarsBrightPlayerInfos.getPlayerName());
				writeInteger(objmarsBrightPlayerInfos.getPlayerLevel());
				writeInteger(objmarsBrightPlayerInfos.getOccupationType());
				writeInteger(objmarsBrightPlayerInfos.getTodayKillValue());
	}
	writeShort(marsDarkPlayerInfos.length);
	for(int i=0; i<marsDarkPlayerInfos.length; i++){
	com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo objmarsDarkPlayerInfos = marsDarkPlayerInfos[i];
				writeLong(objmarsDarkPlayerInfos.getPlayerId());
				writeString(objmarsDarkPlayerInfos.getPlayerName());
				writeInteger(objmarsDarkPlayerInfos.getPlayerLevel());
				writeInteger(objmarsDarkPlayerInfos.getOccupationType());
				writeInteger(objmarsDarkPlayerInfos.getTodayKillValue());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_MARS_RANK_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_MARS_RANK_PANEL";
	}

	public int getBrightTodayKillValue(){
		return brightTodayKillValue;
	}
		
	public void setBrightTodayKillValue(int brightTodayKillValue){
		this.brightTodayKillValue = brightTodayKillValue;
	}

	public int getDarkTodayKillValue(){
		return darkTodayKillValue;
	}
		
	public void setDarkTodayKillValue(int darkTodayKillValue){
		this.darkTodayKillValue = darkTodayKillValue;
	}

	public com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo[] getMarsBrightPlayerInfos(){
		return marsBrightPlayerInfos;
	}

	public void setMarsBrightPlayerInfos(com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo[] marsBrightPlayerInfos){
		this.marsBrightPlayerInfos = marsBrightPlayerInfos;
	}	

	public com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo[] getMarsDarkPlayerInfos(){
		return marsDarkPlayerInfos;
	}

	public void setMarsDarkPlayerInfos(com.hifun.soul.gameserver.mars.msg.info.MarsPlayerInfo[] marsDarkPlayerInfos){
		this.marsDarkPlayerInfos = marsDarkPlayerInfos;
	}	
}