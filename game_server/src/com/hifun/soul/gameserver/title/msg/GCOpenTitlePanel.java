package com.hifun.soul.gameserver.title.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 响应打开军衔面板
 *
 * @author SevenSoul
 */
@Component
public class GCOpenTitlePanel extends GCMessage{
	
	/** 当前军衔id */
	private int currentTitleId;
	/** 当前威望 */
	private int currentPrestige;
	/** 当日俸禄是否领取 */
	private boolean isGotTitleSalary;
	/** 军衔列表  */
	private com.hifun.soul.gameserver.title.HumanTitle[] titleList;
	/** 默认荣誉上限 */
	private int defaultMaxHonor;

	public GCOpenTitlePanel (){
	}
	
	public GCOpenTitlePanel (
			int currentTitleId,
			int currentPrestige,
			boolean isGotTitleSalary,
			com.hifun.soul.gameserver.title.HumanTitle[] titleList,
			int defaultMaxHonor ){
			this.currentTitleId = currentTitleId;
			this.currentPrestige = currentPrestige;
			this.isGotTitleSalary = isGotTitleSalary;
			this.titleList = titleList;
			this.defaultMaxHonor = defaultMaxHonor;
	}

	@Override
	protected boolean readImpl() {
		short count=0;
		currentTitleId = readInteger();
		currentPrestige = readInteger();
		isGotTitleSalary = readBoolean();
		count = readShort();
		count = count < 0 ? 0 : count;
		titleList = new com.hifun.soul.gameserver.title.HumanTitle[count];
		for(int i=0; i<count; i++){
			com.hifun.soul.gameserver.title.HumanTitle objtitleList = new com.hifun.soul.gameserver.title.HumanTitle();
			titleList[i] = objtitleList;
					objtitleList.setTitleId(readInteger());
							objtitleList.setTitleName(readString());
							objtitleList.setNeedPrestige(readInteger());
							objtitleList.setMaxHonor(readInteger());
							objtitleList.setTitleSalary(readInteger());
							objtitleList.setTitleSkillNum(readInteger());
								{
	int subCounttitleProperties = readShort();
		com.hifun.soul.gameserver.title.template.HumanTitleProperty[] subListtitleProperties = new com.hifun.soul.gameserver.title.template.HumanTitleProperty[subCounttitleProperties];
		objtitleList.setTitleProperties(subListtitleProperties);
	for(int jtitleProperties = 0; jtitleProperties < subCounttitleProperties; jtitleProperties++){
						com.hifun.soul.gameserver.title.template.HumanTitleProperty objtitleProperties = new com.hifun.soul.gameserver.title.template.HumanTitleProperty();
		subListtitleProperties[jtitleProperties] = objtitleProperties;
						objtitleProperties.setPropertyId(readInteger());
								objtitleProperties.setAmendType(readInteger());
								objtitleProperties.setPropertyValue(readInteger());
							}
	}
				}
		defaultMaxHonor = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(currentTitleId);
		writeInteger(currentPrestige);
		writeBoolean(isGotTitleSalary);
	writeShort(titleList.length);
	for(int i=0; i<titleList.length; i++){
	com.hifun.soul.gameserver.title.HumanTitle objtitleList = titleList[i];
				writeInteger(objtitleList.getTitleId());
				writeString(objtitleList.getTitleName());
				writeInteger(objtitleList.getNeedPrestige());
				writeInteger(objtitleList.getMaxHonor());
				writeInteger(objtitleList.getTitleSalary());
				writeInteger(objtitleList.getTitleSkillNum());
					com.hifun.soul.gameserver.title.template.HumanTitleProperty[] titleProperties_objtitleList=objtitleList.getTitleProperties();
	writeShort(titleProperties_objtitleList.length);
	for(int jtitleProperties=0; jtitleProperties<titleProperties_objtitleList.length; jtitleProperties++){
					com.hifun.soul.gameserver.title.template.HumanTitleProperty titleProperties_objtitleList_jtitleProperties = titleProperties_objtitleList[jtitleProperties];
													writeInteger(titleProperties_objtitleList_jtitleProperties.getPropertyId());
													writeInteger(titleProperties_objtitleList_jtitleProperties.getAmendType());
													writeInteger(titleProperties_objtitleList_jtitleProperties.getPropertyValue());
									}
	}
		writeInteger(defaultMaxHonor);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_OPEN_TITLE_PANEL;
	}
	
	@Override
	public String getTypeName() {
		return "GC_OPEN_TITLE_PANEL";
	}

	public int getCurrentTitleId(){
		return currentTitleId;
	}
		
	public void setCurrentTitleId(int currentTitleId){
		this.currentTitleId = currentTitleId;
	}

	public int getCurrentPrestige(){
		return currentPrestige;
	}
		
	public void setCurrentPrestige(int currentPrestige){
		this.currentPrestige = currentPrestige;
	}

	public boolean getIsGotTitleSalary(){
		return isGotTitleSalary;
	}
		
	public void setIsGotTitleSalary(boolean isGotTitleSalary){
		this.isGotTitleSalary = isGotTitleSalary;
	}

	public com.hifun.soul.gameserver.title.HumanTitle[] getTitleList(){
		return titleList;
	}

	public void setTitleList(com.hifun.soul.gameserver.title.HumanTitle[] titleList){
		this.titleList = titleList;
	}	

	public int getDefaultMaxHonor(){
		return defaultMaxHonor;
	}
		
	public void setDefaultMaxHonor(int defaultMaxHonor){
		this.defaultMaxHonor = defaultMaxHonor;
	}
}