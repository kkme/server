package com.hifun.soul.gameserver.battleguide.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 战斗四连消引导
 *
 * @author SevenSoul
 */
@Component
public class GCBattleGuideFourBombs extends GCMessage{
	
	/** 引导类型  */
	private short guideType;
	/** 引导步骤列表 */
	private com.hifun.soul.gameserver.guide.msg.GuideStepInfo[] guideStepList;

	public GCBattleGuideFourBombs (){
	}
	
	public GCBattleGuideFourBombs (
			short guideType,
			com.hifun.soul.gameserver.guide.msg.GuideStepInfo[] guideStepList ){
			this.guideType = guideType;
			this.guideStepList = guideStepList;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		guideType = readShort();
		count = readShort();
		count = count < 0 ? 0 : count;
		guideStepList = new com.hifun.soul.gameserver.guide.msg.GuideStepInfo[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.guide.msg.GuideStepInfo objguideStepList = new com.hifun.soul.gameserver.guide.msg.GuideStepInfo();
			guideStepList[i] = objguideStepList;
					objguideStepList.setMaskColorInt(readInteger());
							objguideStepList.setMaskAlpha(readShort());
							objguideStepList.setGuideText(readString());
							objguideStepList.setTextPosX(readShort());
							objguideStepList.setTextPosY(readShort());
							objguideStepList.setTextMode(readShort());
							objguideStepList.setHighlite1PosX(readShort());
							objguideStepList.setHighlite1PosY(readShort());
							objguideStepList.setHighlite1Width(readShort());
							objguideStepList.setHighlite1Height(readShort());
							objguideStepList.setHighlite1BorderColor(readInteger());
							objguideStepList.setHighlite1BorderThickness(readShort());
							objguideStepList.setHighlite2PosX(readShort());
							objguideStepList.setHighlite2PosY(readShort());
							objguideStepList.setHighlite2Width(readShort());
							objguideStepList.setHighlite2Height(readShort());
							objguideStepList.setHighlite2BorderColor(readInteger());
							objguideStepList.setHighlite2BorderThickness(readShort());
							objguideStepList.setHighlite3PosX(readShort());
							objguideStepList.setHighlite3PosY(readShort());
							objguideStepList.setHighlite3Width(readShort());
							objguideStepList.setHighlite3Height(readShort());
							objguideStepList.setHighlite3BorderColor(readInteger());
							objguideStepList.setHighlite3BorderThickness(readShort());
							objguideStepList.setHighlite4PosX(readShort());
							objguideStepList.setHighlite4PosY(readShort());
							objguideStepList.setHighlite4Width(readShort());
							objguideStepList.setHighlite4Height(readShort());
							objguideStepList.setHighlite4BorderColor(readInteger());
							objguideStepList.setHighlite4BorderThickness(readShort());
							objguideStepList.setHighlite5PosX(readShort());
							objguideStepList.setHighlite5PosY(readShort());
							objguideStepList.setHighlite5Width(readShort());
							objguideStepList.setHighlite5Height(readShort());
							objguideStepList.setHighlite5BorderColor(readInteger());
							objguideStepList.setHighlite5BorderThickness(readShort());
							objguideStepList.setArrow1PosX(readShort());
							objguideStepList.setArrow1PosY(readShort());
							objguideStepList.setArrow2PosX(readShort());
							objguideStepList.setArrow2PosY(readShort());
							objguideStepList.setArrow3PosX(readShort());
							objguideStepList.setArrow3PosY(readShort());
							objguideStepList.setOperationalPosX(readShort());
							objguideStepList.setOperationalPosY(readShort());
							objguideStepList.setOperationalWidth(readShort());
							objguideStepList.setOperationalHeight(readShort());
							objguideStepList.setOperationalBorderColor(readInteger());
							objguideStepList.setOperationalBorderThickness(readShort());
							objguideStepList.setEvent(readString());
							objguideStepList.setControlName(readString());
							objguideStepList.setMovie(readString());
							objguideStepList.setAutoCommit(readInteger());
							objguideStepList.setSceneType(readInteger());
							objguideStepList.setSkillId(readInteger());
							objguideStepList.setRow1(readShort());
							objguideStepList.setCol1(readShort());
							objguideStepList.setRow2(readShort());
							objguideStepList.setCol2(readShort());
							objguideStepList.setGuideIcon(readInteger());
							objguideStepList.setGuideIconX(readInteger());
							objguideStepList.setGuideIconY(readInteger());
				}
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeShort(guideType);
	writeShort(guideStepList.length);
	for(int i=0; i<guideStepList.length; i++){
	com.hifun.soul.gameserver.guide.msg.GuideStepInfo objguideStepList = guideStepList[i];
				writeInteger(objguideStepList.getMaskColorInt());
				writeShort(objguideStepList.getMaskAlpha());
				writeString(objguideStepList.getGuideText());
				writeShort(objguideStepList.getTextPosX());
				writeShort(objguideStepList.getTextPosY());
				writeShort(objguideStepList.getTextMode());
				writeShort(objguideStepList.getHighlite1PosX());
				writeShort(objguideStepList.getHighlite1PosY());
				writeShort(objguideStepList.getHighlite1Width());
				writeShort(objguideStepList.getHighlite1Height());
				writeInteger(objguideStepList.getHighlite1BorderColor());
				writeShort(objguideStepList.getHighlite1BorderThickness());
				writeShort(objguideStepList.getHighlite2PosX());
				writeShort(objguideStepList.getHighlite2PosY());
				writeShort(objguideStepList.getHighlite2Width());
				writeShort(objguideStepList.getHighlite2Height());
				writeInteger(objguideStepList.getHighlite2BorderColor());
				writeShort(objguideStepList.getHighlite2BorderThickness());
				writeShort(objguideStepList.getHighlite3PosX());
				writeShort(objguideStepList.getHighlite3PosY());
				writeShort(objguideStepList.getHighlite3Width());
				writeShort(objguideStepList.getHighlite3Height());
				writeInteger(objguideStepList.getHighlite3BorderColor());
				writeShort(objguideStepList.getHighlite3BorderThickness());
				writeShort(objguideStepList.getHighlite4PosX());
				writeShort(objguideStepList.getHighlite4PosY());
				writeShort(objguideStepList.getHighlite4Width());
				writeShort(objguideStepList.getHighlite4Height());
				writeInteger(objguideStepList.getHighlite4BorderColor());
				writeShort(objguideStepList.getHighlite4BorderThickness());
				writeShort(objguideStepList.getHighlite5PosX());
				writeShort(objguideStepList.getHighlite5PosY());
				writeShort(objguideStepList.getHighlite5Width());
				writeShort(objguideStepList.getHighlite5Height());
				writeInteger(objguideStepList.getHighlite5BorderColor());
				writeShort(objguideStepList.getHighlite5BorderThickness());
				writeShort(objguideStepList.getArrow1PosX());
				writeShort(objguideStepList.getArrow1PosY());
				writeShort(objguideStepList.getArrow2PosX());
				writeShort(objguideStepList.getArrow2PosY());
				writeShort(objguideStepList.getArrow3PosX());
				writeShort(objguideStepList.getArrow3PosY());
				writeShort(objguideStepList.getOperationalPosX());
				writeShort(objguideStepList.getOperationalPosY());
				writeShort(objguideStepList.getOperationalWidth());
				writeShort(objguideStepList.getOperationalHeight());
				writeInteger(objguideStepList.getOperationalBorderColor());
				writeShort(objguideStepList.getOperationalBorderThickness());
				writeString(objguideStepList.getEvent());
				writeString(objguideStepList.getControlName());
				writeString(objguideStepList.getMovie());
				writeInteger(objguideStepList.getAutoCommit());
				writeInteger(objguideStepList.getSceneType());
				writeInteger(objguideStepList.getSkillId());
				writeShort(objguideStepList.getRow1());
				writeShort(objguideStepList.getCol1());
				writeShort(objguideStepList.getRow2());
				writeShort(objguideStepList.getCol2());
				writeInteger(objguideStepList.getGuideIcon());
				writeInteger(objguideStepList.getGuideIconX());
				writeInteger(objguideStepList.getGuideIconY());
	}
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_BATTLE_GUIDE_FOUR_BOMBS;
	}
	
	@Override
	public String getTypeName() {
		return "GC_BATTLE_GUIDE_FOUR_BOMBS";
	}

	public short getGuideType(){
		return guideType;
	}
		
	public void setGuideType(short guideType){
		this.guideType = guideType;
	}

	public com.hifun.soul.gameserver.guide.msg.GuideStepInfo[] getGuideStepList(){
		return guideStepList;
	}

	public void setGuideStepList(com.hifun.soul.gameserver.guide.msg.GuideStepInfo[] guideStepList){
		this.guideStepList = guideStepList;
	}	
}