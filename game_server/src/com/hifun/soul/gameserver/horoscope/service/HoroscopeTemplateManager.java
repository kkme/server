package com.hifun.soul.gameserver.horoscope.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.core.util.KeyUtil;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.horoscope.HoroscopeBagType;
import com.hifun.soul.gameserver.horoscope.HoroscopeSoulInfo;
import com.hifun.soul.gameserver.horoscope.HoroscopeSoulType;
import com.hifun.soul.gameserver.horoscope.msg.HoroscopeInfo;
import com.hifun.soul.gameserver.horoscope.template.HoroscopeGambleTemplate;
import com.hifun.soul.gameserver.horoscope.template.HoroscopeTemplate;
import com.hifun.soul.gameserver.horoscope.template.HoroscopeTypeTemplate;

@Scope("singleton")
@Component
public class HoroscopeTemplateManager implements IInitializeRequired {
	private Logger logger = Loggers.HOROSCOPE_LOGGER;
	@Autowired
	private TemplateService templateService;
	private Map<Integer,HoroscopeGambleTemplate> horoscopeGambleTemplates;
	private Map<Integer,HoroscopeTypeTemplate> horoscopeTypeTemplates;
	private Map<Integer,HoroscopeTemplate> horoscopeTemplates;
	
	
	@Override
	public void init() {
		horoscopeGambleTemplates = templateService.getAll(HoroscopeGambleTemplate.class);
		horoscopeTypeTemplates = templateService.getAll(HoroscopeTypeTemplate.class);
		horoscopeTemplates = templateService.getAll(HoroscopeTemplate.class);
	}
	
	/**
	 * 根据占星师id获取占星模版
	 * @param stargazerId
	 * @return
	 */
	public HoroscopeGambleTemplate getHoroscopeGambleTemplate(int stargazerId) {
		return horoscopeGambleTemplates.get(stargazerId);
	}
	
	/**
	 * 获取星运装备位类型模版
	 * @param horoscopeSoulType
	 * @return
	 */
	public HoroscopeTypeTemplate getHoroscopeTypeTemplate(int horoscopeSoulType) {
		return horoscopeTypeTemplates.get(horoscopeSoulType);
	}
	
	/**
	 * 获取随机到的星运id
	 * @param template
	 * @param rate
	 * @return
	 */
	public int randHoroscopeId(HoroscopeGambleTemplate template) {
		int greenRate = template.getGreenRate();
		int blueRate = template.getBlueRate() + greenRate;
		int purpleRate = template.getPurpleRate() + blueRate;
		int yellowRate = template.getYellowRate() + purpleRate;
		int rate = MathUtils.random(0, yellowRate);
		String horoscopeIds = null;
		if(rate <= greenRate){
			horoscopeIds = template.getGreenIds();
		}
		else if(rate >= greenRate
				&& rate < blueRate){
			horoscopeIds = template.getBlueIds();
		}
		else if(rate >= blueRate
				&& rate < purpleRate){
			horoscopeIds = template.getPurpleIds();
		}
		else if(rate >= purpleRate
				&& rate < yellowRate){
			horoscopeIds = template.getYellowIds();
		}
		if(horoscopeIds == null){
			return 0;
		}
		String[] ids = horoscopeIds.split(",");
		if(ids == null
				|| ids.length == 0){
			return 0;
		}
		return Integer.valueOf(ids[MathUtils.random(0, ids.length-1)]);
	}
	
	/**
	 * 根据星运id获取对应的星运信息
	 * @param horoscopeId
	 * @return
	 */
	public HoroscopeInfo genHoroscopeInfo(int horoscopeId, HoroscopeBagType bagType) {
		if(horoscopeId < 0){
			return null;
		}
		HoroscopeTemplate template = horoscopeTemplates.get(horoscopeId);
		if(template == null){
			return null;
		}
		HoroscopeInfo horoscopeInfo = new HoroscopeInfo();
		horoscopeInfo.setUuid(KeyUtil.UUIDKey());
		horoscopeInfo.setColor(template.getColor());
		horoscopeInfo.setDesc(template.getDesc());
		horoscopeInfo.setExperience(template.getDefaultExperience());
		horoscopeInfo.setHoroscopeBag(bagType.getIndex());
		horoscopeInfo.setHoroscopeId(horoscopeId);
		horoscopeInfo.setIcon(template.getIcon());
		horoscopeInfo.setKey(template.getHoroscopeKey());
		horoscopeInfo.setLevel(template.getLevel());
		horoscopeInfo.setMaxExperience(template.getExperience());
		horoscopeInfo.setName(template.getName());
		horoscopeInfo.setValue(template.getHoroscopeValue());
		horoscopeInfo.setNextHoroscopeId(template.getNextHoroscopeId());
		horoscopeInfo.setPropertyAddType(template.getPropertyType());
		return horoscopeInfo;
	}
	
