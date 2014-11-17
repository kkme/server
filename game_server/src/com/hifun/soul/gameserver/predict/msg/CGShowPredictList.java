package com.hifun.soul.gameserver.predict.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 请求展示预见列表 
 * 
 * @author SevenSoul
 */
@Component
public class CGShowPredictList extends CGMessage{
	
	/** 页码 */
	private int pageIndex;
	
	public CGShowPredictList (){
	}
	
	public CGShowPredictList (
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
		return MessageType.CG_SHOW_PREDICT_LIST;
	}
	
	@Override
	public String getTypeName() {
		return "CG_SHOW_PREDICT_LIST";
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