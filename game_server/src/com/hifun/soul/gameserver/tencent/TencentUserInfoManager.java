package com.hifun.soul.gameserver.tencent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.core.orm.IEntity;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.cache.CacheEntry;
import com.hifun.soul.gamedb.cache.CacheManager;
import com.hifun.soul.gamedb.cache.ICachableComponent;
import com.hifun.soul.gamedb.entity.TencentUserInfoEntity;
import com.hifun.soul.gamedb.service.DataService;
import com.hifun.soul.gameserver.tencent.converter.TencentUserInfoConverter;

@Scope("singleton")
@Component
public class TencentUserInfoManager implements ICachableComponent {
	private Map<Long,TencentUserInfo> allUsers = new HashMap<Long, TencentUserInfo>();
	private TencentUserInfoConverter converter = new TencentUserInfoConverter();
	private CacheEntry<Long, IEntity> cache = new CacheEntry<Long, IEntity>();
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private DataService dataService;
	public void load(IDBService dbService){
		List<?> result = dbService.findByNamedQuery(DataQueryConstants.QUERY_ALL_TENCENT_USER_INFO);
		if(result == null || result.isEmpty()){
			return;
		}
		for(Object obj : result){
			TencentUserInfoEntity entity = (TencentUserInfoEntity)obj;
			TencentUserInfo userInfo = converter.reverseConvert(entity);
			allUsers.put(userInfo.getId(), userInfo);
		}
		cacheManager.registerOtherCachableComponent(this);
	}
	
	public void updateTencentUserInfo(TencentUserInfo txUserInfo){
		if(txUserInfo == null){
			return;
		}
		// 没有openid标明不是tencent平台
		if(txUserInfo.getOpenId()==null || txUserInfo.getOpenId().trim().length()==0){
			return;
		}
		if(allUsers.containsKey(txUserInfo.getId())){
			allUsers.put(txUserInfo.getId(), txUserInfo);
			cache.addUpdate(txUserInfo.getId(), converter.convert(txUserInfo));
		}else{
			allUsers.put(txUserInfo.getId(), txUserInfo);
			dataService.insert(converter.convert(txUserInfo));
		}		
	}
	
	public TencentUserInfo getTencentUserInfo(long humanId){
		return allUsers.get(humanId);
	}

	@Override
	public List<IEntity> getUpdateEntities() {
		return cache.getAllUpdateData();
	}

	@Override
	public List<IEntity> getDeleteEntities() {
		return cache.getAllDeleteData();
	}
}
