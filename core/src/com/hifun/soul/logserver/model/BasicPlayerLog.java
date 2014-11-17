package com.hifun.soul.logserver.model;
import com.hifun.soul.logserver.MessageType;
import com.hifun.soul.logserver.BaseLogMessage;

/**
 * This is an auto generated source,please don't modify it.
 */
public class BasicPlayerLog extends BaseLogMessage{
       private String ip;
       private int crystal;
       private int coin;
       private int experience;
       private int energy;
       private long online_time;
    
    public BasicPlayerLog() {    	
    }

    public BasicPlayerLog(
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
			String ip,			int crystal,			int coin,			int experience,			int energy,			long online_time            ) {
        super(MessageType.LOG_BASICPLAYER_RECORD,logTime,regionId,serverId,accountId,accountName,charId,charName,level,allianceId,vipLevel,reason,param);
            this.ip =  ip;
            this.crystal =  crystal;
            this.coin =  coin;
            this.experience =  experience;
            this.energy =  energy;
            this.online_time =  online_time;
    }

       public String getIp() {
	       return ip;
       }
       public int getCrystal() {
	       return crystal;
       }
       public int getCoin() {
	       return coin;
       }
       public int getExperience() {
	       return experience;
       }
       public int getEnergy() {
	       return energy;
       }
       public long getOnline_time() {
	       return online_time;
       }
        
       public void setIp(String ip) {
	       this.ip = ip;
       }
       public void setCrystal(int crystal) {
	       this.crystal = crystal;
       }
       public void setCoin(int coin) {
	       this.coin = coin;
       }
       public void setExperience(int experience) {
	       this.experience = experience;
       }
       public void setEnergy(int energy) {
	       this.energy = energy;
       }
       public void setOnline_time(long online_time) {
	       this.online_time = online_time;
       }
    
    @Override
    protected boolean readLogContent() {
	        ip =  readString();
	        crystal =  readInt();
	        coin =  readInt();
	        experience =  readInt();
	        energy =  readInt();
	        online_time =  readLong();
        return true;
    }

    @Override
    protected boolean writeLogContent() {
	        writeString(ip);
	        writeInt(crystal);
	        writeInt(coin);
	        writeInt(experience);
	        writeInt(energy);
	        writeLong(online_time);
        return true;
    }
    
    @Override
    public short getType() {
        return MessageType.LOG_BASICPLAYER_RECORD;
    }

    @Override
    public String getTypeName() {
        return "LOG_BASICPLAYER_RECORD";
    }
}