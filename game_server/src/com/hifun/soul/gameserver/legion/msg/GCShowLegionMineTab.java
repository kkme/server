package com.hifun.soul.gameserver.legion.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应展示军团矿战标签
 *
 * @author SevenSoul
 */
@Component
public class GCShowLegionMineTab extends GCMessage{
	
	/** 矿战状态 */
	private int warState;
	/** boss战开启时间 */
	private String bossWarTime;
	/** 矿战开启时间 */
	private String mineWarTime;
	/** 第一军团名称 */
	private String firstLegionName;
	/** 第二军团名称 */
	private String secondLegionName;
	/** 是否有矿战资格 */
	private boolean hasMineWarRight;

	public GCShowLegionMineTab (){
	}
	
	public GCShowLegionMineTab (
			int warState,
			String bossWarTime,
			String mineWarTime,
			String firstLegionName,
			String secondLegionName,
			boolean hasMineWarRight ){
			this.warState = warState;
			this.bossWarTime = bossWarTime;
			this.mineWarTime = mineWarTime;
			this.firstLegionName = firstLegionName;
			this.secondLegionName = secondLegionName;
			this.hasMineWarRight = hasMineWarRight;
	}

	@Override
	protected boolean readImpl() {
		warState = readInteger();
		bossWarTime = readString();
		mineWarTime = readString();
		firstLegionName = readString();
		secondLegionName = readString();
		hasMineWarRight = readBoolean();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(warState);
		writeString(bossWarTime);
		writeString(mineWarTime);
		writeString(firstLegionName);
		writeString(secondLegionName);
		writeBoolean(hasMineWarRight);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_SHOW_LEGION_MINE_TAB;
	}
	
	@Override
	public String getTypeName() {
		return "GC_SHOW_LEGION_MINE_TAB";
	}

	public int getWarState(){
		return warState;
	}
		
	public void setWarState(int warState){
		this.warState = warState;
	}

	public String getBossWarTime(){
		return bossWarTime;
	}
		
	public void setBossWarTime(String bossWarTime){
		this.bossWarTime = bossWarTime;
	}

	public String getMineWarTime(){
		return mineWarTime;
	}
		
	public void setMineWarTime(String mineWarTime){
		this.mineWarTime = mineWarTime;
	}

	public String getFirstLegionName(){
		return firstLegionName;
	}
		
	public void setFirstLegionName(String firstLegionName){
		this.firstLegionName = firstLegionName;
	}

	public String getSecondLegionName(){
		return secondLegionName;
	}
		
	public void setSecondLegionName(String secondLegionName){
		this.secondLegionName = secondLegionName;
	}

	public boolean getHasMineWarRight(){
		return hasMineWarRight;
	}
		
	public void setHasMineWarRight(boolean hasMineWarRight){
		this.hasMineWarRight = hasMineWarRight;
	}
}