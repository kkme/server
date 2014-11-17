package com.hifun.soul.gameserver.pay;

import java.util.List;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.core.context.ApplicationContext;
import com.hifun.soul.core.msg.BaseSessionMessage;
import com.hifun.soul.core.msg.MessageType;
import com.hifun.soul.core.msg.SysInternalMessage;
import com.hifun.soul.gamedb.callback.IMainThreadDBCallback;
import com.hifun.soul.gamedb.entity.QQRechargeEntity;
import com.hifun.soul.gamedb.mock.QQRechargeConfirmResult;
import com.hifun.soul.gameserver.common.GameServerAssist;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.auth.local.LocalHandlerFactory;
import com.hifun.soul.gameserver.rechargetx.IRechargeHandler;

@Component
public class PGSendItem extends BaseSessionMessage<PSClientSession> {
	private Logger logger = Loggers.LOCAL_LOGGER;
	private String jsonProperties;
	private int confirmTimes;
	private static int needConfirmTimes = 2;
	private static int delayTime = 5000;

	@Override
	public void execute() {
		System.out.println(this.jsonProperties);
		final JSONObject request = JSONObject.fromObject(this.jsonProperties);
		final long humanGUID = Long.valueOf(request.getString("humanGUID"));
		// FIXME: cfh 要考虑并发，所以这个要包成一个消息投递给场景。看此用户是否在线，如果在线直接发放。如果不在线记录，等下次登录发放。之类的。
		GameServerAssist.getGameWorld().putMessage(new SysInternalMessage() {
			
			@Override
			public void execute() {
				LocalHandlerFactory localHandlerFactory = ApplicationContext
						.getApplicationContext().getBean(LocalHandlerFactory.class);
				final IRechargeHandler rechargeHandler = localHandlerFactory.getRechargeHandler(
						GameServerAssist.getGameServerConfig().getLocalType());
				if(rechargeHandler == null){
					logger.error("PGSendItem : can not find RechargeHandler!");
					return;
				}
				// 执行充值确认操作
				rechargeHandler.doRechargeConfirm(humanGUID, request.toString(), new IMainThreadDBCallback<List<?>>(){

					@Override
					public void onSucceed(List<?> result) {
						dbCallBackHandle(result, rechargeHandler, humanGUID, request);
					}

					@Override
					public void onFailed(String errorMsg) {
						// 日志记录一下
						logger.error(String.format("QQRechargeConfirmFailed:%s", errorMsg));
					}
					
				});
			}
		});
	}

	@Override
	public short getType() {
		return MessageType.PG_SEND_ITEM;
	}

	@Override
	protected boolean readImpl() {
		this.jsonProperties = this.readString();
		return true;
	}

	@Override
	protected boolean writeImpl() {
		throw new UnsupportedOperationException();
	}

	public String getJsonProperties() {
		return jsonProperties;
	}

	public void setJsonProperties(String jsonProperties) {
		this.jsonProperties = jsonProperties;
	}
	
	private void dbCallBackHandle(List<?> result, final IRechargeHandler rechargeHandler, final long humanGUID, final JSONObject request){
		if(result != null
				&& result.size() >= 1){
			QQRechargeConfirmResult confirmResult = (QQRechargeConfirmResult)result.get(0);
			if(confirmResult.isSuccess()){
				// 判断玩家是否在线
				Human human = GameServerAssist.getGameWorld().getSceneHumanManager().getHumanByGuid(humanGUID);
				// 充值成功
				if(human != null){
					// 充值物品信息(id*price*数量)
					String payItem = request.getString("payitem");
					QQRechargeEntity rechargeEntity = (QQRechargeEntity) confirmResult.getObject();
					rechargeEntity.setSended(true);
					GameServerAssist.getDataService().update(rechargeEntity);
					if(!human.getHumanRechargeTxManager().giveMoneyByRecharge(payItem)){
						logger.error(
								String.format("PGSendItem onSucceed: give money by recharge error! humanGUID:%s,rechargeEntityId:%s",
										human.getHumanGuid(),rechargeEntity.getId()));
					}
				}
			}
			else if(confirmResult.isNeedConfirmAgain()){
				if(confirmTimes < PGSendItem.needConfirmTimes){
					confirmTimes++;
					// 由于某些原因没有确认成功，需要延迟发起确认
					GameServerAssist.getGameWorld().scheduleOnece(new SysInternalMessage(){
						@Override
						public void execute() {
							rechargeHandler.doRechargeConfirm(humanGUID, request.toString(), new IMainThreadDBCallback<List<?>>(){

								@Override
								public void onSucceed(
										List<?> result) {
									dbCallBackHandle(result, rechargeHandler, humanGUID, request);
								}

								@Override
								public void onFailed(
										String errorMsg) {
									logger.error("QQRechargeConfirmFailed again error!");
								}
								
							});
							
						}
						
					}, delayTime);
				}
			}
			else{
				logger.error(String.format("QQRechargeConfirmFailed! errorCode=%s", confirmResult.getResultCode()));
			}
		}
		else{
			logger.error("QQRechargeConfirmFailed!");
		}
	}

}
