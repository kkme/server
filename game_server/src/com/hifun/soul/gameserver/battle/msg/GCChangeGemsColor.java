package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务端通知客户端转换宝石颜色
 *
 * @author SevenSoul
 */
@Component
public class GCChangeGemsColor extends GCMessage{
	
	/** 改变后的宝石集合 */
	private com.hifun.soul.gameserver.battle.gem.GemPosition[] changedGems;

	public GCChangeGemsColor (){
	}
	
	public GCChangeGemsColor (
			com.hifun.soul.gameserver.battle.gem.GemPosition[] changedGems ){
			this.changedGems = changedGems;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		changedGems = new com.hifun.soul.gameserver.battle.gem.GemPosition[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.battle.gem.GemPosition objchangedGems = new com.hifun.soul.gameserver.battle.gem.GemPosition();
			changedGems[i] = objchangedGems;
					objchangedGems.setRow(readInteger());
							objchangedGems.setCol(readInteger());
							objchangedGems.setType(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(changedGems.length);
	for(int i=0; i<changedGems.length; i++){
	com.hifun.soul.gameserver.battle.gem.GemPosition objchangedGems = changedGems[i];
				writeInteger(objchangedGems.getRow());
				writeInteger(objchangedGems.getCol());
				writeInteger(objchangedGems.getType());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CHANGE_GEMS_COLOR;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CHANGE_GEMS_COLOR";
	}

	public com.hifun.soul.gameserver.battle.gem.GemPosition[] getChangedGems(){
		return changedGems;
	}

	public void setChangedGems(com.hifun.soul.gameserver.battle.gem.GemPosition[] changedGems){
		this.changedGems = changedGems;
	}	
}