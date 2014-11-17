package com.hifun.soul.gameserver.mars.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.mars.template.MarsBattleRewardTemplate;
import com.hifun.soul.gameserver.mars.template.MarsBetTemplate;
import com.hifun.soul.gameserver.mars.template.MarsFieldTemplate;
import com.hifun.soul.gameserver.mars.template.MarsRoomTemplate;
import com.hifun.soul.gameserver.vip.template.MarsBuyMultipleNumCostTemplate;

/**
 * 战神之巅模板管理器
 * 
 * @author yandajun
 * 
 */
@Component
public class MarsTemplateManager implements IInitializeRequired {
	@Autowired
	private TemplateService templateService;
	/** 战场类型 - 战场模板 */
	Map<Integer, MarsFieldTemplate> marsFieldTemplates = new HashMap<Integer, MarsFieldTemplate>();
	/** 房间类型 - 房间模板 */
	Map<Integer, MarsRoomTemplate> marsRoomTemplates = new HashMap<Integer, MarsRoomTemplate>();
	/** 等级 - 奖励模板 */
	Map<Integer, MarsBattleRewardTemplate> marsBattleRewardTemplates = new HashMap<Integer, MarsBattleRewardTemplate>();
	/** 倍数 - 加倍模板 */
	Map<Integer, MarsBetTemplate> marsBetTemplates = new HashMap<Integer, MarsBetTemplate>();
	/** 购买加倍次数 - 花费魔晶数 */
	Map<Integer, MarsBuyMultipleNumCostTemplate> buyMultipleNumCostTemplates = new HashMap<Integer, MarsBuyMultipleNumCostTemplate>();

	@Override
	public void init() {
		marsFieldTemplates = templateService.getAll(MarsFieldTemplate.class);
		marsRoomTemplates = templateService.getAll(MarsRoomTemplate.class);
		marsBetTemplates = templateService.getAll(MarsBetTemplate.class);
		marsBattleRewardTemplates = templateService
				.getAll(MarsBattleRewardTemplate.class);
		buyMultipleNumCostTemplates = templateService
				.getAll(MarsBuyMultipleNumCostTemplate.class);
	}

	public Map<Integer, MarsFieldTemplate> getMarsFieldTemplates() {
		return marsFieldTemplates;
	}

	public MarsFieldTemplate getMarsFieldTempalte(int level) {
		for (int fieldType : marsFieldTemplates.keySet()) {
			MarsFieldTemplate marsFieldTemplate = marsFieldTemplates
					.get(fieldType);
			if (level >= marsFieldTemplate.getStartLevel()
					&& level <= marsFieldTemplate.getEndLevel()) {
				return marsFieldTemplate;
			}
		}
		return null;
	}

	public Map<Integer, MarsRoomTemplate> getMarsRoomTemplates() {
		return marsRoomTemplates;
	}

	public MarsRoomTemplate getMarsRoomTemplate(int roomType) {
		return marsRoomTemplates.get(roomType);
	}

	public Map<Integer, MarsBattleRewardTemplate> getMarsBattleRewardTemplates() {
		return marsBattleRewardTemplates;
	}

	public MarsBattleRewardTemplate getMarsBattleRewardTemplate(int level) {
		return marsBattleRewardTemplates.get(level);
	}

	public Map<Integer, MarsBetTemplate> getMarsBetTemplates() {
		return marsBetTemplates;
	}

	public MarsBetTemplate getMarsBetTemplate(int multiple) {
		return marsBetTemplates.get(multiple);
	}

	public int getBuyMultipleNumCost(int buyNum) {
		if (buyNum > buyMultipleNumCostTemplates.size()) {
			return 0;
		}
		return buyMultipleNumCostTemplates.get(buyNum).getCrystal();
	}
}
