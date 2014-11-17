package com.hifun.soul.manageweb.mail.action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONSerializer;

import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.manageweb.action.BaseAction;
import com.hifun.soul.manageweb.common.SimpleItem;
import com.hifun.soul.manageweb.template.SimpleItemTemplate;
import com.hifun.soul.manageweb.template.manager.SimpleItemTemplateManager;

public class QuerySimpleItemsAction extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4285741760245114128L;
	
	private List<SimpleItem> itemList;
	private String itemJsonResult = "";
	
	private List<String> categories;
	private String categoryJsonResult = "";
	
	public String getItems() {
		return itemJsonResult;
	}
	
	public String getCategories(){
		return categoryJsonResult;
	}
	

	public String querySimpleConsumeItems(){		
		String categoryParam = ServletActionContext.getRequest().getParameter("category");
		List<SimpleItemTemplate> itemTemplates = ApplicationContext.getApplicationContext()
				.getBean(SimpleItemTemplateManager.class).getSimpleItemTemplateByCategory(categoryParam);
		if(itemTemplates != null){			
			itemList = new ArrayList<SimpleItem>();		
			for(SimpleItemTemplate template : itemTemplates){
				SimpleItem item = new SimpleItem();
				item.setItemId(template.getId());
				item.setItemName(template.getName());
				item.setItemDesc(template.getDesc());
				item.setCategory(template.getCategory());
				itemList.add(item);
			}
			itemJsonResult = JSONSerializer.toJSON(itemList).toString();
		}
		return "success";
	}
	
	public String querySimpleItemCategories(){		
		Collection<String> categoryList = ApplicationContext.getApplicationContext()
				.getBean(SimpleItemTemplateManager.class).getItemCategories();
		if(categoryList != null){			
			categories = new ArrayList<String>();		
			categories.addAll(categoryList);
			categoryJsonResult = JSONSerializer.toJSON(categories).toString();
		}
		return "success";
	}
}
