package com.hifun.soul.logserver.model;
import com.hifun.soul.logserver.MessageType;
import com.hifun.soul.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
public class GmCommandLog extends BaseLogMessage{
       private String gmCommandContext;
    
    public GmCommandLog() {    	
    }

    public GmCommandLog(
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
			String gmCommandContext            ) {
        super(MessageType.LOG_GMCOMMAND_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,param);
            this.gmCommandContext =  gmCommandContext;
    }

       public String getGmCommandContext() {
	       return gmCommandContext;
       }
        
       public void setGmCommandContext(String gmCommandContext) {
	       this.gmCommandContext = gmCommandContext;
       }
    
    @Override
    protected boolean readLogContent() {
	        gmCommandContext =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeString(gmCommandContext);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_GMCOMMAND_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_GMCOMMAND_RECORD";
    }
}