package com.hifun.soul.gameserver.function.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.template.GameFuncTemplate;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.human.msg.GCFunctionInfo.FunctionInfo;

@Scope("singleton")
@Component
public class GameFuncService implements IInitializeRequired {
	@Autowired
	private TemplateService templateService;
	private Map<Integer, GameFuncTemplate> gameFuncTemplateMap = new HashMap<Integer, GameFuncTemplate>();

	@Override
	public void init() {
		initGameFuncTemplateMap();
	}

	private void initGameFuncTemplateMap() {
		gameFuncTemplateMap = templateService.getAll(GameFuncTemplate.class);
	}

	/**
	 * 游戏功能模版
	 * 
	 * @param funcId
	 * @return
	 */
	public GameFuncTemplate getGameFuncTemplate(int funcId) {
		return gameFuncTemplateMap.get(funcId);
	}

	/**
	 * 获取某个等级开启的所有游戏功能模版
	 * 
	 * @param level
	 * @return
	 */
	public List<GameFuncTemplate> getGameFuncTemplates(int level) {
		List<GameFuncTemplate> funcTemplateList = new ArrayList<GameFuncTemplate>();
		for (Integer funcId : gameFuncTemplateMap.keySet()) {
			GameFuncTemplate funcTempalte = gameFuncTemplateMap.get(funcId);
			if (funcTempalte.getOpenLevel() == level) {
				funcTemplateList.add(funcTempalte);
			}
		}
		return funcTemplateList;
	}

	/**
	 * 判断功能是否已经开放
	 * 
	 * @param human
	 * @param funcId
	 * @return
	 */
	public boolean gameFuncIsOpen(Human human, GameFuncType funcType,
			boolean isNotify) {
		GameFuncTemplate gameFuncTemplate = getGameFuncTemplate(funcType
				.getIndex());
		// 找不到功能控制模版默认开放
		if (gameFuncTemplate == null) {
			return true;
		}
		// 关闭阵营功能 2014-05-04
		if (funcType == GameFuncType.FACTION) {
			return false;
		}
		// 如果个人目标全部完成则关闭
		if (funcType == GameFuncType.TARGET
				&& human.getHumanTargetManager().isAllTargetsCompleted()) {
			return false;
		}
		// 判断是否到达开放等级
		if (gameFuncTemplate.getOpenLevel() > human.getLevel()) {
			if (isNotify) {
				human.sendErrorMessage(LangConstants.CHARACTER_LEVEL_LIMIT,
						gameFuncTemplate.getOpenLevel());
			}
			return false;
		}
		// 判断是否到达vip等级
		if (gameFuncTemplate.getVipLevel() > human.getVipLevel()) {
			if (isNotify) {
				human.sendErrorMessage(LangConstants.VIP_NOT_ENOUGH,
						gameFuncTemplate.getVipLevel());
			}
			return false;
		}
		return true;
	}

	/**
	 * 获取所有开放的功能
	 * 
	 * @param human
	 * @return
	 */
	public int[] getOpenedGameFuncs(Human human) {
		List<Integer> openGameFuncList = new ArrayList<Integer>();
		for (GameFuncTemplate template : gameFuncTemplateMap.values()) {
			if (template == null) {
				continue;
			}
			if (gameFuncIsOpen(human, GameFuncType.indexOf(template.getId()),
					false)) {
				openGameFuncList.add(template.getId());
			}
		}
		int[] openGameFuncs = new int[openGameFuncList.size()];
		for (int i = 0; i < openGameFuncs.length; i++) {
			openGameFuncs[i] = openGameFuncList.get(i);
		}
		return openGameFuncs;
	}

	/**
	 * 获取当前等级和vip等级需要开放的功能
	 * 
	 * @param level
	 * @param vipLevel
	 * @return
	 */
	public int[] getSuitableGameFuncs(List<Integer> openFuncs, Human human,
			List<Integer> closeFuncs) {
		List<Integer> suitGameFuncList = new ArrayList<Integer>();
		for (GameFuncTemplate template : gameFuncTemplateMap.values()) {
			if (template == null) {
				continue;
			}
			if (template.getOpenLevel() <= human.getLevel()
					&& template.getVipLevel() <= human.getVipLevel()
					&& !openFuncs.contains(template.getId()) 
					/** editby: crazyjohn 2014-4-2 添加了功能是否符合的限制 */
					&& gameFuncIsOpen(human, GameFuncType.indexOf(template.getId()),
							false)) {
				suitGameFuncList.add(template.getId());
			}
			// add by will@2013-5-7增加vip体验卡道具后可能会出现一种情况，某些已开启的功能在体验时间结束后要关闭
			else if (openFuncs.contains(template.getId())
					&& template.getVipLevel() > human.getVipLevel()) {
				closeFuncs.add(template.getId());
			}
		}
		int[] suitGameFuncs = new int[suitGameFuncList.size()];
		for (int i = 0; i < suitGameFuncs.length; i++) {
			suitGameFuncs[i] = suitGameFuncList.get(i);
		}
		return suitGameFuncs;
	}

	public List<FunctionInfo> getFunctionInfo() {
		List<FunctionInfo> result = new ArrayList<FunctionInfo>();
		for (GameFuncTemplate template : gameFuncTemplateMap.values()) {
			if (template == null) {
				continue;
			}
			FunctionInfo function = new FunctionInfo();
			function.setId(template.getId());
			function.setOpenLevel(template.getOpenLevel());
			result.add(function);
		}
		return result;
	}

}
