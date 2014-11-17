package com.hifun.soul.gameserver.human.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.GCMessage;

/**
 * 角色升级信息, 通知客户端展示 
 *
 * @author SevenSoul
 */
@Component
public class GCHumanLevelUpInfo extends GCMessage{
	
	/** 原来等级 */
	private int oldLevel;
	/** 当前等级 */
	private int currentLevel;
	/** 原来HP */
	private int oldHp;
	/** 当前HP */
	private int currentHp;
	/** 原来的属性点 */
	private int oldPropPoint;
	/** 现在的属性点 */
	private int currentPropPoint;
	/** 原来的的技能点 */
	private int oldSkillPoint;
	/** 现在的技能点 */
	private int currentSkillPoint;

	public GCHumanLevelUpInfo (){
	}
	
	public GCHumanLevelUpInfo (
			int oldLevel,
			int currentLevel,
			int oldHp,
			int currentHp,
			int oldPropPoint,
			int currentPropPoint,
			int oldSkillPoint,
			int currentSkillPoint ){
			this.oldLevel = oldLevel;
			this.currentLevel = currentLevel;
			this.oldHp = oldHp;
			this.currentHp = currentHp;
			this.oldPropPoint = oldPropPoint;
			this.currentPropPoint = currentPropPoint;
			this.oldSkillPoint = oldSkillPoint;
			this.currentSkillPoint = currentSkillPoint;
	}

	@Override
	protected boolean readImpl() {
		oldLevel = readInteger();
		currentLevel = readInteger();
		oldHp = readInteger();
		currentHp = readInteger();
		oldPropPoint = readInteger();
		currentPropPoint = readInteger();
		oldSkillPoint = readInteger();
		currentSkillPoint = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(oldLevel);
		writeInteger(currentLevel);
		writeInteger(oldHp);
		writeInteger(currentHp);
		writeInteger(oldPropPoint);
		writeInteger(currentPropPoint);
		writeInteger(oldSkillPoint);
		writeInteger(currentSkillPoint);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.GC_HUMAN_LEVEL_UP_INFO;
	}
	
	@Override
	public String getTypeName() {
		return "GC_HUMAN_LEVEL_UP_INFO";
	}

	public int getOldLevel(){
		return oldLevel;
	}
		
	public void setOldLevel(int oldLevel){
		this.oldLevel = oldLevel;
	}

	public int getCurrentLevel(){
		return currentLevel;
	}
		
	public void setCurrentLevel(int currentLevel){
		this.currentLevel = currentLevel;
	}

	public int getOldHp(){
		return oldHp;
	}
		
	public void setOldHp(int oldHp){
		this.oldHp = oldHp;
	}

	public int getCurrentHp(){
		return currentHp;
	}
		
	public void setCurrentHp(int currentHp){
		this.currentHp = currentHp;
	}

	public int getOldPropPoint(){
		return oldPropPoint;
	}
		
	public void setOldPropPoint(int oldPropPoint){
		this.oldPropPoint = oldPropPoint;
	}

	public int getCurrentPropPoint(){
		return currentPropPoint;
	}
		
	public void setCurrentPropPoint(int currentPropPoint){
		this.currentPropPoint = currentPropPoint;
	}

	public int getOldSkillPoint(){
		return oldSkillPoint;
	}
		
	public void setOldSkillPoint(int oldSkillPoint){
		this.oldSkillPoint = oldSkillPoint;
	}

	public int getCurrentSkillPoint(){
		return currentSkillPoint;
	}
		
	public void setCurrentSkillPoint(int currentSkillPoint){
		this.currentSkillPoint = currentSkillPoint;
	}
}