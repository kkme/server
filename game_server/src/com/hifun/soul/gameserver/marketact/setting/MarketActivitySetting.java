package com.hifun.soul.gameserver.marketact.setting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.entity.MarketActivitySettingEntity;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.marketact.MarketActType;

/**
 * 运营活动设置
 * @author magicstone
 *
 */
@Component
@Scope("singleton")
public class MarketActivitySetting {
	
	private Map<Integer,MarketActivitySettingEntity> settings = new HashMap<Integer,MarketActivitySettingEntity>();

	public void loadSettings(IDBService dbService){
		settings.clear();
		List<?> result = dbService.findByNamedQuery(DataQueryConstants.QUERY_MARKET_ACTIVITY_SETTING);
		if(result == null || result.isEmpty()){
			return;
		}
		for(Object obj : result){
			MarketActivitySettingEntity entity = (MarketActivitySettingEntity)obj;
			settings.put(entity.getType(), entity);
		}
	}

	/**
	 * 判断某个运营活动是否可用
	 * @param type
	 * @param roleLevel
	 * @param vipLevel
	 * @return
	 */
	public boolean isEnable(MarketActType type, int roleLevel,int vipLevel){
		int typeKey = type.getIndex();
		if(!settings.containsKey(typeKey)){
			return false;
		}
		MarketActivitySettingEntity setting = settings.get(typeKey);
		if(!setting.isEnable()){
			return false;
		}
		if(setting.getRoleLevel()>roleLevel){
			return false;
		}
		if(setting.getVipLevel()>vipLevel){
			return false;
		}
		return true;
	}
	
	/**
	 * 更新某个运营活动的设置
	 * @param type
	 * @param isEnable
	 * @param roleLevel
	 * @param vipLevel
	 */
	public boolean updateSetting(int type,boolean isEnable, int roleLevel,int vipLevel){
		if(!settings.containsKey(type)){
			return false;
		}
		MarketActivitySettingEntity setting = settings.get(type);
		if(setting.isEnable() == isEnable && setting.getRoleLevel() == roleLevel && setting.getVipLevel() == vipLevel){
			return true;
		}
		setting.setEnable(isEnable);
		setting.setRoleLevel(roleLevel);
		setting.setVipLevel(vipLevel);
		GameServerAssist.getDataService().update(setting);
		return true;
	}
}
