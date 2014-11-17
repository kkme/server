package com.hifun.soul.gameserver.name;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.core.util.MathUtils;
import com.hifun.soul.gameserver.name.template.FemaleSecondNameTemplate;
import com.hifun.soul.gameserver.name.template.FirstNameTemplate;
import com.hifun.soul.gameserver.name.template.MaleSecondNameTemplate;
import com.hifun.soul.gameserver.role.Gender;

@Scope("singleton")
@Component
public class NameTemplateManager implements IInitializeRequired{
	private Map<Integer,FirstNameTemplate> firstNameTemplates;
	private Map<Integer,MaleSecondNameTemplate> maleSecondNameTemplates;
	private Map<Integer,FemaleSecondNameTemplate> femaleSecondNameTemplates;
	
	@Autowired
	private TemplateService templateService;
	@Override
	public void init() {
		firstNameTemplates = templateService.getAll(FirstNameTemplate.class);
		maleSecondNameTemplates = templateService.getAll(MaleSecondNameTemplate.class);
		femaleSecondNameTemplates = templateService.getAll(FemaleSecondNameTemplate.class);
	}
	
	/**
	 * 获取随机姓名
	 * 
	 * @param sex 1为男性，2为女性,其他为未知
	 * @return
	 */
	public String getRandomName(int sex){		
		String secondName = "";
		if(sex==Gender.MALE.getIndex()){
			int secondNameKey = MathUtils.random(1, maleSecondNameTemplates.size());
			secondName = maleSecondNameTemplates.get(secondNameKey).getSecondName();
		}
		else if(sex==Gender.FEMALE.getIndex()){
			int secondNameKey  = MathUtils.random(1, femaleSecondNameTemplates.size());
			secondName = femaleSecondNameTemplates.get(secondNameKey).getSecondName();
		}
		else{
			Gender gender =  MathUtils.random(Gender.class);
			return  getRandomName(gender.getIndex());
		}
		int firstNameKey = MathUtils.random(1, firstNameTemplates.size());
		return firstNameTemplates.get(firstNameKey).getFirstName()+secondName;
	}

}
