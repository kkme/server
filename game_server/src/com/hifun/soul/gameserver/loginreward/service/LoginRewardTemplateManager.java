package com.hifun.soul.gameserver.loginreward.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.item.assist.CommonItemBuilder;
import com.hifun.soul.gameserver.item.assist.SimpleCommonItem;
import com.hifun.soul.gameserver.loginreward.LoginRewardTimeInfo;
import com.hifun.soul.gameserver.loginreward.template.LoginRewardTemplate;
import com.hifun.soul.gameserver.loginreward.template.LoginRewardTimeTemplate;
import com.hifun.soul.gameserver.loginreward.template.LoginSpecialRewardTemplate;

@Scope("singleton")
@Component
public class LoginRewardTemplateManager implements IInitializeRequired {
	@Autowired
	private TemplateService templateService;
	private Map<Integer,LoginSpecialRewardTemplate> specialRewardTemplateMap = new HashMap<Integer,LoginSpecialRewardTemplate>();
	private Map<Integer,LoginRewardTimeTemplate> loginRewardTimeTemplateMap = new HashMap<Integer,LoginRewardTimeTemplate>();
	private LoginRewardTemplate template;
	private LoginRewardTimeInfo[] loginRewardTimeInfos;

	@Override
	public void init() {
		// 特殊奖励模版初始化
		for(LoginSpecialRewardTemplate specialRewardTemplate : templateService
				.getAll(LoginSpecialRewardTemplate.class).values()){
			specialRewardTemplateMap.put(specialRewardTemplate.getId(), specialRewardTemplate);
		}
		// 翻盘奖励模版
		template = templateService.get(SharedConstants.CONFIG_TEMPLATE_DEFAULT_ID, LoginRewardTemplate.class);
		// 连续登陆天数和抽奖次数模版
		for(LoginRewardTimeTemplate loginRewardTimeTemplate : templateService
				.getAll(LoginRewardTimeTemplate.class).values()){
			loginRewardTimeTemplateMap.put(loginRewardTimeTemplate.getId(), loginRewardTimeTemplate);
		}
		// 连续登陆天数和抽奖次数信息
		Map<Integer,LoginRewardTimeInfo> loginRewardTimeInfos = new HashMap<Integer,LoginRewardTimeInfo>();
		for(LoginRewardTimeTemplate loginRewardTimeTemplate : templateService
				.getAll(LoginRewardTimeTemplate.class).values()){
			LoginRewardTimeInfo loginRewardTimeInfo = loginRewardTimeInfos.get(loginRewardTimeTemplate.getRewardNum());
			if(loginRewardTimeInfo == null){
				loginRewardTimeInfo = new LoginRewardTimeInfo();
				loginRewardTimeInfo.setDays(loginRewardTimeTemplate.getId());
				loginRewardTimeInfo.setTimes(loginRewardTimeTemplate.getRewardNum());
				loginRewardTimeInfos.put(loginRewardTimeTemplate.getRewardNum(), loginRewardTimeInfo);
			}
			else if(loginRewardTimeTemplate.getId() < loginRewardTimeInfo.getDays()){
				loginRewardTimeInfo.setDays(loginRewardTimeTemplate.getId());
				loginRewardTimeInfos.put(loginRewardTimeTemplate.getRewardNum(), loginRewardTimeInfo);
			}
		}
		this.loginRewardTimeInfos = loginRewardTimeInfos.values().toArray(new LoginRewardTimeInfo[0]);
	}
	
	/**
	 * 连续登陆和翻盘次数
	 * @return
	 */
	public LoginRewardTimeInfo[] getLoginRewardTimeInfos() {
		return loginRewardTimeInfos;
	}
	
	/**
	 * 连续登陆奖励抽奖次数
	 * @return
	 */
	public LoginRewardTimeTemplate getLoginRewardTimeTemplate(int days) {
		return loginRewardTimeTemplateMap.get(days);
	}
	
	/**
	 * 奖品信息
	 * @param type
	 * @return
	 */
	public SimpleCommonItem getReward(int type) {
		LoginSpecialRewardTemplate template = specialRewardTemplateMap.get(type);
		if(template == null){
			return null;
		}
		return CommonItemBuilder.genSimpleCommonItem(template.getItemId());
	}
	
	/**
	 * 特殊奖励模版
	 * @param days
	 * @return
	 */
	public LoginSpecialRewardTemplate getLoginSpecialRewardTemplate(int days) {
		return specialRewardTemplateMap.get(days);
	}
	
	/**
	 * 所有特殊奖励模版
	 * @return
	 */
	public Collection<LoginSpecialRewardTemplate> getLoginSpecialRewardTemplates() {
		return specialRewardTemplateMap.values();
	}
	
	/**
	 * 随机获取一个奖励
	 * @param itemIds
	 * @return
	 */
	public int getRewardId(List<Integer> itemIds) {
		if(template == null){
			return 0;
		}
		List<Integer> rewardIds = template.getRewards();
		if(rewardIds == null){
			return 0;
		}
		List<Integer> newRewards = new ArrayList<Integer>();
		for(Integer rewardId : rewardIds){
			if(rewardId <= 0){
				continue;
			}
			if(!itemIds.contains(rewardId)){
				newRewards.add(rewardId);
			}
		}
		if(newRewards.size() == 0){
			return 0;
		}
		return newRewards.get(MathUtils.random(newRewards.toArray(new Integer[0])));
	}

}
