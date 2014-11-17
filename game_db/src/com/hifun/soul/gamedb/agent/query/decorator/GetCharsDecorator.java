package com.hifun.soul.gamedb.agent.query.decorator;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.InvalidProtocolBufferException;
import com.hifun.soul.common.auth.LoginChar;
import com.hifun.soul.gamedb.agent.query.IXQLExecutor;
import com.hifun.soul.gamedb.logger.Loggers;
import com.hifun.soul.proto.data.entity.Entity.HumanBaseProperties;

/**
 * 登录查询玩家的所有角色;
 * 
 * @author crazyjohn
 * 
 */
public class GetCharsDecorator extends XQLExecutorDecorator {
	public GetCharsDecorator(IXQLExecutor wrappedExecutor) {
		super(wrappedExecutor);
	}

	@Override
	public List<?> execute(String queryName, String[] params, Object[] values) {
		List<?> basePropsList = this.wrappedExecutor.execute(queryName, params,
				values);
		List<LoginChar> loginChars = new ArrayList<LoginChar>();
		for (int i = 0; i < basePropsList.size(); i++) {
			try {
				HumanBaseProperties props = HumanBaseProperties
						.parseFrom(((byte[]) basePropsList.get(i)));

				LoginChar oneChar = new LoginChar();
				oneChar.setHumanGuid(props.getHumanGuid());
				oneChar.setCoins(props.getCoins());
				oneChar.setEnergy(props.getEnergy());
				oneChar.setHomeLevel(props.getHomeLevel());
				oneChar.setLevel(props.getLevel());
				oneChar.setName(props.getName());
				oneChar.setOccupation(props.getOccupation());
				loginChars.add(oneChar);
			} catch (InvalidProtocolBufferException e) {
				Loggers.DB_MAIN_LOGGER.error("Deserializable error!", e);
				continue;
			}
		}
		return loginChars;
	}

}
