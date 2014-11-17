package com.hifun.soul.gamedb.agent.query.decorator;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.core.orm.IDBService;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.agent.query.IXQLExecutor;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.entity.HumanQuestionEntity;
import com.hifun.soul.gamedb.entity.HumanMatchBattleEntity;

/**
 * 登录时候查询所有角色数据的装饰器;<br>
 * 提出此类的原因有：<br>
 * 1. 在角色登录时候有些角色数据不在t_human表里, 是分表存储的, 如果需要在登录进场景之前就构造好数据需要在此构造好数据;<br>
 * 2. 在角色登录的时候会调用{@code Human#onLoad}方法, 切记不要在相关的业务XXManager的onLoad里再次调用数据服务,
 * 这样可能会引发线程问题;<br>
 * <p>
 * 具体的做法就是:(除去不需要在登录时候构建的数据, 比如有些业务数据可能在客户端第一次打开面板的时候请求, 此时可以根据情况使用延时加载)<br>
 * 1. 在查询出HumanEntity以后, 把需要查询的其它业务数据查询出来, 然后装配到HumanEntity上;<br>
 * 2. 业务数据onLoad时的数据直接从HumanEntity中读取;<br>
 * 
 * @author crazyjohn
 * 
 */
public class HumanQueryDecorator extends XQLExecutorDecorator {
	protected IDBService service;

	public HumanQueryDecorator(IXQLExecutor wrappedExecutor, IDBService service) {
		super(wrappedExecutor);
		this.service = service;
	}

	@Override
	public List<?> execute(String queryName, String[] params, Object[] values) {
		List<HumanEntity> result = new ArrayList<HumanEntity>();
		List<?> baseResult = super.execute(queryName, params, values);
		if (baseResult == null || baseResult.isEmpty()) {
			return result;
		}
		HumanEntity entity = (HumanEntity) baseResult.get(0);
		
		// TODO: server guys; 在此处写需要构建的其它业务数据;
		// eg: List<ItemEntity> items = service.query(queryName, params,
		// values);
		// humanEntity.setItems(items);
		
		List<?> questionResult = service.findByNamedQueryAndNamedParam(DataQueryConstants.QUERY_QUESTION_INFO_BY_HUMAN_ID, new String[]{"humanId"},new Object[]{entity.getId()});
		if(questionResult!=null && !questionResult.isEmpty()){
			HumanQuestionEntity questionEntity = (HumanQuestionEntity) questionResult.get(0);
			entity.setQuestionEntity(questionEntity);
		}
		List<?> matchBattleRole = service.findByNamedQueryAndNamedParam(DataQueryConstants.QUERY_MATCH_BATTLE_ROLE_BY_HUMAN_ID, new String[]{"humanId"},new Object[]{entity.getId()});
		if(matchBattleRole!=null && !matchBattleRole.isEmpty()){
			HumanMatchBattleEntity matchBattleRoleEntity = (HumanMatchBattleEntity) matchBattleRole.get(0);
			entity.setMatchBattleEntity(matchBattleRoleEntity);
		}
		result.add(entity);
		return result;
	}

}
