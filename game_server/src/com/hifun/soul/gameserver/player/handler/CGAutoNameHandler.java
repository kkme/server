package com.hifun.soul.gameserver.player.handler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.injection.IMessageHandlerWithType;
import com.hifun.soul.gamedb.agent.query.DataQueryConstants;
import com.hifun.soul.gamedb.callback.IDBCallback;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.name.NameTemplateManager;
import com.hifun.soul.gameserver.player.Player;
import com.hifun.soul.gameserver.player.msg.CGAutoName;
import com.hifun.soul.gameserver.player.msg.GCAutoName;
import com.hifun.soul.gameserver.role.Gender;
import com.hifun.soul.gameserver.role.Occupation;


@Component
public class CGAutoNameHandler implements IMessageHandlerWithType<CGAutoName> {
	@Autowired
	private NameTemplateManager nameManager;
	@Override
	public short getMessageType() {
		return MessageType.CG_AUTO_NAME;
	}

	@Override
	public void execute(CGAutoName message) {
		short gender = Gender.MALE.getIndex();
		if(message.getOccupation()==Occupation.MAGICIAN.getIndex()){
			gender = Gender.FEMALE.getIndex();
		}
		handleAutoName(message.getPlayer(),gender,0);
	}

	/**
	 * 处理自动命名，该方法会检查游戏中是否已经存在相同名字，若已存在，递归调用该方法重新生成新的名字返回客户端
	 * @param player
	 * @param gender
	 */
	private void handleAutoName(final Player player,short gender,int runtimes){
		final String roleName=nameManager.getRandomName(gender);
		runtimes++;
		if(runtimes>3){//若连续生成3次都被占用，直接返回
			GCAutoName gcMsg = new GCAutoName();
			gcMsg.setRoleName(roleName);
			player.sendMessage(gcMsg);
			return;
		}
		final short sex = gender;
		final int times = runtimes;
		GameServerAssist.getDataService().query(DataQueryConstants.QUERY_HUMAN_BY_NAME,
				new String[] { "name" }, new Object[] { roleName },
				new IDBCallback<List<?>>() {
					@Override
					public void onSucceed(List<?> result) {
						if(result==null || result.isEmpty()){
							GCAutoName gcMsg = new GCAutoName();
							gcMsg.setRoleName(roleName);
							player.sendMessage(gcMsg);
						}
						else{
							handleAutoName(player,sex,times);
						}
					}

					@Override
					public void onFailed(String errorMsg) {
												
					}
		});
	
	}
}
