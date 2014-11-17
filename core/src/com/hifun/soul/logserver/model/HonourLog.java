package com.hifun.soul.logserver.model;
import com.hifun.soul.logserver.MessageType;
import com.hifun.soul.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
public class HonourLog extends BaseLogMessage{
       private int changeNum;
       private int afterChangeNum;
    
    public HonourLog() {    	
    }

    public HonourLog(
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
			int changeNum,			int afterChangeNum            ) {
        super(MessageType.LOG_HONOUR_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,param);
            this.changeNum =  changeNum;
            this.afterChangeNum =  afterChangeNum;
    }

       public int getChangeNum() {
	       return changeNum;
       }
       public int getAfterChangeNum() {
	       return afterChangeNum;
       }
        
       public void setChangeNum(int changeNum) {
	       this.changeNum = changeNum;
       }
       public void setAfterChangeNum(int afterChangeNum) {
	       this.afterChangeNum = afterChangeNum;
       }
    
    @Override
    protected boolean readLogContent() {
	        changeNum =  readInt();
	        afterChangeNum =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(changeNum);
	        writeInt(afterChangeNum);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_HONOUR_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_HONOUR_RECORD";
    }
}