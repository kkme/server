package com.hifun.soul.logserver;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;

import javax.script.ScriptException;

import org.apache.mina.common.ByteBuffer;
import org.apache.mina.common.IoAcceptor;
import org.apache.mina.common.SimpleByteBufferAllocator;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;
import org.slf4j.Logger;

import com.hifun.soul.core.codec.GameCodecFactory;
import com.hifun.soul.core.config.ConfigUtil;
import com.hifun.soul.core.server.ServerStatusLog;
import com.hifun.soul.core.server.SingleThreadModel;
import com.hifun.soul.logserver.common.LogServerAssist;
import com.hifun.soul.logserver.createtable.CreateTabaleTask;
import com.hifun.soul.logserver.createtable.CreateTimer;
import com.hifun.soul.logserver.createtable.ITableCreator;
import com.hifun.soul.logserver.dao.DBConnection;
import com.hifun.soul.logserver.util.ResourcePathUtil;

/**
 * 定义LogServer,负责解析配置,并启动服务器<br>
 * FIXME: crazyjohn 重构一下;
 * 
 */
public class LogServer {
	private static final String LOG_SERVER_CFG_JS = "log_server.cfg.js";
	private static final Logger logger = org.slf4j.LoggerFactory
			.getLogger(LogServer.class);

	/** 日志服务器配置 */
	private static LogServerConfig config;

	/** 日志表创建器 */
	private ITableCreator iTableCreator;
	
	/** 日志消息处理接受器 */
	private IoAcceptor acceptor;
	
//	/** Telnet进程*/
//	private static TelnetServerProcess telnetProcess;

	public LogServer() {

	}

	public static LogServerConfig getLogServerConfig() {
		return LogServer.config;
	}

	/**
	 * 执行初始化操作
	 * @throws IOException 
	 * @throws ScriptException 
	 * 
	 * @exception RuntimeException
	 *                如果在初始化的过程中出现错误,会抛出此异常
	 */
	public void initialize() throws ScriptException, IOException {
		if (logger.isInfoEnabled()) {
			logger.info("Mina config:Disable Direct Byte Buffer and Buffer pool.");
			logger.info("Mina config:User SimpleByteBufferAllocator.");
		}
		ByteBuffer.setUseDirectBuffers(false);
		ByteBuffer.setAllocator(new SimpleByteBufferAllocator());

		if (logger.isInfoEnabled()) {
			logger.info("Load the config from file [" + LOG_SERVER_CFG_JS + "]");
		}
		buildConfig(LOG_SERVER_CFG_JS);

		// 初始化数据库连接
		final URL _ibatisConfigFile = ResourcePathUtil
				.getRootPath(LogServer.config.getIbatisConfig());
		if (_ibatisConfigFile == null) {
			throw new RuntimeException("Can't locate the ibatis config file "
					+ LogServer.config.getIbatisConfig() + " in the classpath.");
		}

		DBConnection.initDBConnection(_ibatisConfigFile);

		if (DBConnection.getInstance().getTypes().isEmpty()) {
			if (logger.isWarnEnabled()) {
				logger.warn("No log type found.");
			}
		}

		iTableCreator = LogServer.config.getTableCreator();

		iTableCreator.setBaseTableNames(DBConnection.getInstance().getTypes());

		if (logger.isInfoEnabled()) {
			logger.info("Table creator class:" + this.iTableCreator);
			logger.info("Create log tables");
		}

		iTableCreator.buildTable();

		CreateTimer.scheduleTask(new CreateTabaleTask(iTableCreator),
				LogServer.config.getCreateTableTaskDelay(), LogServer.config
						.getCreateTableTaskPeriod());

		if (logger.isInfoEnabled()) {
			logger.info("Schedule create new log table task [Delay:"
					+ LogServer.config.getCreateTableTaskDelay() + "ms,period:"
					+ LogServer.config.getCreateTableTaskPeriod() + "ms]");
		}
		
//		telnetProcess = new TelnetServerProcess(config.getTelnetServerName(),
//				config.getTelnetBindIp(), config.getTelnetPort(),
//				new LogMessageHandler());
		LogServerAssist.init(config);
	}

	/**
	 * 启动服务
	 * 
	 * @throws IOException
	 */
	public void start() throws IOException {
//		acceptor = new DatagramAcceptor();
		acceptor = new SocketAcceptor();
		SocketAcceptorConfig cfg = new SocketAcceptorConfig();
		cfg.setThreadModel(SingleThreadModel.getInstance());
		cfg.setReuseAddress(true);
//		int recBufferSize = cfg.getSessionConfig().getReceiveBufferSize();
//
//		if (logger.isInfoEnabled()) {
//			logger.info("[#LogServer.LogServer.start] [Default receive buffer size"	+ recBufferSize + "]");
//		}
		
		// 停机时停止主线程的处理
		LogServerAssist.getShutdownHooker().register(new Runnable() {
			@Override
			public void run() {
				acceptor.unbindAll();
//				telnetProcess.stop();
			}
		});
		
		// 增加JVM停机时的处理
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				try {
					ServerStatusLog.getDefaultLog().logStoppping();
					LogServerAssist.getShutdownHooker().run();
				} finally {
					ServerStatusLog.getDefaultLog().logStopped();
				}
			}
		});
		
		// Mina的socket接收缓冲和Session的接收缓冲是同一个,暂时先去掉改配置,等找到好办法在改(在量大的情况下会可能会丢包)
		// cfg.getSessionConfig().setReceiveBufferSize(recBufferSize*8);
		if (logger.isInfoEnabled()) {
			logger.info("[#LogServer.LogServer.start] [New receive buffer size"	+ cfg.getSessionConfig().getReceiveBufferSize() + "]");
		}
		cfg.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new GameCodecFactory(LogServer.config
						.getMessageRecognizer())));

		// 启动log服务器监听
		InetSocketAddress _bindAddress = new InetSocketAddress(InetAddress
				.getByName(LogServer.config.getBindIp()), LogServer.config
				.getPort());

		acceptor.bind(_bindAddress, LogServer.config.getLogMessageHandler(),
				cfg);
		if (logger.isInfoEnabled()) {
			logger.info("Log server listening on " + _bindAddress.toString());
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LogServer _server = new LogServer();
		try {
			ServerStatusLog.getDefaultLog().logStarting();
			_server.initialize();
			_server.start();
//			telnetProcess.start();
			ServerStatusLog.getDefaultLog().logRunning();
		} catch (Exception e) {
			ServerStatusLog.getDefaultLog().logStartFail();
			e.printStackTrace();
			System.exit(1);
		}
	}

	
	private static void buildConfig(String logConfigFile) throws ScriptException, IOException {
		config = new LogServerConfig();
		ConfigUtil.loadJsConfig(config, LOG_SERVER_CFG_JS);
	}
	
}
