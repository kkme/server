package com.hifun.soul.gameserver.foster.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.common.constants.LangConstants;
import com.hifun.soul.core.i18n.SysLangService;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.currency.CurrencyType;
import com.hifun.soul.gameserver.foster.FosterAttribute;
import com.hifun.soul.gameserver.foster.FosterCurrency;
import com.hifun.soul.gameserver.foster.FosterCurrencyType;
import com.hifun.soul.gameserver.foster.FosterMode;
import com.hifun.soul.gameserver.foster.FosterModeType;
import com.hifun.soul.gameserver.foster.template.FosterAttributeTemplate;
import com.hifun.soul.gameserver.foster.template.FosterCoinCostTemplate;
import com.hifun.soul.gameserver.foster.template.FosterCostTemplate;
import com.hifun.soul.gameserver.foster.template.FosterMaxTemplate;
import com.hifun.soul.gameserver.foster.template.FosterModeTemplate;

@Scope("singleton")
@Component
public class FosterTemplateManager implements IInitializeRequired {

	@Autowired
	private TemplateService templateService;
	@Autowired
	private SysLangService sysLangService;
	/** key:level1PropertyId,value:FosterAttributeTemplate */
//	private Map<Integer, FosterAttributeTemplate> attributeTemplates = new HashMap<Integer, FosterAttributeTemplate>();
	private Map<Integer, Map<Integer,FosterMaxTemplate>> fosterMaxTemplates = new HashMap<Integer, Map<Integer,FosterMaxTemplate>>();
	private Map<Integer, FosterModeTemplate> modeTemplates;	
	private Map<Integer, FosterCostTemplate> fosterCostTemplates;
	private Map<Integer, FosterCoinCostTemplate> fosterCoinCostTemplates;
	private Map<Integer, Map<Integer,FosterAttributeTemplate>> attributeTemplates = new HashMap<Integer, Map<Integer,FosterAttributeTemplate>>();
	private List<Integer> fosterAttributeId = new ArrayList<Integer>();
	private String coinName="";
	private String crystalName="";
	private String trainCoinName="";
	

	@Override
	public void init() {
		modeTemplates = templateService.getAll(FosterModeTemplate.class);
		Map<Integer, FosterAttributeTemplate> attrTemplates = templateService.getAll(FosterAttributeTemplate.class);
		for(FosterAttributeTemplate template : attrTemplates.values()){			
			if(!fosterAttributeId.contains(template.getLevel1PropertyId())){
				fosterAttributeId.add(template.getLevel1PropertyId());
			}
			if(attributeTemplates.containsKey(template.getLevel1PropertyId())){
				attributeTemplates.get(template.getLevel1PropertyId()).put(template.getFosterMode(), template);
			}else{
				Map<Integer,FosterAttributeTemplate> modeMap =  new HashMap<Integer, FosterAttributeTemplate>();
				modeMap.put(template.getFosterMode(), template);
				attributeTemplates.put(template.getLevel1PropertyId(), modeMap);
			}
		}
		fosterCostTemplates = templateService.getAll(FosterCostTemplate.class);
		fosterCoinCostTemplates = templateService.getAll(FosterCoinCostTemplate.class);
		Map<Integer, FosterMaxTemplate> maxTempaltes = templateService.getAll(FosterMaxTemplate.class);
		for(FosterMaxTemplate template : maxTempaltes.values()){
			if(fosterMaxTemplates.containsKey(template.getLevel1Property())){
				fosterMaxTemplates.get(template.getLevel1Property()).put(template.getHumanLevel(), template);
			}else{
				Map<Integer,FosterMaxTemplate> map = new HashMap<Integer, FosterMaxTemplate>();
				map.put(template.getHumanLevel(), template);
				fosterMaxTemplates.put(template.getLevel1Property(), map);
			}
		}
		coinName = sysLangService.read(LangConstants.COIN);
		crystalName = sysLangService.read(LangConstants.CRYSTAL);
		trainCoinName = sysLangService.read(LangConstants.TRAIN_COIN);
	}
	
