package com.hifun.soul.gameserver.legionmine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 通知客户端更新矿堆列表 
 *
 * @author SevenSoul
 */
@Component
public class GCUpdateLegionMineList extends GCMessage{
	
	/** 军团矿堆信息 */
	private com.hifun.soul.gameserver.legionmine.msg.info.LegionMineInfo[] mineInfos;

	public GCUpdateLegionMineList (){
	}
	
	public GCUpdateLegionMineList (
			com.hifun.soul.gameserver.legionmine.msg.info.LegionMineInfo[] mineInfos ){
			this.mineInfos = mineInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		mineInfos = new com.hifun.soul.gameserver.legionmine.msg.info.LegionMineInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.legionmine.msg.info.LegionMineInfo objmineInfos = new com.hifun.soul.gameserver.legionmine.msg.info.LegionMineInfo();
			mineInfos[i] = objmineInfos;
					objmineInfos.setMineIndex(readInteger());
							objmineInfos.setCanBattle(readBoolean());
							objmineInfos.setCanMove(readBoolean());
							objmineInfos.setCanDisturb(readBoolean());
							objmineInfos.setJoinLegionType(readInteger());
							objmineInfos.setSurroundState(readInteger());
							objmineInfos.setOccupyNum(readInteger());
							objmineInfos.setOccupyNumVisible(readBoolean());
							objmineInfos.setIsDouble(readBoolean());
							objmineInfos.setIsRedMine(readBoolean());
							objmineInfos.setIsSelf(readBoolean());
							objmineInfos.setMineType(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(mineInfos.length);
	for(int i=0; i<mineInfos.length; i++){
	com.hifun.soul.gameserver.legionmine.msg.info.LegionMineInfo objmineInfos = mineInfos[i];
				writeInteger(objmineInfos.getMineIndex());
				writeBoolean(objmineInfos.getCanBattle());
				writeBoolean(objmineInfos.getCanMove());
				writeBoolean(objmineInfos.getCanDisturb());
				writeInteger(objmineInfos.getJoinLegionType());
				writeInteger(objmineInfos.getSurroundState());
				writeInteger(objmineInfos.getOccupyNum());
				writeBoolean(objmineInfos.getOccupyNumVisible());
				writeBoolean(objmineInfos.getIsDouble());
				writeBoolean(objmineInfos.getIsRedMine());
				writeBoolean(objmineInfos.getIsSelf());
				writeInteger(objmineInfos.getMineType());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_UPDATE_LEGION_MINE_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_UPDATE_LEGION_MINE_LIST";
	}

	public com.hifun.soul.gameserver.legionmine.msg.info.LegionMineInfo[] getMineInfos(){
		return mineInfos;
	}

	public void setMineInfos(com.hifun.soul.gameserver.legionmine.msg.info.LegionMineInfo[] mineInfos){
		this.mineInfos = mineInfos;
	}	
}