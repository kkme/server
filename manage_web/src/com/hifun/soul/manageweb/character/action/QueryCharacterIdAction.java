package com.hifun.soul.manageweb.character.action;

import org.apache.struts2.ServletActionContext;

import com.google.protobuf.ServiceException;
import com.hifun.soul.manageweb.Managers;
import com.hifun.soul.manageweb.action.BaseAction;
import com.hifun.soul.proto.services.Services.QueryHumanIdByNameRequest;
import com.hifun.soul.proto.services.Services.QueryHumanIdByNameResponse;

public class QueryCharacterIdAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7162037886895829956L;
	/**
	 * 玩家的id
	 */
	private long charId;	
	
	private String charName;
	
	public String getCharName() {
		return charName;
	}

	public void setCharName(String charName) {
		this.charName = charName;
	}


	public String getCharId() {
		return String.valueOf(charId);
	}

	public void setCharId(String charIdStr) {
		this.charId = Long.parseLong(charIdStr);		
	}	

	public String queryCharacterIdByName() throws ServiceException{
		charName = ServletActionContext.getRequest().getParameter("charName");
		if(charName == null || charName.length()==0){
			return "failed";
		}
		QueryHumanIdByNameResponse response = 
		Managers.getServiceManager().getCharacterService()
		.queryHumanIdByName(null,QueryHumanIdByNameRequest.newBuilder().setName(charName).build());
		charId = response.getCharId();		
		return "success";
	}

}
