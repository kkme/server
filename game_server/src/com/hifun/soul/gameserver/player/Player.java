package com.hifun.soul.gameserver.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import com.hifun.soul.common.IHeartBeatable;
import com.hifun.soul.common.auth.LoginChar;
import com.hifun.soul.common.constants.Loggers;
import com.hifun.soul.common.constants.SharedConstants;
import com.hifun.soul.common.constants.SystemMessageType;
import com.hifun.soul.core.msg.IMessage;
import com.hifun.soul.core.msg.MessageQueue;
import com.hifun.soul.core.server.MessageHandlerManager;
import com.hifun.soul.core.util.Assert;
import com.hifun.soul.core.util.MessageTypeUtil;
import com.hifun.soul.gamedb.PlayerPermissionType;
import com.hifun.soul.gameserver.common.msg.GCSystemMessage;
import com.hifun.soul.gameserver.human.Human;
import com.hifun.soul.gameserver.player.state.IPlayerState;
import com.hifun.soul.gameserver.player.state.PlayerState;
import com.hifun.soul.gameserver.startup.GameClientSession;

/**
 * 玩家的抽象;<br>
 * FIXME: crazyjohn currentState是否会有并发的访问？？？如果有的话需要同步
 * 
 * @author crazyjohn
 * 
 */
public class Player implements IHeartBeatable {

	/** 玩家对应的session */
	private GameClientSession session;
	/** 玩家账号id; 保证可视性 */
	private volatile long passportId;
	/** 玩家账号 */
	private volatile String account;
	/** 玩家对应的角色 */
	private Human human;
	/** 玩家对应的消息队列 */
	private MessageQueue msgQueue;
	/** 玩家的当前状态 , 初始狀態為NULL */
	private IPlayerState currentState = PlayerState.NONE;
	private String clientIp;
	/** 角色的权限 */
	private PlayerPermissionType permissionType = PlayerPermissionType.NORMAL_PLAYER;
	/** 角色的LoginChars */
	private List<LoginChar> loginChars = new ArrayList<LoginChar>();
	/** 当前的LoginChar */
	private LoginChar currentLoginChar;
	private MessageHandlerManager handler = new MessageHandlerManager();	
	/** 平台数据信息 */
	private Properties localProperties;
	private String plateformJosnValue;

	public Player(GameClientSession session) {
		this.session = session;
		session.setPlayer(this);
		handler.init();
		this.msgQueue = new MessageQueue();
	}

	/**
	 * 添加LoginChar;
	 * 
	 * @param loginChar
	 */
	public void addLoginChar(LoginChar loginChar) {
		this.loginChars.add(loginChar);
	}

	@Override
	public void heartBeat() {
		// 此处即使角色不为空, 但是角色的数据不一定构造好了, 且player写human在主线程, player心跳在场景线程;
		// 所以此功能放到SceneHumanManager去做;
		// if(human != null){
		// human.heartBeat();
		// }
	}

	/**
	 * 判断玩家是否在游戏中
	 * 
	 * @return
	 */
	public boolean isInGaming() {
		return this.currentState == PlayerState.GAMEING
				|| this.currentState == PlayerState.BATTLING;
	}

	/**
	 * 放置玩家需要处理的消息
	 * 
	 * @param msg
	 */
	public void putMessage(IMessage msg) {
		this.msgQueue.put(msg);
	}

