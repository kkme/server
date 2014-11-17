package com.hifun.soul.gameserver.currency;

/**
 * 具体货币对象
 * @author magicstone
 *
 */
public class Currency {
	private CurrencyType type;
	private int number;
	
	public Currency(){
		
	}
	public Currency(CurrencyType currencyType,int num){
		this.type = currencyType;
		this.number = num;
	}
	
	public CurrencyType getType() {
		return type;
	}
	public void setType(CurrencyType type) {
		this.type = type;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
}
