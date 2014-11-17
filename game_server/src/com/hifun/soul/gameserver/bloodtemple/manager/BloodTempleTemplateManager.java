package com.hifun.soul.gameserver.bloodtemple.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.bloodtemple.template.BloodTempleRoomTemplate;
import com.hifun.soul.gameserver.vip.template.BloodTempleBuyNumCostTemplate;

/**
 * 嗜血神殿模板管理器
 * 
 * @author yandajun
 * 
 */
@Component
public class BloodTempleTemplateManager implements IInitializeRequired {
	@Autowired
	private TemplateService templateService;
	/** 房间ID - 房间模板信息 */
	private Map<Integer, BloodTempleRoomTemplate> bloodTempleRoomTemplates = new HashMap<Integer, BloodTempleRoomTemplate>();
	private Map<Integer, BloodTempleBuyNumCostTemplate> buyNumCostTemplates = new HashMap<Integer, BloodTempleBuyNumCostTemplate>();

	@Override
	public void init() {
		bloodTempleRoomTemplates = templateService
				.getAll(BloodTempleRoomTemplate.class);
		buyNumCostTemplates = templateService
				.getAll(BloodTempleBuyNumCostTemplate.class);

	}

	/**
	 * 获取所有房间模板信息
	 */
	public Map<Integer, BloodTempleRoomTemplate> getBloodTempleRoomTemplates() {
		return bloodTempleRoomTemplates;
	}

	/**
	 * 获取下次购买角斗次数花费
	 * 
	 * @param buyNum
	 * @return
	 */
	public int getBuyWrestleNumCost(int buyNum) {
		if (buyNum < 1) {
			return 0;
		} else if (buyNum > buyNumCostTemplates.size()) {
			return 0;
		}
		return buyNumCostTemplates.get(buyNum).getCrystal();
	}
}
