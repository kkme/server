package com.hifun.soul.logserver.model;
import com.hifun.soul.logserver.MessageType;
import com.hifun.soul.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
public class OnlineTimeLog extends BaseLogMessage{
       private int minute;
       private int totalMinute;
       private long lastLoginTime;
       private long lastLogoutTime;
    
    public OnlineTimeLog() {    	
    }

    public OnlineTimeLog(
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
			int minute,			int totalMinute,			long lastLoginTime,			long lastLogoutTime            ) {
        super(MessageType.LOG_ONLINETIME_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,param);
            this.minute =  minute;
            this.totalMinute =  totalMinute;
            this.lastLoginTime =  lastLoginTime;
            this.lastLogoutTime =  lastLogoutTime;
    }

       public int getMinute() {
	       return minute;
       }
       public int getTotalMinute() {
	       return totalMinute;
       }
       public long getLastLoginTime() {
	       return lastLoginTime;
       }
       public long getLastLogoutTime() {
	       return lastLogoutTime;
       }
        
       public void setMinute(int minute) {
	       this.minute = minute;
       }
       public void setTotalMinute(int totalMinute) {
	       this.totalMinute = totalMinute;
       }
       public void setLastLoginTime(long lastLoginTime) {
	       this.lastLoginTime = lastLoginTime;
       }
       public void setLastLogoutTime(long lastLogoutTime) {
	       this.lastLogoutTime = lastLogoutTime;
       }
    
    @Override
    protected boolean readLogContent() {
	        minute =  readInt();
	        totalMinute =  readInt();
	        lastLoginTime =  readLong();
	        lastLogoutTime =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(minute);
	        writeInt(totalMinute);
	        writeLong(lastLoginTime);
	        writeLong(lastLogoutTime);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_ONLINETIME_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_ONLINETIME_RECORD";
    }
}