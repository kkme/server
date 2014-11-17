package com.hifun.soul.gameserver.meditation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.function.GameFuncType;
import com.hifun.soul.gameserver.function.service.GameFuncService;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.meditation.template.MeditationAssistPositionTemplate;
import com.hifun.soul.gameserver.meditation.template.MeditationAssistRewardTemplate;
import com.hifun.soul.gameserver.meditation.template.MeditationInfo;
import com.hifun.soul.gameserver.meditation.template.MeditationModeTemplate;
import com.hifun.soul.gameserver.meditation.template.MeditationRewardTemplate;

@Scope("singleton")
@Component
public class MeditationTemplateManager implements IInitializeRequired {
	private Map<Integer,MeditationRewardTemplate> meditationRewardTemplates;
	private MeditationModeTemplate[] meditationModeArray = null;
	private Map<Integer,MeditationAssistRewardTemplate> assistRewardTemplates;
	private Map<Integer,MeditationAssistPositionTemplate> assistPositionTemplates;
	/**key:当前等级; value:不花费魔晶应开启的协助位数量 */
	private Map<Integer,Integer> assistPositionMap  = new HashMap<Integer,Integer>();
	@Autowired
	private TemplateService templateService;
	@Autowired
	private GameFuncService gameFuncService;
	private Map<MeditationModeType,GameFuncType> modeGameFuncMap = new HashMap<MeditationModeType,GameFuncType>();
	private Map<MeditationTimeModeType,GameFuncType> modeTimeGameFuncMap = new HashMap<MeditationTimeModeType,GameFuncType>();
	
	@Override
	public void init() {
		meditationRewardTemplates = templateService.getAll(MeditationRewardTemplate.class);	
		Map<Integer,MeditationModeTemplate> modeTemplates = templateService.getAll(MeditationModeTemplate.class);
		meditationModeArray = new MeditationModeTemplate[0];
		meditationModeArray = modeTemplates.values().toArray(meditationModeArray);
		assistRewardTemplates = templateService.getAll(MeditationAssistRewardTemplate.class);
		assistPositionTemplates = templateService.getAll(MeditationAssistPositionTemplate.class);
		for(Integer id : assistPositionTemplates.keySet()){
			int openLevel = assistPositionTemplates.get(id).getOpenLevel();
			if(!assistPositionMap.containsKey(openLevel)){
				assistPositionMap.put(openLevel, id);
			}
		}
		initGameFuncMap();
	}
	
	private void initGameFuncMap() {
		modeGameFuncMap.put(MeditationModeType.MODE_TWO, GameFuncType.MUSE_MODE_TWO);
		modeGameFuncMap.put(MeditationModeType.MODE_THREE, GameFuncType.MUSE_MODE_THREE);
		modeGameFuncMap.put(MeditationModeType.MODE_FOUR, GameFuncType.MUSE_MODE_FOUR);
		modeTimeGameFuncMap.put(MeditationTimeModeType.TIME_MODE_TWO, GameFuncType.MUSE_TIME_TWO);
		modeTimeGameFuncMap.put(MeditationTimeModeType.TIME_MODE_THREE, GameFuncType.MUSE_TIME_THREE);
		modeTimeGameFuncMap.put(MeditationTimeModeType.TIME_MODE_FOUR, GameFuncType.MUSE_TIME_FOUR);
	}
	
	/**
	 * 获取冥想时长模式
	 * 
	 * @param level 角色等级
	 * @return
	 */
	public MeditationInfo[] getMeditationTimeModes(Human human){
		List<MeditationInfo> newMeditationInfos = new ArrayList<MeditationInfo>();
		List<MeditationInfo> meditationInfos = meditationRewardTemplates.get(human.getLevel()).getMeditationInfo();
		// 这种配置方式好像只能根据索引去判断该功能是不是开放了
		for(int i=0; i<meditationInfos.size(); i++){
			MeditationTimeModeType timeModeType = MeditationTimeModeType.indexOf(i);
			if(timeModeType != null){
				GameFuncType gameFuncType = modeTimeGameFuncMap.get(timeModeType);
				if(gameFuncType == null){
					newMeditationInfos.add(meditationInfos.get(i));
				}
				else if(gameFuncService.gameFuncIsOpen(human, gameFuncType, false)){
					newMeditationInfos.add(meditationInfos.get(i));
				}
			}
		}
		return newMeditationInfos.toArray(new MeditationInfo[0]);
	}
	
