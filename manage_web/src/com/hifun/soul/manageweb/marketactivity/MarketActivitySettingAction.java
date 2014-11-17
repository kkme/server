package com.hifun.soul.manageweb.marketactivity;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.ServiceException;
import com.hifun.soul.manageweb.Managers;
import com.hifun.soul.manageweb.action.BaseAction;
import com.hifun.soul.proto.services.Services.MarketActRpcService;
import com.hifun.soul.proto.services.Services.MarketActivitySetting;
import com.hifun.soul.proto.services.Services.QueryMarketActListRequest;
import com.hifun.soul.proto.services.Services.QueryMarketActListResponse;
import com.hifun.soul.proto.services.Services.UpdateMarketActSettingRequest;
import com.hifun.soul.proto.services.Services.UpdateMarketActSettingResponse;
import com.opensymphony.xwork2.ActionContext;

/**
 * 运营活动设置
 * @author magicstone
 *
 */
public class MarketActivitySettingAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9093151752846996016L;
	
	private List<MarketActivitySetting.Builder> settings = new ArrayList<MarketActivitySetting.Builder>();
	
	private boolean enable = false;
	private int marketActType;
	private int roleLevel;
	private int vipLevel;

	public List<MarketActivitySetting.Builder> getSettings() {
		return settings;
	}

	public void setSettings(List<MarketActivitySetting.Builder> settings) {
		this.settings = settings;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public int getMarketActType() {
		return marketActType;
	}

	public void setMarketActType(int marketActType) {
		this.marketActType = marketActType;
	}

	public int getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(int roleLevel) {
		this.roleLevel = roleLevel;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	
	@SuppressWarnings("unchecked")
	public String updateMarketActivitySetting() throws ServiceException{
		setSettings((List<MarketActivitySetting.Builder>) ActionContext.getContext().getSession().get("allSettings"));
		if(marketActType<=0){
			return "failed";
		}
		MarketActRpcService.BlockingInterface marketActService = Managers.getServiceManager().getMarketActService();
		UpdateMarketActSettingRequest.Builder request = UpdateMarketActSettingRequest.newBuilder();
		request.setEnable(enable);
		request.setMarketActType(marketActType);
		request.setRoleLevel(roleLevel);
		request.setVipLevel(vipLevel);
		UpdateMarketActSettingResponse response = marketActService.updateMarketActSetting(null,request.build());
		if(!response.getResult()){
			return "failed";
		}
		for(MarketActivitySetting.Builder setting : settings){
			if(setting.getMarketActType() == marketActType){
				setting.setEnable(enable);
				setting.setRoleLevel(roleLevel);
				setting.setVipLevel(vipLevel);
			}
		}
		return "success";
	}
	
	public String queryAllMarketActivitySettings() throws ServiceException{
		MarketActRpcService.BlockingInterface marketActService =Managers.getServiceManager().getMarketActService();
		QueryMarketActListResponse response = marketActService.queryMarketActList(null, QueryMarketActListRequest.newBuilder().build());
		settings.clear();
		for(MarketActivitySetting setting : response.getSettingsList()){
			settings.add(setting.toBuilder());
		}
		ActionContext.getContext().getSession().put("allSettings", settings);
		return "success";
	}

}
