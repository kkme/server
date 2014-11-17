package com.hifun.soul.gameserver.battle.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 服务器通知客户端棋盘信息
 *
 * @author SevenSoul
 */
@Component
public class GCChessBoardInfo extends GCMessage{
	
	/** 棋盘信息, 按照列发送 */
	private com.hifun.soul.gameserver.battle.gem.ColNewGems[] chessBoardCols;

	public GCChessBoardInfo (){
	}
	
	public GCChessBoardInfo (
			com.hifun.soul.gameserver.battle.gem.ColNewGems[] chessBoardCols ){
			this.chessBoardCols = chessBoardCols;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		count = readShort();
		count = count < 0 ? 0 : count;
		chessBoardCols = new com.hifun.soul.gameserver.battle.gem.ColNewGems[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.battle.gem.ColNewGems objchessBoardCols = new com.hifun.soul.gameserver.battle.gem.ColNewGems();
			chessBoardCols[i] = objchessBoardCols;
					objchessBoardCols.setCol(readInteger());
								{
	int subCountgems = readShort();
		int[] subListgems = new int[subCountgems];
		objchessBoardCols.setGems(subListgems);
	for(int jgems = 0; jgems < subCountgems; jgems++){
						subListgems[jgems] = readInteger();
			}
	}
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
	writeShort(chessBoardCols.length);
	for(int i=0; i<chessBoardCols.length; i++){
	com.hifun.soul.gameserver.battle.gem.ColNewGems objchessBoardCols = chessBoardCols[i];
				writeInteger(objchessBoardCols.getCol());
					int[] gems_objchessBoardCols=objchessBoardCols.getGems();
	writeShort(gems_objchessBoardCols.length);
	for(int jgems=0; jgems<gems_objchessBoardCols.length; jgems++){
					writeInteger(gems_objchessBoardCols[jgems]);
			}
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_CHESS_BOARD_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_CHESS_BOARD_INFO";
	}

	public com.hifun.soul.gameserver.battle.gem.ColNewGems[] getChessBoardCols(){
		return chessBoardCols;
	}

	public void setChessBoardCols(com.hifun.soul.gameserver.battle.gem.ColNewGems[] chessBoardCols){
		this.chessBoardCols = chessBoardCols;
	}	
}