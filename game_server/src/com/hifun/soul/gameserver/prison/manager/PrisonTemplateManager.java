package com.hifun.soul.gameserver.prison.manager;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.prison.template.PrisonExperienceTemplate;
import com.hifun.soul.gameserver.prison.template.PrisonUnlockRoomTemplate;
import com.hifun.soul.gameserver.vip.template.PrisonBuyArrestNumTemplate;

@Component
public class PrisonTemplateManager implements IInitializeRequired {
	@Autowired
	private TemplateService templateService;
	Map<Integer, PrisonExperienceTemplate> experienceTemplates = new HashMap<Integer, PrisonExperienceTemplate>();
	Map<Integer, PrisonBuyArrestNumTemplate> buyArrestNumTemplates = new HashMap<Integer, PrisonBuyArrestNumTemplate>();
	Map<Integer, PrisonUnlockRoomTemplate> unlockRoomTemplates = new HashMap<Integer, PrisonUnlockRoomTemplate>();

	@Override
	public void init() {
		experienceTemplates = templateService
				.getAll(PrisonExperienceTemplate.class);
		buyArrestNumTemplates = templateService
				.getAll(PrisonBuyArrestNumTemplate.class);
		unlockRoomTemplates = templateService
				.getAll(PrisonUnlockRoomTemplate.class);
	}

	public PrisonExperienceTemplate getPrisonExperienceTemplate(int level) {
		int size = experienceTemplates.keySet().size();
		if (level > size) {
			level = size;
		}
		return experienceTemplates.get(level);
	}

	public PrisonBuyArrestNumTemplate getPrisonBuyArrestNumTemplate(
			int arrestNum) {
		int size = buyArrestNumTemplates.keySet().size();
		if (arrestNum > size) {
			arrestNum = size;
		}
		return buyArrestNumTemplates.get(arrestNum);
	}

	public PrisonUnlockRoomTemplate getPrisonUnlockRoomTemplate(int roomId) {
		int size = experienceTemplates.keySet().size();
		if (roomId > size) {
			roomId = size;
		}
		return unlockRoomTemplates.get(roomId);
	}

	public int getUnlockRoomCost(int unlockedRoomNum, int roomId) {
		int size = experienceTemplates.keySet().size();
		if (roomId > size) {
			roomId = size;
		}
		int cost = 0;
		for (int i = unlockedRoomNum + 1; i <= roomId; i++) {
			cost += unlockRoomTemplates.get(i).getCostNum();
		}
		return cost;
	}
}
