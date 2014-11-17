package com.hifun.soul.logserver.model;
import com.hifun.soul.logserver.MessageType;
import com.hifun.soul.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
public class MoneyLog extends BaseLogMessage{
       private int currencyType;
       private int currencyNum;
       private int afterNum;
    
    public MoneyLog() {    	
    }

    public MoneyLog(
                   long logTime,
                   int regionId,
                   int serverId,
                   long accountId,
                   String accountName,
                   long charId,
                   String charName,
                   int level,
                   int allianceId,
                   int vipLevel,
                   int reason,
                   String param,
			int currencyType,			int currencyNum,			int afterNum            ) {
        super(MessageType.LOG_MONEY_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,param);
            this.currencyType =  currencyType;
            this.currencyNum =  currencyNum;
            this.afterNum =  afterNum;
    }

       public int getCurrencyType() {
	       return currencyType;
       }
       public int getCurrencyNum() {
	       return currencyNum;
       }
       public int getAfterNum() {
	       return afterNum;
       }
        
       public void setCurrencyType(int currencyType) {
	       this.currencyType = currencyType;
       }
       public void setCurrencyNum(int currencyNum) {
	       this.currencyNum = currencyNum;
       }
       public void setAfterNum(int afterNum) {
	       this.afterNum = afterNum;
       }
    
    @Override
    protected boolean readLogContent() {
	        currencyType =  readInt();
	        currencyNum =  readInt();
	        afterNum =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(currencyType);
	        writeInt(currencyNum);
	        writeInt(afterNum);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_MONEY_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_MONEY_RECORD";
    }
}