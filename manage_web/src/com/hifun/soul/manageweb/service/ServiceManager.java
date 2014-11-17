package com.hifun.soul.manageweb.service;

import org.apache.commons.pool.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.hifun.soul.core.client.socket.SocketConnectionFactory;
import com.hifun.soul.core.client.socket.SocketConnectionPool;
import com.hifun.soul.core.client.socket.SocketFactory;
import com.hifun.soul.core.client.socket.SocketFactory.SocketConfig;
import com.hifun.soul.core.rpc.client.RpcClient;
import com.hifun.soul.manageweb.Loggers;
import com.hifun.soul.manageweb.config.ManageServerConfig;
import com.hifun.soul.manageweb.socket.TGWSocketFactory;
import com.hifun.soul.proto.services.LogServices.LogRpcService;
import com.hifun.soul.proto.services.LogServices.ManageLogRpcService;
import com.hifun.soul.proto.services.Services.AccountRpcService;
import com.hifun.soul.proto.services.Services.BulletinRpcService;
import com.hifun.soul.proto.services.Services.CharacterRpcService;
import com.hifun.soul.proto.services.Services.CharacterStatisticRpcService;
import com.hifun.soul.proto.services.Services.MailRpcService;
import com.hifun.soul.proto.services.Services.MarketActRpcService;
import com.hifun.soul.proto.services.Services.QuestionRpcService;
import com.hifun.soul.proto.services.Services.RuntimeRpcService;
import com.hifun.soul.proto.services.Services.UserRpcService;
import com.hifun.soul.proto.services.Services.UserRpcService.BlockingInterface;
import com.hifun.soul.proto.services.Services.RechargeRpcService;

/**
 * 服务管理器;
 * 
 * @author crazyjohn
 * 
 */
@Scope("singleton")
@Component
public class ServiceManager{
	protected static Logger logger = Loggers.MAIN_LOGGER;
	/** RPC客户端 */
	private RpcClient clientChannel;
	/** 用户服务接口 */
	private BlockingInterface userService;
	/** 运行时管理服务接口 */
	private RuntimeRpcService.BlockingInterface runtimeService;
	/** 账号服务接口 */
	private AccountRpcService.BlockingInterface accountService;
	/** 角色服务接口 */
	private CharacterRpcService.BlockingInterface characterService;
	/** 公告服务接口 */
	private BulletinRpcService.BlockingInterface bulletinService;
	/** 邮件服务接口 */
	private MailRpcService.BlockingInterface mailService;
	/** 服务器日志查询接口 */
	private LogRpcService.BlockingInterface serverLogService;
	/** 玩家反馈信息接口 */
	private QuestionRpcService.BlockingInterface questionService;
	/** 运营系统管理接口 */
	private MarketActRpcService.BlockingInterface marketActService;
	/** 角色统计接口 */
	private CharacterStatisticRpcService.BlockingInterface charStatisticService;
	/** gm操作日志接口 */
	private ManageLogRpcService.BlockingInterface manageLogService;
	/** 充值查询服务 */
	private RechargeRpcService.BlockingInterface rechargeService;

	public void init(ManageServerConfig config) {
		SocketFactory socketFactory = new TGWSocketFactory(
				config.getHost(), config.getPort(),
				new SocketConfig(), true);
		SocketConnectionFactory connectionFactory = new SocketConnectionFactory(
				socketFactory);
		GenericObjectPool.Config poolConfig = new GenericObjectPool.Config();
		poolConfig.testOnBorrow = true;
		poolConfig.testOnReturn = true;
		poolConfig.testWhileIdle = true;

		SocketConnectionPool pool = new SocketConnectionPool(connectionFactory,
				poolConfig);
		clientChannel = new RpcClient(pool);
		// 初始化服务
		userService = UserRpcService.newBlockingStub(clientChannel);
		runtimeService = RuntimeRpcService.newBlockingStub(clientChannel);
		accountService = AccountRpcService.newBlockingStub(clientChannel);
		characterService = CharacterRpcService.newBlockingStub(clientChannel);
		bulletinService = BulletinRpcService.newBlockingStub(clientChannel);
		mailService = MailRpcService.newBlockingStub(clientChannel);
		serverLogService = LogRpcService.newBlockingStub(clientChannel);
		questionService = QuestionRpcService.newBlockingStub(clientChannel);
		marketActService = MarketActRpcService.newBlockingStub(clientChannel);
		charStatisticService = CharacterStatisticRpcService.newBlockingStub(clientChannel);
		manageLogService = ManageLogRpcService.newBlockingStub(clientChannel);
		rechargeService = RechargeRpcService.newBlockingStub(clientChannel);
	}

	/**
	 * 获取用户服务;
	 * 
	 * @return
	 */
	public BlockingInterface getUserService() {
		return this.userService;
	}

	/**
	 * 获取运行时服务;
	 * 
	 * @return
	 */
	public RuntimeRpcService.BlockingInterface getRuntimeServie() {
		return this.runtimeService;
	}

	/**
	 * 获取账户服务
	 * 
	 * @return
	 */
	public AccountRpcService.BlockingInterface getAccountService() {
		return accountService;
	}
	
	/**
	 * 获取角色相关服务
	 * 
	 * @return
	 */
	public CharacterRpcService.BlockingInterface getCharacterService(){
		return characterService;
	}
	
	/**
	 * 获取公告相关服务
	 * 
	 * @return
	 */
	public BulletinRpcService.BlockingInterface getBulletinService() {
		return bulletinService;
	}
	
	/**
	 * 获取邮件相关服务
	 * 
	 * @return
	 */
	public MailRpcService.BlockingInterface getMailService() {
		return mailService;
	}
	
	/**
	 * 获取服务器日志相关服务
	 * 
	 * @return
	 */
	public LogRpcService.BlockingInterface getServerLogService() {
		return serverLogService;
	}
	
	/**
	 * 获取玩家反馈信息服务
	 * @return
	 */
	public QuestionRpcService.BlockingInterface getQuestionService(){
		return questionService;
	}
	
	/**
	 * 获取运营系统管理服务
	 * @return
	 */
	public MarketActRpcService.BlockingInterface getMarketActService(){
		return marketActService;
	}

	/**
	 * 获取角色统计服务
	 * @return
	 */
	public CharacterStatisticRpcService.BlockingInterface getCharStatisticService() {
		return charStatisticService;
	}
	
	/**
	 * 获取gm操作日志服务
	 * @return
	 */
	public ManageLogRpcService.BlockingInterface getManageLogService(){
		return manageLogService;
	}
	
	/**
	 * 获取充值查询服务
	 * @return
	 */
	public RechargeRpcService.BlockingInterface getRechargeService(){
		return rechargeService;
	}
}