	/**
	 * 获取培养类型
	 * @param humanLevel
	 * @param vipLevel
	 * @return
	 */
	public Map<Integer,FosterMode> getFosterMode(int humanLevel,int vipLevel){
		Map<Integer,FosterMode> result = new HashMap<Integer,FosterMode>();
		for(FosterModeTemplate template : modeTemplates.values()){
			if(template.getOpenVipLevel()<=vipLevel){
				FosterMode fosterMode = generateFosterMode(template,humanLevel);
				result.put(fosterMode.getId(), fosterMode);
			}
		}
		return result;
	}
	
	/**
	 * 生成培养类型业务实体
	 * @param modeTemplate
	 * @param humanLevel
	 * @return
	 */
	private FosterMode generateFosterMode(FosterModeTemplate modeTemplate,int humanLevel){
		int modeId = modeTemplate.getId();
		FosterMode fosterMode = new FosterMode();
		fosterMode.setId(modeId);
		fosterMode.setName(modeTemplate.getName());
		String desc = "";
		int costNum = 0;
		if(fosterCostTemplates.keySet().contains(modeId)){
			FosterCostTemplate costTemplate = fosterCostTemplates.get(modeId);
			fosterMode.setType(FosterModeType.SUPER_FOSTER.getIndex());
			costNum = costTemplate.getCostNum();
			fosterMode.setCostCurrencyNum(costNum);
			fosterMode.setCostCurrencyType(costTemplate.getCostCurrencyType());
			if(costTemplate.getCostCurrencyType() == CurrencyType.COIN.getIndex()){
				desc = costNum+coinName;
			}
			else if(costTemplate.getCostCurrencyType() == CurrencyType.CRYSTAL.getIndex()){
				desc = costNum+crystalName;
			}
		}
		else{
			fosterMode.setType(FosterModeType.NORMAL_FOSTER.getIndex());
			costNum = this.fosterCoinCostTemplates.get(humanLevel).getTrainCoinCostNum();
			fosterMode.setCostCurrencyNum(costNum);
			fosterMode.setCostCurrencyType(FosterCurrencyType.TRAIN_COIN.getIndex());
			desc = costNum+trainCoinName;
		}		
		fosterMode.setDesc(desc);
		return fosterMode;
	}
	
	
	/**
	 * 获取培养所需花费
	 * @param humanLevel
	 * @param modeId
	 * @return
	 */
	public FosterCurrency getFosterCost(int humanLevel,int modeId){
		FosterCurrency currency = new FosterCurrency();
		if(fosterCostTemplates.keySet().contains(modeId)){
			FosterCostTemplate costTemplate = fosterCostTemplates.get(modeId);
			currency.setNumber(costTemplate.getCostNum());
			currency.setType(FosterCurrencyType.indexOf(costTemplate.getCostCurrencyType()));
		}
		else{			
			int costNum = this.fosterCoinCostTemplates.get(humanLevel).getTrainCoinCostNum();
			currency.setNumber(costNum);
			currency.setType(FosterCurrencyType.TRAIN_COIN);
		}	
		return currency;
	}
	
	

	/**
	 * 获取可培养的属性(未设置最大值与当前值)
	 * @return
	 */
	public FosterAttribute[] getFosterAttributes(){
		FosterAttribute[] result = new FosterAttribute[attributeTemplates.size()];
		int index = 0;
		for(Integer level1Property : fosterAttributeId){
			FosterAttribute fosterAttr = new FosterAttribute();
			fosterAttr.setId(level1Property);			
			result[index] = fosterAttr;
			index++;
		}
		return result;
	}
	
	/**
	 * 获取培养模板
	 * @param modeId
	 * @return
	 */
	public FosterModeTemplate getFosterModeTemplate(int modeId){
		return modeTemplates.get(modeId);
	}
	
	public FosterMaxTemplate getFosterMaxTemplate(int level1Property,int humanLevel){
		if(fosterMaxTemplates.get(level1Property)==null){
			return null;
		}
		return fosterMaxTemplates.get(level1Property).get(humanLevel);
	}
	
	public int getFosterSuccessValue(int fosterMode,int level1Property){
		if(!attributeTemplates.containsKey(level1Property)){
			return 0;
		}
		if(!attributeTemplates.get(level1Property).containsKey(fosterMode)){
			return 0;
		}
		FosterAttributeTemplate tempalte = attributeTemplates.get(level1Property).get(fosterMode);
		return MathUtils.random(tempalte.getMinNumOfPerFoster(), tempalte.getMaxNumOfPerFoster());
	}

}
