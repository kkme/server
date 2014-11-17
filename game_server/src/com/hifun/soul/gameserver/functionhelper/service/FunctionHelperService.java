package com.hifun.soul.gameserver.functionhelper.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.function.template.GameFuncTemplate;
import com.hifun.soul.gameserver.functionhelper.FuncCategory;
import com.hifun.soul.gameserver.functionhelper.FuncHelperInfo;
import com.hifun.soul.gameserver.functionhelper.FuncRevenue;
import com.hifun.soul.gameserver.functionhelper.msg.GCOpenFuncHelpPanel;
import com.hifun.soul.gameserver.functionhelper.msg.GCShowFuncHelpInfos;
import com.hifun.soul.gameserver.functionhelper.template.FuncCategoryTemplate;
import com.hifun.soul.gameserver.functionhelper.template.FuncHelperMapTemplate;
import com.hifun.soul.gameserver.human.Human;

/**
 * 功能助手服务
 * 
 * @author magicstone
 * 
 */
@Scope("singleton")
@Component
public class FunctionHelperService implements IInitializeRequired {
	Map<Integer, FuncCategoryTemplate> categoryTemplates;
	private FuncCategory[] funcCategories;
	/** 分类id为主键，有哪些功能 */
	private Map<Integer, List<FuncHelperMapTemplate>> funcCategoryMaps = new HashMap<Integer, List<FuncHelperMapTemplate>>();
	/** 功能id为主键，属于哪几个分类 */
	private Map<Integer, List<FuncHelperMapTemplate>> funcHelperMaps = new HashMap<Integer, List<FuncHelperMapTemplate>>();
	/** 显示在小助手的功能 */
	private Map<Integer, GameFuncTemplate> helperTemplates = new HashMap<Integer, GameFuncTemplate>();

	private Map<Integer, FuncHelperInfo[]> categoryHelperInfos = new HashMap<Integer, FuncHelperInfo[]>();

	@Autowired
	private TemplateService templateService;

	@Override
	public void init() {
		categoryTemplates = templateService.getAll(FuncCategoryTemplate.class);
		funcCategories = new FuncCategory[categoryTemplates.size()];
		int index = 0;
		for (FuncCategoryTemplate template : categoryTemplates.values()) {
			FuncCategory category = new FuncCategory();
			category.setId(template.getId());
			category.setName(template.getName());
			funcCategories[index] = category;
			index++;
		}
		for (Integer funcId : templateService.getAll(GameFuncTemplate.class)
				.keySet()) {
			GameFuncTemplate helperTemplate = templateService.getAll(
					GameFuncTemplate.class).get(funcId);
			if (helperTemplate.isShowInHelper()) {
				helperTemplates.put(funcId, helperTemplate);
			}
		}

		Map<Integer, FuncHelperMapTemplate> funcHelperMapTemplates = templateService
				.getAll(FuncHelperMapTemplate.class);
		for (FuncHelperMapTemplate template : funcHelperMapTemplates.values()) {
			if (funcCategoryMaps.containsKey(template.getCategoryId())) {
				funcCategoryMaps.get(template.getCategoryId()).add(template);
			} else {
				List<FuncHelperMapTemplate> templateList = new ArrayList<FuncHelperMapTemplate>();
				templateList.add(template);
				funcCategoryMaps.put(template.getCategoryId(), templateList);
			}
			if (funcHelperMaps.containsKey(template.getFuncHelperId())) {
				funcHelperMaps.get(template.getFuncHelperId()).add(template);
			} else {
				List<FuncHelperMapTemplate> templateList = new ArrayList<FuncHelperMapTemplate>();
				templateList.add(template);
				funcHelperMaps.put(template.getFuncHelperId(), templateList);
			}
		}
		for (Integer categoryId : funcCategoryMaps.keySet()) {
			// 获取到所有的指定分类的模版;
			List<FuncHelperMapTemplate> helpTempList = funcCategoryMaps
					.get(categoryId);
			FuncHelperInfo[] helperInfos = new FuncHelperInfo[helpTempList
					.size()];
			for (int i = 0; i < helperInfos.length; i++) {
				helperInfos[i] = createFuncHelperInfo(helpTempList.get(i));
			}
			categoryHelperInfos.put(categoryId, helperInfos);
		}
	}

