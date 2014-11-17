package com.hifun.soul.gameserver.human.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 功能开放的信息
 *
 * @author SevenSoul
 */
@Component
public class GCFunctionInfo extends GCMessage{
	
	/** 功能开放信息列表 */
	private FunctionInfo[] functionInfos;

	public GCFunctionInfo (){
	}
	
	public GCFunctionInfo (
			FunctionInfo[] functionInfos ){
			this.functionInfos = functionInfos;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		functionInfos = new FunctionInfo[count];
		for(int i=0; i<count; i++){
			FunctionInfo objfunctionInfos = new FunctionInfo();
			functionInfos[i] = objfunctionInfos;
					objfunctionInfos.setId(readInteger());
							objfunctionInfos.setOpenLevel(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(functionInfos.length);
	for(int i=0; i<functionInfos.length; i++){
	FunctionInfo objfunctionInfos = functionInfos[i];
				writeInteger(objfunctionInfos.getId());
				writeInteger(objfunctionInfos.getOpenLevel());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_FUNCTION_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_FUNCTION_INFO";
	}

	public FunctionInfo[] getFunctionInfos(){
		return functionInfos;
	}

	public void setFunctionInfos(FunctionInfo[] functionInfos){
		this.functionInfos = functionInfos;
	}	

	public static class FunctionInfo{
		/** 功能ID */
		private int id;
		/** 开启等级 */
		private int openLevel;

		public FunctionInfo (){}

		public FunctionInfo (
			int id,
			int openLevel	 ){
			this.id = id;
			this.openLevel = openLevel;
		}
 
		public int getId(){
			return id;
		}
			
		public void setId(int id){
			this.id = id;
		}
 
		public int getOpenLevel(){
			return openLevel;
		}
			
		public void setOpenLevel(int openLevel){
			this.openLevel = openLevel;
		}
	}
}