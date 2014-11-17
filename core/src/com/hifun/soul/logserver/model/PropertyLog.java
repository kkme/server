package com.hifun.soul.logserver.model;
import com.hifun.soul.logserver.MessageType;
import com.hifun.soul.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
public class PropertyLog extends BaseLogMessage{
       private int propertyKey;
       private String propertyChange;
    
    public PropertyLog() {    	
    }

    public PropertyLog(
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
			int propertyKey,			String propertyChange            ) {
        super(MessageType.LOG_PROPERTY_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,param);
            this.propertyKey =  propertyKey;
            this.propertyChange =  propertyChange;
    }

       public int getPropertyKey() {
	       return propertyKey;
       }
       public String getPropertyChange() {
	       return propertyChange;
       }
        
       public void setPropertyKey(int propertyKey) {
	       this.propertyKey = propertyKey;
       }
       public void setPropertyChange(String propertyChange) {
	       this.propertyChange = propertyChange;
       }
    
    @Override
    protected boolean readLogContent() {
	        propertyKey =  readInt();
	        propertyChange =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(propertyKey);
	        writeString(propertyChange);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_PROPERTY_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_PROPERTY_RECORD";
    }
}