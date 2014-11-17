package com.hifun.soul.gameserver.bloodtemple.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求展示嗜血神殿房间
 * 
 * @author SevenSoul
 */
@Component
public class CGShowBloodTempleRoom extends CGMessage{
	
	/** 房间页码 */
	private int pageIndex;
	
	public CGShowBloodTempleRoom (){
	}
	
	public CGShowBloodTempleRoom (
			int pageIndex ){
			this.pageIndex = pageIndex;
	}
	
	@Override
	protected boolean readImpl() {
		pageIndex = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(pageIndex);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_SHOW_BLOOD_TEMPLE_ROOM;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_BLOOD_TEMPLE_ROOM";
	}

	public int getPageIndex(){
		return pageIndex;
	}
		
	public void setPageIndex(int pageIndex){
		this.pageIndex = pageIndex;
	}

	@Override
	public void execute() {
	}
}