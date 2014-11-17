package com.hifun.soul.gameserver.player.state;

import java.util.ArrayList;
import java.util.List;

import com.hifun.soul.core.msg.MessageType;

/**
 * 玩家状态机;
 * 
 * @author crazyjohn
 * 
 */
public enum PlayerState implements IPlayerState {
	/** 初始状态, 在没有网络回话建立之前 */
	NONE() {

		@Override
		public boolean canProcessMessage(short messageType) {
			return false;
		}

		@Override
		public boolean canTransferToState(IPlayerState target) {
			if (target == CONNECTED) {
				return true;
			}
			return false;
		}

		@Override
		public IPlayerState[] preStates() {
			return null;
		}

	},
	/** 建立网络回话 */
	CONNECTED() {

		@Override
		public boolean canProcessMessage(short messageType) {
			// 只处理登录消息
			if (messageType == MessageType.CG_PLAYER_LOGIN) {
				return true;
			}
			return false;
		}

		@Override
		public IPlayerState[] preStates() {
			return new IPlayerState[] { NONE };
		}
	},
	/** 已认证 */
	AUTHRONIZED() {

		@Override
		public boolean canProcessMessage(short messageType) {
			if (messageType == MessageType.CG_GET_CHAR_LIST
					|| messageType == MessageType.CG_CREATE_CHAR
					|| messageType == MessageType.CG_SELECT_CHAR) {
				return true;
			}
			return false;
		}

		@Override
		public IPlayerState[] preStates() {
			return new IPlayerState[] { CONNECTED };
		}

	},
	/** 正在进入游戏中 */
	ENTERING() {

		@Override
		public boolean canProcessMessage(short messageType) {
			if (messageType == MessageType.CG_ENTER_SCENE_READY) {
				return true;

			}
			return false;
		}

		@Override
		public IPlayerState[] preStates() {
			return new IPlayerState[] { AUTHRONIZED };
		}

	},
	/** 游戏中 */
	GAMEING() {
		@Override
		public boolean canProcessMessage(short messageType) {
			if (messageType != MessageType.CG_PLAYER_LOGIN
					&& messageType != MessageType.CG_GET_CHAR_LIST
					&& messageType != MessageType.CG_CREATE_CHAR
					&& messageType != MessageType.CG_SELECT_CHAR
					&& messageType != MessageType.CG_ENTER_SCENE_READY) {
				return true;
			}
			return false;
		}

		@Override
		public IPlayerState[] preStates() {
			return new IPlayerState[] { ENTERING, BATTLING, AUTOBATTLEING,
					HOSTING_BATTLING };
		}

	},
	/** 战斗中 */
	BATTLING() {
		private List<Short> types = new ArrayList<Short>();
		{
			// 注册战斗时候可以处理的消息
			types.add(MessageType.CG_NORMAL_ATTACK);
			types.add(MessageType.CG_ANIMATION_OVER);
			types.add(MessageType.CG_BATTLE_RESOURCE_LOADED);
			types.add(MessageType.CG_USE_SKILL);
			types.add(MessageType.CG_REQUEST_HOSTING_BATTLE);
			types.add(MessageType.CG_REQUEST_BATTLE_GIVE_UP);
			types.add(MessageType.CG_BATTLE_CHAT);
			types.add(MessageType.CG_RESET_CHESSBOARD_ANIMATION_OVER);
		}

		@Override
		public boolean canProcessMessage(short messageType) {
			// 处理战斗相关的消息;
			if (types.contains(messageType)) {
				return true;
			}
			return false;
		}

		@Override
		public IPlayerState[] preStates() {
			return new IPlayerState[] { GAMEING, HOSTING_BATTLING /** 可以从托管转到战斗 */
			};
		}

	},
	/** 扫荡中 */
	AUTOBATTLEING() {
		@Override
		public boolean canProcessMessage(short messageType) {
			if (messageType == MessageType.CG_CANCEL_ELITE_AUTO_BATTLE
					|| messageType == MessageType.CG_CANCEL_STAGE_AUTO_BATTLE) {
				return true;
			}
			return false;
		}

		@Override
		public IPlayerState[] preStates() {
			return new IPlayerState[] { GAMEING };
		}

	},
	/** 托管战斗中 */
	HOSTING_BATTLING() {
		private List<Short> types = new ArrayList<Short>();
		{
			// 注册托管战斗时候可以处理的消息
			types.add(MessageType.CG_REQUEST_BATTLE_GIVE_UP);
			types.add(MessageType.CG_BATTLE_CHAT);
			types.add(MessageType.CG_ANIMATION_OVER);
			types.add(MessageType.CG_RESET_CHESSBOARD_ANIMATION_OVER);
			types.add(MessageType.CG_REQUEST_CANCEL_HOSTING_BATTLE);
		}

		@Override
		public boolean canProcessMessage(short messageType) {
			// 处理托管战斗相关的消息;
			if (types.contains(messageType)) {
				return true;
			}
			return false;
		}

		@Override
		public IPlayerState[] preStates() {
			// 只可以从战斗状态转化过去
			return new IPlayerState[] { BATTLING };
		}

	},
	/** 退出中 */
	EXITING() {

		@Override
		public boolean canProcessMessage(short messageType) {
			return false;
		}

		@Override
		public IPlayerState[] preStates() {
			return new IPlayerState[] { CONNECTED, AUTHRONIZED, ENTERING,
					GAMEING };
		}

	};

	@Override
	public boolean canTransferToState(IPlayerState target) {
		PlayerState playerTargetState = (PlayerState) target;
		for (IPlayerState state : playerTargetState.preStates()) {
			if (this == state) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 前置状态;<br>
	 * 标记着当前状态可以由哪些状态转换而来;
	 * 
	 * @return
	 */
	public abstract IPlayerState[] preStates();

	public static boolean isInBattleState(IPlayerState state) {
		return state == PlayerState.BATTLING
				|| state == PlayerState.HOSTING_BATTLING;
	}
}
