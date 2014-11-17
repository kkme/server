package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知客户端普通攻击(移动宝石)
 *
 * @author SevenSoul
 */
@Component
public class GCNormalAttack extends GCMessage{
	
	/** 初始位置行数 */
	private int row1;
	/** 初始位置列数 */
	private int col1;
	/** 目标位置行数 */
	private int row2;
	/** 目标位置列数 */
	private int col2;

	public GCNormalAttack (){
	}
	
	public GCNormalAttack (
			int row1,
			int col1,
			int row2,
			int col2 ){
			this.row1 = row1;
			this.col1 = col1;
			this.row2 = row2;
			this.col2 = col2;
	}

	@Override
	protected boolean readImpl() {
		row1 = readInteger();
		col1 = readInteger();
		row2 = readInteger();
		col2 = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(row1);
		writeInteger(col1);
		writeInteger(row2);
		writeInteger(col2);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_NORMAL_ATTACK;
	}
	
	@Override
	public String getTypeName() {
		return "GC_NORMAL_ATTACK";
	}

	public int getRow1(){
		return row1;
	}
		
	public void setRow1(int row1){
		this.row1 = row1;
	}

	public int getCol1(){
		return col1;
	}
		
	public void setCol1(int col1){
		this.col1 = col1;
	}

	public int getRow2(){
		return row2;
	}
		
	public void setRow2(int row2){
		this.row2 = row2;
	}

	public int getCol2(){
		return col2;
	}
		
	public void setCol2(int col2){
		this.col2 = col2;
	}
}