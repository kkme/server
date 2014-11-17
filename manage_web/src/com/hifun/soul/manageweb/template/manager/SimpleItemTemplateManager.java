package com.hifun.soul.manageweb.template.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.IInitializeRequired;
import com.hifun.soul.core.template.TemplateService;
import com.hifun.soul.manageweb.template.SimpleItemTemplate;

@Scope("singleton")
@Component
public class SimpleItemTemplateManager implements IInitializeRequired {
	private static Map<Integer,SimpleItemTemplate> simpleItemTemplates;
	private static Map<String,List<SimpleItemTemplate>> categorySimpleItemTemplates = new HashMap<String, List<SimpleItemTemplate>>();
	@Autowired
	private TemplateService templateService;
	@Override
	public void init() {
		simpleItemTemplates = templateService.getAll(SimpleItemTemplate.class);
		for(SimpleItemTemplate template : simpleItemTemplates.values()){
			if(categorySimpleItemTemplates.containsKey(template.getCategory())){
				categorySimpleItemTemplates.get(template.getCategory()).add(template);
			}else{
				List<SimpleItemTemplate> list = new ArrayList<SimpleItemTemplate>();
				list.add(template);
				categorySimpleItemTemplates.put(template.getCategory(), list);
			}
		}
		
	}
	
	public Collection<String> getItemCategories(){
		return categorySimpleItemTemplates.keySet();
	}

	public Map<Integer,SimpleItemTemplate> getSimpleItemTemplates(){
		return simpleItemTemplates;
	}
	
	
	public  List<SimpleItemTemplate> getSimpleItemTemplateByCategory(String category){
		return categorySimpleItemTemplates.get(category);
	}
}
