package com.hifun.soul.gameserver.building;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hifun.soul.gameserver.building.assist.BuildingFuncFactory;
import com.hifun.soul.gameserver.building.function.IBuildingFunc;
import com.hifun.soul.gameserver.building.msg.BuildingFuncInfo;
import com.hifun.soul.gameserver.building.msg.GCShowBuildingFunc;
import com.hifun.soul.gameserver.building.template.BuildingTemplate;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.common.obj.StaticObject;
import com.hifun.soul.gameserver.function.template.GameFuncTemplate;
import com.hifun.soul.gameserver.human.Human;

/**
 * 建筑抽象
 * 
 * @author magicstone
 * 
 */
public abstract class AbstractBuilding extends StaticObject implements IBuilding {
	/** 建筑功能列表 */
	private Map<Integer, IBuildingFunc> funcs = new HashMap<Integer, IBuildingFunc>();
	/** 建筑模版 */
	private BuildingTemplate buildingTemplate;

	/** 建筑等级默认值为1 */
	private int level = 1;

	public void init() {
		buildingTemplate = GameServerAssist.getBuildingTemplateManager()
				.getBuildingTemplate(getBuildingType());
		Set<GameFuncTemplate> gameFuncTemplates = GameServerAssist.getBuildingTemplateManager()
				.getGameFuncTemplates(getBuildingType());
		if (gameFuncTemplates != null && gameFuncTemplates.size() > 0) {
			for (GameFuncTemplate template : gameFuncTemplates) {
				IBuildingFunc func = BuildingFuncFactory
						.createBuildingFunc(template.getId());
				if (func != null) {
					funcs.put(template.getId(), func);
				}
			}
		}
	}

	protected Collection<IBuildingFunc> getFuncList() {
		return funcs.values();
	}

	public void onClick(Human human, long UUID) {
		// 初始化该场景对于该玩家可见的功能列表
		List<BuildingFuncInfo> funcList = new ArrayList<BuildingFuncInfo>();

		if (this.funcs != null) {
			for (IBuildingFunc _func : funcs.values()) {
				if (_func.isOpen()
						&& human.getHumanBuildingManager().canSee(
								_func.getBuildingFuncType())) {
					BuildingFuncInfo fi = new BuildingFuncInfo();

					fi.setFuncId(_func.getBuildingFuncType());
					fi.setName(_func.getName());
					fi.setDesc(_func.getDesc());

					funcList.add(fi);
				}
			}
		}

		if (funcList != null && funcList.size() > 0) {
			GCShowBuildingFunc gcShowBuildingFunc = new GCShowBuildingFunc();

			gcShowBuildingFunc.setBuildingType(getBuildingType());
			gcShowBuildingFunc.setFuncs(funcList
					.toArray(new BuildingFuncInfo[funcList.size()]));

			human.sendMessage(gcShowBuildingFunc);
		}

	}

	@Override
	public void onClickFunc(Human human, long UUID, int funcId) {
		IBuildingFunc buildingFunc = funcs.get(funcId);

		if (buildingFunc != null) {
			buildingFunc.execute(human, UUID, this);
		}
	}

	@Override
	public BuildingTemplate getTemplate() {
		return buildingTemplate;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public String getName() {
		return buildingTemplate.getName();
	}

	@Override
	public int getIcon() {
		return buildingTemplate.getIcon();
	}
}