	/**
	 * 获取冥想模式
	 * @param level
	 * @param index
	 * @return
	 */
	public MeditationInfo getMeditationTimeMode(int level,int index){
		if(index<0){
			return null;
		}
		if(meditationRewardTemplates.get(level) == null || meditationRewardTemplates.get(level).getMeditationInfo().size()<=index){
			return null;
		}else{
			return meditationRewardTemplates.get(level).getMeditationInfo().get(index);
		}
	}
	
	/**
	 * 获取全部冥想模式
	 * 
	 * @return
	 */
	public MeditationModeTemplate[] getAllMeditatiomMode() {
		return meditationModeArray;		
	}

	/**
	 * 获取可用的冥想模式（有vip等级限制）
	 * 
	 * @return
	 */
	public MeditationModeTemplate[] getAvailableMeditatiomMode(Human human) {
		// 冥想模式要根据vip判断功能是否开放
		List<MeditationModeTemplate> modeTemplateList = new ArrayList<MeditationModeTemplate>();
		for(int i=0; i<meditationModeArray.length; i++){
			MeditationModeType modeType = MeditationModeType.indexOf(i);
			GameFuncType gameFuncType = modeGameFuncMap.get(modeType);
			if(gameFuncType == null){
				modeTemplateList.add(meditationModeArray[i]);
			}
			else if(gameFuncService.gameFuncIsOpen(human, gameFuncType, false)){
				modeTemplateList.add(meditationModeArray[i]);
			}
		}
		return modeTemplateList.toArray(new MeditationModeTemplate[0]);
	}
		
	/**
	 * 获取协助收益加成概率
	 * 
	 * @param assistNum 协助者序号,即第几个协助者
	 * @return
	 */
	public int getAssistRate(int assistNum){
		if(!assistPositionTemplates.containsKey(assistNum)){
			return 0;
		}
		return assistPositionTemplates.get(assistNum).getAssistRate();
	}

	/**
	 * 获取协助奖励
	 * 
	 * @param level
	 * @return
	 */
	public int getAssistReward(int level) {
		if(level<=0){
			return 0;
		}
		int key = 0;
		for(Integer levelLimit : assistRewardTemplates.keySet()){
			if(level<levelLimit){				
				break;
			}
			key = levelLimit;
		}
		return assistRewardTemplates.get(key).getTechPoint();
	}

	/**
	 * 根据协助位获取协助申请发送最大数量
	 * 
	 * @param assistPositionNum
	 * @return
	 */
	public int getAssistApplyNum(int assistPositionNum) {
		if(assistPositionNum<=0){
			return 0;
		}
		return assistPositionTemplates.get(assistPositionNum).getApplyNum();
	}
	
	/**
	 * 获取协助位开启等级
	 * @param positionNum
	 * @return
	 */
	public int getAssistPositionLevelLimit(int positionNum){
		return this.assistPositionMap.get(positionNum);
	}
	
	/**
	 * 获取协助位开启模板
	 * @param assistPositionNum
	 * @return
	 */
	public MeditationAssistPositionTemplate getAssistPositionTemplate(int assistPositionNum){
		return this.assistPositionTemplates.get(assistPositionNum);
	}
	
	/**
	 * 判断冥想选中的模式的功能是否开放
	 * @param selectedModeIndex
	 * @param selectedTimeIndex
	 * @return
	 */
	public boolean checkFuncIsOpen(Human human, int selectedModeIndex, int selectedTimeIndex) {
		MeditationModeType modeType = MeditationModeType.indexOf(selectedModeIndex);
		MeditationTimeModeType timeModeType = MeditationTimeModeType.indexOf(selectedTimeIndex);
		if(modeType == null
				|| timeModeType == null){
			return false;
		}
		GameFuncType modeFuncType = modeGameFuncMap.get(modeType);
		GameFuncType timeModeFuncType = modeTimeGameFuncMap.get(timeModeType);
		boolean modeTypeOpen = true;
		if(modeFuncType != null){
			modeTypeOpen = gameFuncService.gameFuncIsOpen(human, modeGameFuncMap.get(modeType), true);
		}
		boolean timeModeTypeOpen = true;
		if(timeModeFuncType != null){
			timeModeTypeOpen = gameFuncService.gameFuncIsOpen(human, modeTimeGameFuncMap.get(timeModeType), true);
		}
		if(modeTypeOpen && timeModeTypeOpen){
			return true;
		}
		else{
			return false;
		}
	}
	

}
