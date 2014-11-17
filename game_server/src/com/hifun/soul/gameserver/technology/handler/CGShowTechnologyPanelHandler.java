package com.hifun.soul.gameserver.technology.handler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.guide.GuideType;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.technology.manager.HumanTechnologyManager;
import com.hifun.soul.gameserver.technology.msg.CGShowTechnologyPanel;
import com.hifun.soul.gameserver.technology.msg.GCShowTechnologyPanel;
import com.hifun.soul.gameserver.technology.msg.TechnologyDetailInfo;
import com.hifun.soul.gameserver.technology.msg.TechnologyInfo;
import com.hifun.soul.gameserver.technology.service.TechnologyTemplateManager;
import com.hifun.soul.gameserver.technology.template.TechnologyTemplate;

@Component
public class CGShowTechnologyPanelHandler implements
		IMessageHandlerWithType<CGShowTechnologyPanel> {

	@Autowired
	private TechnologyTemplateManager technologyTemplateManager;
	@Autowired
	private GameFuncService gameFuncService;
	
	@Override
	public short getMessageType() {
		return MessageType.CG_SHOW_TECHNOLOGY_PANEL;
	}

	@Override
	public void execute(CGShowTechnologyPanel message) {
		Player player = message.getPlayer();
		if(player == null){
			return;
		}
		
		Human human = player.getHuman();
		if(human == null){
			return;
		}
		
		// 判断功能是否开放
		if(!gameFuncService.gameFuncIsOpen(human, GameFuncType.TECHNOLOGY, true)){
			return;
		}
		
		// 页面索引
		int pageIndex = message.getPageIndex();
		if(pageIndex < 0){
			return;
		}
		
		HumanTechnologyManager humanTechnologyManager = human.getHumanTechnologyManager();
		// 打开科技面板
		GCShowTechnologyPanel gcMsg = new GCShowTechnologyPanel();
		gcMsg.setPageIndex((short)pageIndex);
		gcMsg.setTechnologyNum(humanTechnologyManager.getTechnologyPoints());
		List<TechnologyInfo> technologyInfos = new ArrayList<TechnologyInfo>();
		TechnologyInfo techInfo=null;
		for(TechnologyTemplate technologyTemplate : technologyTemplateManager.getAllTechnologyTemplate()){
			if(technologyTemplate.getOpenLevel() > human.getLevel()){
				continue;
			}
			int level = humanTechnologyManager.getTechnologyLevel(technologyTemplate.getId());
			TechnologyDetailInfo  technologyDetailInfo 
				= technologyTemplateManager.getTechnologyDetailInfo(technologyTemplate.getId(),level);
			if(technologyDetailInfo != null){
				techInfo = technologyTemplateManager.getTechnologyInfo(technologyDetailInfo);
				technologyInfos.add(techInfo);
			}
		}
		gcMsg.setTotalSize((short) technologyInfos.size());
		List<TechnologyInfo> technologyList = getTechnologyInfos(humanTechnologyManager,technologyInfos,pageIndex);
		gcMsg.setTechnologyInfos(technologyList.toArray(new TechnologyInfo[0]));
		TechnologyDetailInfo technologyDetailInfo = getDefaultTechnologyDetailInfo(humanTechnologyManager,technologyList);
		gcMsg.setTechnologyDetailInfo(technologyDetailInfo);
		human.sendMessage(gcMsg);
		
		// 打开科技面板的引导
		human.getHumanGuideManager().showGuide(GuideType.OPEN_TECHNOLOGY_PANEL.getIndex());
		
	}
	
	/**
	 * 获取该页科技信息
	 * 
	 * @param humanTechnologyManager
	 * @param pageIndex
	 * @return
	 */
	private List<TechnologyInfo> getTechnologyInfos(HumanTechnologyManager humanTechnologyManager, List<TechnologyInfo> technologyInfos, int pageIndex) {
		int _totalSize = technologyInfos.size();
		
		int _startPos = pageIndex * SharedConstants.TECHNOLOGY_PAGE_SIZE;
		
		// 开始位置检查
		if (_startPos < 0 || _startPos >= _totalSize) {
			_startPos = _totalSize;
		}
		 
		int _endPos = (pageIndex + 1) * SharedConstants.TECHNOLOGY_PAGE_SIZE;
		// 结束位置大于当前总数，则置为总数
		if (_endPos > _totalSize) {
			_endPos = _totalSize;
		}
		
		List<TechnologyInfo> technologyList = Lists.newArrayList();
		
		// 获取起始位置之间的科技信息
		for (int i = _startPos; i < _endPos; i++) {
			TechnologyInfo technologyInfo = technologyInfos.get(i);
			if (technologyInfo == null) {
				continue;
			}				
			technologyList.add(technologyInfo);	
		}
		
		return technologyList;
	}
	
	/**
	 * 获取默认科技信息
	 * 
	 * @param humanTechnologyManager
	 * @param technologyList
	 * @return
	 */
	private TechnologyDetailInfo getDefaultTechnologyDetailInfo(HumanTechnologyManager humanTechnologyManager,List<TechnologyInfo> technologyList) {
		TechnologyDetailInfo technologyDetailInfo = new TechnologyDetailInfo();
		if(technologyList.size() == 0){
			return technologyDetailInfo;
		}
		else{
			int defaultTechnologyId = technologyList.get(0).getTechnologyId();
			technologyDetailInfo = technologyTemplateManager
					.getTechnologyDetailInfo(defaultTechnologyId,humanTechnologyManager.getTechnologyLevel(defaultTechnologyId));
			if(technologyDetailInfo == null){
				technologyDetailInfo = new TechnologyDetailInfo();
			}
			
			return technologyDetailInfo;
		}
	}

}
