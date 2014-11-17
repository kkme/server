package com.hifun.soul.gameserver.guide.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.annotation.SyncOper;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.guide.Guide;
import com.hifun.soul.gameserver.guide.GuideStep;
import com.hifun.soul.gameserver.guide.msg.GCGuideInfo;
import com.hifun.soul.gameserver.guide.msg.GuideStepInfo;
import com.hifun.soul.gameserver.guide.template.GuideTemplate;
import com.hifun.soul.gameserver.human.Human;

/**
 * 引导服务
 * @author magicstone
 */
@Scope("singleton")
@Component
public class GuideTemplateManager implements IInitializeRequired {
	/** 模版服务 */
	@Autowired
	private TemplateService _tmplServ;
	/** 引导字典 */
	private Map<Integer, Guide> _guideMap = Maps.newHashMap();
	/** 是否开启新手引导 */
	private boolean _opened = SharedConstants.GUIDE_OPEN;
	/** key：引导类型/100, value:key同样的引导类型 */
	private Map<Integer,Set<Integer>> guideIds = Maps.newHashMap();

	public GuideTemplateManager() {
		
	}
	
	public void setOpened(boolean isOpen){
		this._opened = isOpen;
	}
	
	/**
	 * 类参数构造器
	 * 
	 * @param tmplServ 
	 * @param seasonServ 
	 * @param opened 
	 * 
	 */
	public GuideTemplateManager(boolean opened) {
		this._opened = opened;
	}

	@SyncOper
	public void init() {
		this.init_guideStep();
	}

	/**
	 * 初始化引导步骤
	 * 
	 */
	private void init_guideStep() {
		// 获取引导配置模版
		Map<Integer, GuideTemplate> guideTmplMap = this._tmplServ.getAll(GuideTemplate.class);
		for (GuideTemplate guideTmpl : guideTmplMap.values()) {
			// 创建引导步骤
			GuideStep gs = new GuideStep(guideTmpl);
			// 获取引导类型
			int gt = gs.getGuideTypeId();
			// 获取引导
			Guide g = this._guideMap.get(gs.getGuideTypeId());
			if (g == null) {
				g = new Guide(gt);
				this._guideMap.put(gt, g);
			}
			g.getGuideStepList().add(gs);
			// 初始化同种类型引导(比如602和601属于同种)id
			int guideType = guideTmpl.getGuideTypeId()/100;
			if(guideIds.get(guideType) == null){
				Set<Integer> guideIdList = new HashSet<Integer>();
				guideIdList.add(guideTmpl.getGuideTypeId());
				guideIds.put(guideType, guideIdList);
			}
			else{
				Set<Integer> guideIdList = guideIds.get(guideType);
				guideIdList.add(guideTmpl.getGuideTypeId());
				guideIds.put(guideType, guideIdList);
			}
		}
		// 引导步骤排序
		Comparator<GuideStep> comparator = new Comparator<GuideStep>() {
			@Override
			public int compare(GuideStep gs0, GuideStep gs1) {
				if (gs0 == null) {
					return -1;
				}

				if (gs1 == null) {
					return +1;
				}

				return gs0.getTemplateId() - gs1.getTemplateId();
			}
		};
		for (Guide g : this._guideMap.values()) {
			Collections.sort(g.getGuideStepList(), comparator);
		}
	}
	
	/**
	 * 获取同种类型引导id列表
	 * @param guideTypeId
	 * @return
	 */
	public Set<Integer> getSameGuideTypeIds(Integer guideTypeId) {
		int guideType = guideTypeId/100;
		return guideIds.get(guideType);
	}

	/**
	 * 获取新手引导
	 * 
	 * @param guideType
	 * @return
	 */
	public Guide getGuide(int guideTypeId) {
		return this._guideMap.get(guideTypeId);
	}

	/**
	 * 显示新手引导
	 * 
	 * @param human 
	 * @param guideType
	 */
	public void showGuide(Human human, int guideTypeId) {
		if (!this._opened) {
			// 如果未开启新手引导, 则直接退出
			return;
		}
		if (human == null) {
			return;
		}
		// 获取新手引导
		Guide guide = this.getGuide(guideTypeId);
		if (guide == null) {
			return;
		}
		List<GuideStepInfo> gsiList = Lists.newArrayList();
		for (GuideStep guideStep : guide.getGuideStepList()) {
			// 添加引导步骤信息
			gsiList.add(guideStep.getGuideStepInfo());
		}
		GCGuideInfo gcmsg = new GCGuideInfo();
		gcmsg.setGuideType((short)guideTypeId);
		gcmsg.setGuideStepList(gsiList.toArray(new GuideStepInfo[0]));
		human.sendMessage(gcmsg);
	}
	
	/**
	 * 主要用于战斗引导，战斗引导和其他引导不同，需特殊处理
	 * @param guideTypeId
	 * @return
	 */
	public List<GuideStepInfo> getGuideStepInfos(int guideTypeId) {
		List<GuideStepInfo> gsiList = Lists.newArrayList();
		if (!this._opened) {
			return gsiList;
		}
		// 获取新手引导
		Guide guide = this.getGuide(guideTypeId);
		if (guide == null) {
			return gsiList;
		}
		for (GuideStep guideStep : guide.getGuideStepList()) {
			// 添加引导步骤信息
			gsiList.add(guideStep.getGuideStepInfo());
		}
		return gsiList;
	}
	
	/**
	 * 引导是否开了
	 * @return
	 */
	public boolean guideIsOpen() {
		return this._opened;
	}
}
