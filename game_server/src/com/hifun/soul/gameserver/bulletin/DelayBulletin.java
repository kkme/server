package com.hifun.soul.gameserver.bulletin;
import com.hifun.soul.gamedb.entity.BulletinEntity;

/**
 * 定时公告
 * 
 * @author magicstone
 * 
 */
public class DelayBulletin extends Bulletin {

	public DelayBulletin() {
		
	}
	public DelayBulletin(BulletinEntity entity){
		super(entity);
	}
	
	public DelayBulletin(com.hifun.soul.proto.services.Services.Bulletin bulletin){
		super(bulletin);
	}
	@Override
	public BulletinType getBulletinType() {
		return BulletinType.DELAY_BULLETIN;
	}

	@Override
	public BulletinEntity toEntity() {		
		return super.toEntity();
	}
	
	
}
