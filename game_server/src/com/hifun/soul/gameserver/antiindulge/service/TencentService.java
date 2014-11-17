package com.hifun.soul.gameserver.antiindulge.service;

import com.hifun.soul.gameserver.antiindulge.msg.GCUpdateRevenueRateRequest;
import com.hifun.soul.gameserver.human.Human;

/**
 * 腾讯平台接口
 * @author magicstone
 *
 */
public class TencentService implements ILocalService {
	@Override
	public void updateRevenueRate(Human human) {		
		GCUpdateRevenueRateRequest requestMsg = new GCUpdateRevenueRateRequest();
		human.sendMessage(requestMsg);
	}

}
