package com.hifun.soul.gameserver.faction.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应打开选择阵营面板
 *
 * @author SevenSoul
 */
@Component
public class GCOpenSelectFactionPanel extends GCMessage{
	
	/** 光明阵营信息 */
	private com.hifun.soul.gameserver.faction.model.FactionSimpleInfo brightInfo;
	/** 黑暗阵营信息 */
	private com.hifun.soul.gameserver.faction.model.FactionSimpleInfo darkInfo;
	/** 随机加入阵营金币奖励数目 */
	private int randomRewardCoin;

	public GCOpenSelectFactionPanel (){
	}
	
	public GCOpenSelectFactionPanel (
			com.hifun.soul.gameserver.faction.model.FactionSimpleInfo brightInfo,
			com.hifun.soul.gameserver.faction.model.FactionSimpleInfo darkInfo,
			int randomRewardCoin ){
			this.brightInfo = brightInfo;
			this.darkInfo = darkInfo;
			this.randomRewardCoin = randomRewardCoin;
	}

	@Override
	protected boolean readImpl() {
		brightInfo = new com.hifun.soul.gameserver.faction.model.FactionSimpleInfo();
						brightInfo.setFactionType(readInteger());
						brightInfo.setFactionDesc(readString());
						brightInfo.setFactionIconId(readInteger());
				darkInfo = new com.hifun.soul.gameserver.faction.model.FactionSimpleInfo();
						darkInfo.setFactionType(readInteger());
						darkInfo.setFactionDesc(readString());
						darkInfo.setFactionIconId(readInteger());
				randomRewardCoin = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(brightInfo.getFactionType());
		writeString(brightInfo.getFactionDesc());
		writeInteger(brightInfo.getFactionIconId());
		writeInteger(darkInfo.getFactionType());
		writeString(darkInfo.getFactionDesc());
		writeInteger(darkInfo.getFactionIconId());
		writeInteger(randomRewardCoin);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_SELECT_FACTION_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_SELECT_FACTION_PANEL";
	}

	public com.hifun.soul.gameserver.faction.model.FactionSimpleInfo getBrightInfo(){
		return brightInfo;
	}
		
	public void setBrightInfo(com.hifun.soul.gameserver.faction.model.FactionSimpleInfo brightInfo){
		this.brightInfo = brightInfo;
	}

	public com.hifun.soul.gameserver.faction.model.FactionSimpleInfo getDarkInfo(){
		return darkInfo;
	}
		
	public void setDarkInfo(com.hifun.soul.gameserver.faction.model.FactionSimpleInfo darkInfo){
		this.darkInfo = darkInfo;
	}

	public int getRandomRewardCoin(){
		return randomRewardCoin;
	}
		
	public void setRandomRewardCoin(int randomRewardCoin){
		this.randomRewardCoin = randomRewardCoin;
	}
}