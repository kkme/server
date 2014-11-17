package com.hifun.soul.logserver.model;
import com.hifun.soul.logserver.MessageType;
import com.hifun.soul.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
public class ChatLog extends BaseLogMessage{
       private long destPassportId;
       private String destRoleName;
       private int chatType;
       private String content;
    
    public ChatLog() {    	
    }

    public ChatLog(
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
			long destPassportId,			String destRoleName,			int chatType,			String content            ) {
        super(MessageType.LOG_CHAT_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,param);
            this.destPassportId =  destPassportId;
            this.destRoleName =  destRoleName;
            this.chatType =  chatType;
            this.content =  content;
    }

       public long getDestPassportId() {
	       return destPassportId;
       }
       public String getDestRoleName() {
	       return destRoleName;
       }
       public int getChatType() {
	       return chatType;
       }
       public String getContent() {
	       return content;
       }
        
       public void setDestPassportId(long destPassportId) {
	       this.destPassportId = destPassportId;
       }
       public void setDestRoleName(String destRoleName) {
	       this.destRoleName = destRoleName;
       }
       public void setChatType(int chatType) {
	       this.chatType = chatType;
       }
       public void setContent(String content) {
	       this.content = content;
       }
    
    @Override
    protected boolean readLogContent() {
	        destPassportId =  readLong();
	        destRoleName =  readString();
	        chatType =  readInt();
	        content =  readString();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeLong(destPassportId);
	        writeString(destRoleName);
	        writeInt(chatType);
	        writeString(content);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_CHAT_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_CHAT_RECORD";
    }
}