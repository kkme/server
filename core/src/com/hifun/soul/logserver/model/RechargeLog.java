package com.hifun.soul.logserver.model;
import com.hifun.soul.logserver.MessageType;
import com.hifun.soul.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
public class RechargeLog extends BaseLogMessage{
       private int rechargeNum;
       private int crystalNum;
       private int crystalRewardNum;
       private int beforeNum;
       private int afterNum;
       private int exchangeRate;
    
    public RechargeLog() {    	
    }

    public RechargeLog(
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
			int rechargeNum,			int crystalNum,			int crystalRewardNum,			int beforeNum,			int afterNum,			int exchangeRate            ) {
        super(MessageType.LOG_RECHARGE_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,param);
            this.rechargeNum =  rechargeNum;
            this.crystalNum =  crystalNum;
            this.crystalRewardNum =  crystalRewardNum;
            this.beforeNum =  beforeNum;
            this.afterNum =  afterNum;
            this.exchangeRate =  exchangeRate;
    }

       public int getRechargeNum() {
	       return rechargeNum;
       }
       public int getCrystalNum() {
	       return crystalNum;
       }
       public int getCrystalRewardNum() {
	       return crystalRewardNum;
       }
       public int getBeforeNum() {
	       return beforeNum;
       }
       public int getAfterNum() {
	       return afterNum;
       }
       public int getExchangeRate() {
	       return exchangeRate;
       }
        
       public void setRechargeNum(int rechargeNum) {
	       this.rechargeNum = rechargeNum;
       }
       public void setCrystalNum(int crystalNum) {
	       this.crystalNum = crystalNum;
       }
       public void setCrystalRewardNum(int crystalRewardNum) {
	       this.crystalRewardNum = crystalRewardNum;
       }
       public void setBeforeNum(int beforeNum) {
	       this.beforeNum = beforeNum;
       }
       public void setAfterNum(int afterNum) {
	       this.afterNum = afterNum;
       }
       public void setExchangeRate(int exchangeRate) {
	       this.exchangeRate = exchangeRate;
       }
    
    @Override
    protected boolean readLogContent() {
	        rechargeNum =  readInt();
	        crystalNum =  readInt();
	        crystalRewardNum =  readInt();
	        beforeNum =  readInt();
	        afterNum =  readInt();
	        exchangeRate =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(rechargeNum);
	        writeInt(crystalNum);
	        writeInt(crystalRewardNum);
	        writeInt(beforeNum);
	        writeInt(afterNum);
	        writeInt(exchangeRate);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_RECHARGE_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_RECHARGE_RECORD";
    }
}