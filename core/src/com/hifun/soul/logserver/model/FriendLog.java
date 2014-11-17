package com.hifun.soul.logserver.model;
import com.hifun.soul.logserver.MessageType;
import com.hifun.soul.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
public class FriendLog extends BaseLogMessage{
       private long friendPassportId;
       private String friendPassportName;
       private long friendRoleId;
       private String friendRoleName;
    
    public FriendLog() {    	
    }

    public FriendLog(
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
			long friendPassportId,			String friendPassportName,			long friendRoleId,			String friendRoleName            ) {
        super(MessageType.LOG_FRIEND_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,param);
            this.friendPassportId =  friendPassportId;
            this.friendPassportName =  friendPassportName;
            this.friendRoleId =  friendRoleId;
            this.friendRoleName =  friendRoleName;
    }

       public long getFriendPassportId() {
	       return friendPassportId;
       }
       public String getFriendPassportName() {
	       return friendPassportName;
       }
       public long getFriendRoleId() {
	       return friendRoleId;
       }
       public String getFriendRoleName() {
	       return friendRoleName;
       }
        
       public void setFriendPassportId(long friendPassportId) {
	       this.friendPassportId = friendPassportId;
       }
       public void setFriendPassportName(String friendPassportName) {
	       this.friendPassportName = friendPassportName;
       }
       public void setFriendRoleId(long friendRoleId) {
	       this.friendRoleId = friendRoleId;
       }
       public void setFriendRoleName(String friendRoleName) {
	       this.friendRoleName = friendRoleName;
       }
    
    @Override
    protected boolean readLogContent() {
	        friendPassportId =  readLong();
	        friendPassportName =  readString();
	        friendRoleId =  readLong();
	        friendRoleName =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeLong(friendPassportId);
	        writeString(friendPassportName);
	        writeLong(friendRoleId);
	        writeString(friendRoleName);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_FRIEND_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_FRIEND_RECORD";
    }
}