package com.hifun.soul.gameserver.legionmine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应收获
 *
 * @author SevenSoul
 */
@Component
public class GCLegionMineWarHarvest extends GCMessage{
	
	/** 占领值 */
	private int occupyValue;
	/** 排名 */
	private int rank;
	/** 矿战军团信息 */
	private com.hifun.soul.gameserver.legionmine.msg.info.JoinLegionInfo[] joinLegionInfos;
	/** 收获者ID */
	private long havestHumanGuid;

	public GCLegionMineWarHarvest (){
	}
	
	public GCLegionMineWarHarvest (
			int occupyValue,
			int rank,
			com.hifun.soul.gameserver.legionmine.msg.info.JoinLegionInfo[] joinLegionInfos,
			long havestHumanGuid ){
			this.occupyValue = occupyValue;
			this.rank = rank;
			this.joinLegionInfos = joinLegionInfos;
			this.havestHumanGuid = havestHumanGuid;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		occupyValue = readInteger();
		rank = readInteger();
		count = readShort();
		count = count < 0 ? 0 : count;
		joinLegionInfos = new com.hifun.soul.gameserver.legionmine.msg.info.JoinLegionInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.legionmine.msg.info.JoinLegionInfo objjoinLegionInfos = new com.hifun.soul.gameserver.legionmine.msg.info.JoinLegionInfo();
			joinLegionInfos[i] = objjoinLegionInfos;
					objjoinLegionInfos.setLegionId(readLong());
							objjoinLegionInfos.setLegionName(readString());
							objjoinLegionInfos.setOccupyValue(readInteger());
							objjoinLegionInfos.setJoinLegionType(readInteger());
				}
		havestHumanGuid = readLong();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(occupyValue);
		writeInteger(rank);
	writeShort(joinLegionInfos.length);
	for(int i=0; i<joinLegionInfos.length; i++){
	com.hifun.soul.gameserver.legionmine.msg.info.JoinLegionInfo objjoinLegionInfos = joinLegionInfos[i];
				writeLong(objjoinLegionInfos.getLegionId());
				writeString(objjoinLegionInfos.getLegionName());
				writeInteger(objjoinLegionInfos.getOccupyValue());
				writeInteger(objjoinLegionInfos.getJoinLegionType());
	}
		writeLong(havestHumanGuid);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_LEGION_MINE_WAR_HARVEST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_LEGION_MINE_WAR_HARVEST";
	}

	public int getOccupyValue(){
		return occupyValue;
	}
		
	public void setOccupyValue(int occupyValue){
		this.occupyValue = occupyValue;
	}

	public int getRank(){
		return rank;
	}
		
	public void setRank(int rank){
		this.rank = rank;
	}

	public com.hifun.soul.gameserver.legionmine.msg.info.JoinLegionInfo[] getJoinLegionInfos(){
		return joinLegionInfos;
	}

	public void setJoinLegionInfos(com.hifun.soul.gameserver.legionmine.msg.info.JoinLegionInfo[] joinLegionInfos){
		this.joinLegionInfos = joinLegionInfos;
	}	

	public long getHavestHumanGuid(){
		return havestHumanGuid;
	}
		
	public void setHavestHumanGuid(long havestHumanGuid){
		this.havestHumanGuid = havestHumanGuid;
	}
}