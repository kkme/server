package com.hifun.soul.gameserver.training;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.gameserver.training.template.TrainingTemplate;
import com.hifun.soul.gameserver.training.template.TrainingTypeTemplate;
import com.hifun.soul.gameserver.training.template.VipTrainingTypeTemplate;

/**
 * 训练场相关模板管理类
 * 
 * @author magicstone
 *
 */
@Scope("singleton")
@Component
public class TrainingTemplateManager implements IInitializeRequired {

	private Map<Integer,TrainingTypeTemplate> normalTrainingTypes ;
	private Map<Integer,VipTrainingTypeTemplate> vipTrainingTypes ;
	private Map<Integer,TrainingTemplate> trainingTemplates  = new HashMap<Integer,TrainingTemplate>();
	@Autowired
	private TemplateService templateService;
	@Override
	public void init() {
		normalTrainingTypes = templateService.getAll(TrainingTypeTemplate.class);
		vipTrainingTypes = templateService.getAll(VipTrainingTypeTemplate.class);
		Map<Integer,TrainingTemplate> trainTemplates = templateService.getAll(TrainingTemplate.class);
		for(Integer id : trainTemplates.keySet()){
			trainingTemplates.put(trainTemplates.get(id).getRoleLevel(), trainTemplates.get(id));
		}
	}
	/**
	 * 获取所有普通训练类型
	 * 
	 * @return
	 */
	public Map<Integer,TrainingTypeTemplate> getNormalTrainingTypes(){
		return normalTrainingTypes;
	}
	
	/**
	 * 获取所有VIP训练类型
	 * 
	 * @return
	 */
	public Map<Integer,VipTrainingTypeTemplate> getVipTrainingTypes(){
		return vipTrainingTypes;
	}
	
	/**
	 * 根据训练Id获取普通训练的模板
	 * 
	 * @param trainingId
	 * @return
	 */
	public TrainingTypeTemplate getNormalTrainingType(int trainingId){
		return normalTrainingTypes.get(trainingId);
	}
	
	/**
	 * 根据训练Id获取VIP训练模板
	 * 
	 * @param trainingId
	 * @return
	 */
	public VipTrainingTypeTemplate getVipTrainingType(int trainingId){
		return vipTrainingTypes.get(trainingId);
	}
	
	/**
	 * 根据角色等级获取相应的训练花费和所得收益模板
	 * 
	 * @param roleLevel
	 * @return
	 */
	public TrainingTemplate getTrainingTemplate(int roleLevel){
		return trainingTemplates.get(roleLevel);
	}
}