	/**
	 * 根据分类模版创建帮助信息;
	 * 
	 * @param mapTemplate
	 * @return
	 */
	private FuncHelperInfo createFuncHelperInfo(
			FuncHelperMapTemplate mapTemplate) {
		FuncHelperInfo helperInfo = new FuncHelperInfo();
		GameFuncTemplate helperTemplate = helperTemplates.get(mapTemplate
				.getFuncHelperId());
		helperInfo.setDesc(helperTemplate.getDesc());
		helperInfo.setIcon(helperTemplate.getIcon());
		helperInfo.setId(helperTemplate.getId());
		FuncRevenue[] funcRevenues = new FuncRevenue[1];
		funcRevenues[0] = new FuncRevenue();
		funcRevenues[0].setId(mapTemplate.getCategoryId());
		funcRevenues[0].setName(categoryTemplates.get(
				mapTemplate.getCategoryId()).getName());
		funcRevenues[0].setStar(mapTemplate.getStar());
		helperInfo.setFuncRevenues(funcRevenues);
		return helperInfo;
	}

	private FuncHelperInfo[] getFuncHelperInfos(int categoryId, int humanLevel) {
		if (!categoryHelperInfos.containsKey(categoryId)) {
			return new FuncHelperInfo[0];
		}
		GameFuncService gameFuncService = GameServerAssist.getGameFuncService();
		FuncHelperInfo[] result = categoryHelperInfos.get(categoryId);
		int openLevel = 1;
		for (int i = 0; i < result.length; i++) {
			GameFuncTemplate helperTemplate = helperTemplates.get(result[i]
					.getId());
			openLevel = gameFuncService.getGameFuncTemplate(
					helperTemplate.getId()).getOpenLevel();
			result[i].setOpenLevel(openLevel);
			result[i].setOpenState(openLevel <= humanLevel);
		}
		Arrays.sort(result, new FunctionHelperComparator(categoryId));

		return result;
	}

	public void sendOpenFuncHelperPanelMessage(Human human) {
		GCOpenFuncHelpPanel gcMsg = new GCOpenFuncHelpPanel();
		gcMsg.setFuncCategoryList(funcCategories);
		gcMsg.setFuncInfoList(getFuncHelperInfos(funcCategories[0].getId(),
				human.getLevel()));
		human.sendMessage(gcMsg);
	}

	public void sendFuncHelpInfos(Human human, int categoryId) {
		GCShowFuncHelpInfos gcMsg = new GCShowFuncHelpInfos();
		gcMsg.setFuncInfoList(getFuncHelperInfos(categoryId, human.getLevel()));
		human.sendMessage(gcMsg);
	}

}

class FunctionHelperComparator implements Comparator<FuncHelperInfo> {
	private int category;

	public FunctionHelperComparator(int category) {
		this.category = category;
	}

	@Override
	public int compare(FuncHelperInfo o1, FuncHelperInfo o2) {
		int previos1 = 0;
		int previos2 = 0;
		for (FuncRevenue revenue : o1.getFuncRevenues()) {
			if (revenue.getId() == category) {
				previos1 = revenue.getStar();
				break;
			}
		}
		for (FuncRevenue revenue : o2.getFuncRevenues()) {
			if (revenue.getId() == category) {
				previos2 = revenue.getStar();
				break;
			}
		}
		if (previos1 == previos2) {
			return o1.getOpenLevel() - o2.getOpenLevel();
		} else {
			return previos2 - previos1;
		}
	}

}
