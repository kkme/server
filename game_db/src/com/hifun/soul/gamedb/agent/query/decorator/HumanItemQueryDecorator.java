package com.hifun.soul.gamedb.agent.query.decorator;

import java.util.ArrayList;
import java.util.List;
import com.google.protobuf.InvalidProtocolBufferException;
import com.hifun.soul.gamedb.agent.query.IXQLExecutor;
import com.hifun.soul.gamedb.logger.Loggers;
import com.hifun.soul.proto.data.entity.Entity.HumanItem;

/**
 * 查询角色身上的物品
 * @author magicstone
 *
 */
public class HumanItemQueryDecorator extends XQLExecutorDecorator {

	public HumanItemQueryDecorator(IXQLExecutor wrappedExecutor) {
		super(wrappedExecutor);		
	}
	@Override
	public List<?> execute(String queryName, String[] params, Object[] values) {
		List<?> humanItemList = this.wrappedExecutor.execute(queryName, params,
				values);
		List<HumanItem> humanItems = new ArrayList<HumanItem>();
		for (int i = 0; i < humanItemList.size(); i++) {
			try {
				HumanItem humanItem = HumanItem
						.parseFrom(((byte[]) humanItemList.get(i)));
				humanItems.add(humanItem);
			} catch (InvalidProtocolBufferException e) {
				Loggers.DB_MAIN_LOGGER.error("Deserializable error!", e);
				continue;
			}
		}
		return humanItems;
	}

}
