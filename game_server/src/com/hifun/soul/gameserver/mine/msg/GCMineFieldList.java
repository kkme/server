package com.hifun.soul.gameserver.mine.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 发送矿坑列表信息
 *
 * @author SevenSoul
 */
@Component
public class GCMineFieldList extends GCMessage{
	
	/** 矿坑列表 */
	private com.hifun.soul.gameserver.mine.MineFieldInfo[] mineFields;

	public GCMineFieldList (){
	}
	
	public GCMineFieldList (
			com.hifun.soul.gameserver.mine.MineFieldInfo[] mineFields ){
			this.mineFields = mineFields;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		mineFields = new com.hifun.soul.gameserver.mine.MineFieldInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.mine.MineFieldInfo objmineFields = new com.hifun.soul.gameserver.mine.MineFieldInfo();
			mineFields[i] = objmineFields;
					objmineFields.setIndex(readInteger());
							objmineFields.setType(readInteger());
							objmineFields.setName(readString());
							objmineFields.setDesc(readString());
							objmineFields.setPicId(readInteger());
							objmineFields.setIsBadMineField(readBoolean());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(mineFields.length);
	for(int i=0; i<mineFields.length; i++){
	com.hifun.soul.gameserver.mine.MineFieldInfo objmineFields = mineFields[i];
				writeInteger(objmineFields.getIndex());
				writeInteger(objmineFields.getType());
				writeString(objmineFields.getName());
				writeString(objmineFields.getDesc());
				writeInteger(objmineFields.getPicId());
				writeBoolean(objmineFields.getIsBadMineField());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_MINE_FIELD_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "GC_MINE_FIELD_LIST";
	}

	public com.hifun.soul.gameserver.mine.MineFieldInfo[] getMineFields(){
		return mineFields;
	}

	public void setMineFields(com.hifun.soul.gameserver.mine.MineFieldInfo[] mineFields){
		this.mineFields = mineFields;
	}	
}