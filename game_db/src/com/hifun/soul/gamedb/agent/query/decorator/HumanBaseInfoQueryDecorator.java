package com.hifun.soul.gamedb.agent.query.decorator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.gamedb.agent.HumanAgent;
import com.hifun.soul.gamedb.agent.HumanCacheData;
import com.hifun.soul.gamedb.agent.query.IXQLExecutor;
import com.hifun.soul.gamedb.cache.converter.HumanCacheToEntityConverter;
import com.hifun.soul.gamedb.entity.HumanEntity;
import com.hifun.soul.gamedb.logger.Loggers;
import com.hifun.soul.proto.data.entity.Entity.HumanBaseProperties;
import com.hifun.soul.proto.data.entity.Entity.HumanOtherProperties;

/**
 * 装饰玩家战斗需要的信息;
 * 
 * @author crazyjohn
 * 
 */
public class HumanBaseInfoQueryDecorator extends XQLExecutorDecorator {
	private HumanAgent humanAgent;
	private HumanCacheToEntityConverter converter = new HumanCacheToEntityConverter();
	public HumanBaseInfoQueryDecorator(IXQLExecutor wrappedExecutor,HumanAgent humanAgent) {
		super(wrappedExecutor);
		this.humanAgent = humanAgent;
	}

	@Override
	public List<?> execute(String queryName, String[] params, Object[] values) {
		List<HumanEntity> result = new ArrayList<HumanEntity>();
		HumanCacheData cache = humanAgent.getFormCache((Long)values[0]);
		if (cache != null) {
			result.add(converter.convert(cache));
			return result;
		}		
		List<?> baseResult = wrappedExecutor.execute(queryName, params, values);		
		if (baseResult == null || baseResult.isEmpty()) {
			return result;
		}

		Object[] battleInfo = (Object[]) baseResult.get(0);
		try {
			
			HumanBaseProperties baseProps = HumanBaseProperties
					.parseFrom(((byte[]) battleInfo[0]));
			HumanOtherProperties otherProps = HumanOtherProperties
					.parseFrom(((byte[]) battleInfo[1]));
			
			HumanEntity entity = new HumanEntity();
			entity.setId(baseProps.getHumanGuid());
			entity.setName(baseProps.getName());
			entity.getBuilder().setBaseProperties(baseProps);
			entity.getBuilder().setOtherProperties(otherProps);
			entity.setHumanItems(((byte[]) battleInfo[2]));
			entity.setHumanHoroscope(((byte[]) battleInfo[3]));
			result.add(entity);
		} catch (IOException e) {
			Loggers.DB_MAIN_LOGGER.error("Deserializable error!", e);
		}

		return result;
	}

}
