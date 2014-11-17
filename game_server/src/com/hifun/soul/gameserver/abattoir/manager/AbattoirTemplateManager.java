package com.hifun.soul.gameserver.abattoir.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.abattoir.template.AbattoirRoomTemp;
import com.hifun.soul.gameserver.abattoir.template.AbattoirRoomTemplate;
import com.hifun.soul.gameserver.vip.template.AbattoirBuyNumCostTemplate;

/**
 * 角斗场模板管理器
 * 
 * @author yandajun
 * 
 */
@Component
public class AbattoirTemplateManager implements IInitializeRequired {
	@Autowired
	private TemplateService templateService;
	/** 等级区间ID - 房间大模板信息 */
	private Map<Integer, AbattoirRoomTemplate> abattoirRoomTemplates = new HashMap<Integer, AbattoirRoomTemplate>();
	/** 房间ID - 房间小模板信息 */
	private Map<Integer, AbattoirRoomTemp> abattoirRoomTemps = new HashMap<Integer, AbattoirRoomTemp>();
	private Map<Integer, AbattoirBuyNumCostTemplate> buyNumCostTemplates = new HashMap<Integer, AbattoirBuyNumCostTemplate>();

	@Override
	public void init() {
		abattoirRoomTemplates = templateService
				.getAll(AbattoirRoomTemplate.class);
		Map<Integer, AbattoirRoomTemplate> roomTemplates = templateService
				.getAll(AbattoirRoomTemplate.class);
		for (Integer levelRangeId : roomTemplates.keySet()) {
			AbattoirRoomTemplate roomTempalte = roomTemplates.get(levelRangeId);
			AbattoirRoomTemp[] rooms = roomTempalte.getAbattoirRooms();
			for (int no = 1; no <= rooms.length; no++) {
				int roomId = levelRangeId
						* SharedConstants.ABATTOIR_ROOM_ID_INTERVAL + no;
				abattoirRoomTemps.put(roomId, rooms[no - 1]);
			}
		}
		buyNumCostTemplates = templateService
				.getAll(AbattoirBuyNumCostTemplate.class);

	}

	/**
	 * 获取房间大模板信息
	 */
	public AbattoirRoomTemplate getAbattoirRoomTemplate(int roomId) {
		return abattoirRoomTemplates.get(roomIdToTemplateId(roomId));
	}

	/**
	 * 获取所有房间小模板信息
	 */
	public Map<Integer, AbattoirRoomTemp> getAbattoirRoomTemps() {
		return abattoirRoomTemps;
	}

	/**
	 * 获取房间小模板信息
	 */
	public AbattoirRoomTemp getAbattoirRoomTemp(int roomId) {
		return abattoirRoomTemps.get(roomId);
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

	/**
	 * 房间号转化为等级范围ID
	 */
	public int roomIdToTemplateId(int roomId) {
		return roomId / SharedConstants.ABATTOIR_ROOM_ID_INTERVAL;
	}

	/**
	 * 角色等级转化为ID
	 */
	public int levelToTemplateId(int level) {
		for (Integer id : abattoirRoomTemplates.keySet()) {
			AbattoirRoomTemplate template = abattoirRoomTemplates.get(id);
			if (level >= template.getLevelLowest()
					&& level <= template.getLevelHighest()) {
				return template.getId();
			}
		}
		return abattoirRoomTemplates.get(abattoirRoomTemplates.size()).getId();
	}
}