	/**
	 * 处理服务器收到的来自玩家的消息，在玩家当前所属的场景线程中调用
	 */
	public void processMessage() {
		for (int i = 0; i < SharedConstants.PLAYER_MSG_PROCESS_COUNT_PER_TICK; i++) {
			if (msgQueue.isEmpty()) {
				break;
			}
			IMessage msg = msgQueue.poll();
			if (msg == null) {
				break;
			}
			Assert.notNull(msg);
			long begin = System.nanoTime();

			try {
				handler.handleMessage(msg);
			} catch (Throwable e) {
				Loggers.playerLogger.error(
						"Player process message error, messageType: "
								+ MessageTypeUtil.getMessageTypeName(msg
										.getType()), e);
				this.disconnect();
			} finally {
				// 特例，统计时间跨度
				long time = (System.nanoTime() - begin) / (1000 * 1000);
				if (time > 1) {
					int type = msg.getType();
					Loggers.CLIENT_LOGGER.info("Message:" + msg.getTypeName()
							+ " Type:" + type + " Time:" + time + "ms");
				}
			}
		}
	}

	/**
	 * 发消息的简单实现
	 * 
	 * @param msg
	 */
	public void sendMessage(IMessage msg) {
		Assert.notNull(msg);
		// TODO: cfh 判断消息在当前状态下是否有必要发送
		if (session != null) {
			session.write(msg);
		}
	}

	/**
	 * 发送系统消息
	 * 
	 * @param systemMessageType
	 * @param content
	 */
	public void sendSystemMessage(SystemMessageType systemMessageType,
			String content) {
		if (content != null) {
			GCSystemMessage msg = new GCSystemMessage(content,
					systemMessageType.getIndex());
			sendMessage(msg);
		}
	}

	/**
	 * 关闭用户连接,解除和session的绑定
	 * 
	 */
	public void disconnect() {
		if (this.session != null && this.session.isConnected()) {
			// 此处会自动触发GameServerIoHandler#sessionClosed
			this.session.close();
		}
	}

	public GameClientSession getSession() {
		return session;
	}

	public void setSession(GameClientSession session) {
		this.session = session;
	}

	public Human getHuman() {
		return human;
	}

	public void setHuman(Human human) {
		this.human = human;
	}

	public long getPassportId() {
		return passportId;
	}

	public void setPassportId(long passportId) {
		this.passportId = passportId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	// public void sendErrorMessage(String errorInfo) {
	// GCSystemMessage msg = new GCSystemMessage(errorInfo,
	// GameColor.SYSMSG_FAIL.getRgb(), SysMsgShowTypes.error);
	// sendMessage(msg);
	// }

	public IPlayerState getState() {
		return currentState;
	}

	/**
	 * 切换玩家状态到指定的状态;
	 * 
	 * @param target
	 *            指定的状态;
	 * @return true 表示切換成功;
	 */
	public boolean transferStateTo(PlayerState target) {
		if (!this.canTransferStateTo(target)) {
			return false;
		}
		this.currentState = target;
		return true;
	}

	/**
	 * 是否可以切换到指定状态;
	 * 
	 * @param target
	 * @return true 表示可以;
	 */
	public boolean canTransferStateTo(PlayerState target) {
		return this.currentState.canTransferToState(target);
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}


	/**
	 * 获取玩家的权限类型
	 * 
	 * @return
	 */
	public PlayerPermissionType getPermissionType() {
		return permissionType;
	}

	public void setPermissionType(PlayerPermissionType permissionType) {
		this.permissionType = permissionType;
	}

	public LoginChar getCurrentLoginChar() {
		return currentLoginChar;
	}

	public void setCurrentLoginChar(LoginChar currentLoginChar) {
		this.currentLoginChar = currentLoginChar;
	}

	public LoginChar getLoginCharByIndex(int charIndex) {
		return this.loginChars.get(charIndex);
	}

	public int getCharSize() {
		return this.loginChars.size();
	}

	public Collection<LoginChar> getChars() {
		return this.loginChars;
	}

	public Properties getLocalProperties() {
		return localProperties;
	}

	public void setLocalProperties(Properties localProperties) {
		this.localProperties = localProperties;
	}

	public String getPlateformJosnValue() {
		return plateformJosnValue;
	}

	public void setPlateformJosnValue(String plateformJosnValue) {
		this.plateformJosnValue = plateformJosnValue;
	}

}
