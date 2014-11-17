package com.hifun.soul.logserver.model;
import com.hifun.soul.logserver.MessageType;
import com.hifun.soul.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
public class HoroscopeLog extends BaseLogMessage{
       private int bagType;
       private int bagIndex;
       private int horoscopeId;
       private int experience;
    
    public HoroscopeLog() {    	
    }

    public HoroscopeLog(
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
			int bagType,			int bagIndex,			int horoscopeId,			int experience            ) {
        super(MessageType.LOG_HOROSCOPE_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,param);
            this.bagType =  bagType;
            this.bagIndex =  bagIndex;
            this.horoscopeId =  horoscopeId;
            this.experience =  experience;
    }

       public int getBagType() {
	       return bagType;
       }
       public int getBagIndex() {
	       return bagIndex;
       }
       public int getHoroscopeId() {
	       return horoscopeId;
       }
       public int getExperience() {
	       return experience;
       }
        
       public void setBagType(int bagType) {
	       this.bagType = bagType;
       }
       public void setBagIndex(int bagIndex) {
	       this.bagIndex = bagIndex;
       }
       public void setHoroscopeId(int horoscopeId) {
	       this.horoscopeId = horoscopeId;
       }
       public void setExperience(int experience) {
	       this.experience = experience;
       }
    
    @Override
    protected boolean readLogContent() {
	        bagType =  readInt();
	        bagIndex =  readInt();
	        horoscopeId =  readInt();
	        experience =  readInt();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeInt(bagType);
	        writeInt(bagIndex);
	        writeInt(horoscopeId);
	        writeInt(experience);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_HOROSCOPE_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_HOROSCOPE_RECORD";
    }
}