	/**
	 * 获取到吃掉之后应该获取的星运
	 * @param wolfHoroscopeInfo
	 * @param sheepHoroscopeInfo
	 */
	public HoroscopeInfo getCompoundHoroscopeInfo(HoroscopeInfo wolfHoroscopeInfo, HoroscopeInfo sheepHoroscopeInfo) {
		HoroscopeInfo horoscopeInfo = genHoroscopeInfo(wolfHoroscopeInfo.getHoroscopeId(), HoroscopeBagType.MAIN_BAG);
		int experience = wolfHoroscopeInfo.getExperience() + sheepHoroscopeInfo.getExperience();
		if(experience >= wolfHoroscopeInfo.getMaxExperience() 
				&& wolfHoroscopeInfo.getNextHoroscopeId() > 0){
			int suitableHoroscopeId = getSuitableHoroscopeId(wolfHoroscopeInfo.getNextHoroscopeId(),experience);
			horoscopeInfo = genHoroscopeInfo(suitableHoroscopeId, HoroscopeBagType.MAIN_BAG);
		}
//		if(experience > horoscopeInfo.getMaxExperience()){
//			experience = horoscopeInfo.getMaxExperience();
//		}
		horoscopeInfo.setExperience(experience);
		return horoscopeInfo;
	}
	
	/**
	 * 获取到应该获取的星运id
	 * @param horoscopeId
	 * @param experience
	 * @return
	 */
	private int getSuitableHoroscopeId(int horoscopeId, int experience) {
		int suitableHoroscopeId = horoscopeId;
		int i=0;
		while(true){
			if(i>1000){
				logger.error(String.format("may be horoscopeTemplate config error. horoscopeId:%s;experience:%s", horoscopeId, experience));
				return suitableHoroscopeId;
			}
			HoroscopeTemplate template = horoscopeTemplates.get(suitableHoroscopeId);
			if(template == null){
				break;
			}
			else{
				if(template.getNextHoroscopeId() <= 0
						|| template.getExperience() > experience){
					break;
				}
				else{
					suitableHoroscopeId = template.getNextHoroscopeId();
				}
			}
			i++;
		}
		return suitableHoroscopeId;
	}
	
	/**
	 * 根据等级去判断应该开启哪些运魂位置
	 * @param level
	 * @return
	 */
	public HoroscopeSoulType[] getOpenHoroscopeTypes(int level) {
		List<HoroscopeSoulType> horoscopeSoulTypes = new ArrayList<HoroscopeSoulType>();
		for(HoroscopeTypeTemplate template : horoscopeTypeTemplates.values()){
			if(level >= template.getLevel()){
				horoscopeSoulTypes.add(HoroscopeSoulType.indexOf(template.getId()));
			}
		}
		return horoscopeSoulTypes.toArray(new HoroscopeSoulType[horoscopeSoulTypes.size()]);
	}
	
	/**
	 * 获取运魂位的信息
	 * @param level
	 * @return
	 */
	public HoroscopeSoulInfo[] getHoroscopeSoulInfos(int level) {
		List<HoroscopeSoulInfo> horoscopeSoulInfoList = new ArrayList<HoroscopeSoulInfo>();
		for(HoroscopeTypeTemplate template : horoscopeTypeTemplates.values()){
			HoroscopeSoulInfo horoscopeSoulInfo = new HoroscopeSoulInfo();
			horoscopeSoulInfo.setDesc(template.getDesc());
			horoscopeSoulInfo.setHoroscopeSoulType(template.getId());
			horoscopeSoulInfo.setLevel(template.getLevel());
			if(level >= template.getLevel()){
				horoscopeSoulInfo.setOpen(true);
			}
			else{
				horoscopeSoulInfo.setOpen(false);
			}
			horoscopeSoulInfoList.add(horoscopeSoulInfo);
		}
		return horoscopeSoulInfoList.toArray(new HoroscopeSoulInfo[0]);
	}
}
