package com.hifun.soul.gameserver.human.msg;

import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.gameserver.common.msg.CGMessage;

/**
 * 分配属性点
 * 
 * @author SevenSoul
 */
@Component
public class CGDistributePropertyPoint extends CGMessage{
	
	/** 力量 */
	private int power;
	/** 敏捷 */
	private int agile;
	/** 体力 */
	private int stamina;
	/** 智力 */
	private int intelligence;
	/** 精神 */
	private int spirit;
	
	public CGDistributePropertyPoint (){
	}
	
	public CGDistributePropertyPoint (
			int power,
			int agile,
			int stamina,
			int intelligence,
			int spirit ){
			this.power = power;
			this.agile = agile;
			this.stamina = stamina;
			this.intelligence = intelligence;
			this.spirit = spirit;
	}
	
	@Override
	protected boolean readImpl() {
		power = readInteger();
		agile = readInteger();
		stamina = readInteger();
		intelligence = readInteger();
		spirit = readInteger();
		return true;
	}
	
	@Override
	protected boolean writeImpl() {
		writeInteger(power);
		writeInteger(agile);
		writeInteger(stamina);
		writeInteger(intelligence);
		writeInteger(spirit);
		return true;
	}
	
	@Override
	public short getType() {
		return MessageType.CG_DISTRIBUTE_PROPERTY_POINT;
	}
	
	@Override
	public String getTypeName() {
		return "CG_DISTRIBUTE_PROPERTY_POINT";
	}

	public int getPower(){
		return power;
	}
		
	public void setPower(int power){
		this.power = power;
	}

	public int getAgile(){
		return agile;
	}
		
	public void setAgile(int agile){
		this.agile = agile;
	}

	public int getStamina(){
		return stamina;
	}
		
	public void setStamina(int stamina){
		this.stamina = stamina;
	}

	public int getIntelligence(){
		return intelligence;
	}
		
	public void setIntelligence(int intelligence){
		this.intelligence = intelligence;
	}

	public int getSpirit(){
		return spirit;
	}
		
	public void setSpirit(int spirit){
		this.spirit = spirit;
	}

	@Override
	public void execute() {
	}
}