/*
 * Server基本信息 
 */
// 服务器类型
config.serverType=1
// 是否调试模式
config.debug=1
// 编码
config.charset="UTF-8"
// 版本
config.version="0.2.0.0"
// 资源版本
config.resourceVersion="0.2.0.0"
// 数据库版本
config.dbVersion="0.2.0.0"
// 大区ID
config.regionId="1"
config.localHostId="1002"
config.serverGroupId="2"
config.serverIndex=1
config.serverId="2"
config.bindIp="127.0.0.1"
// 监听的端口
config.ports="8001"
// 服务器名称
config.serverName="gameserver1"
config.serverHost="0.0.0.0"
config.serverDomain="s2.app100690415.qqopenapp.com"
config.ioProcessor=1
config.language="zh_CN"
config.i18nDir="i18n"
// linux下识别的方式
config.baseResourceDir="../resources"
config.scriptDir="scripts"

// 向DB同步的缓存开关
config.cacheToDBTurnOn = true;
// 多少次tick后向DB同步玩家数据(10次是5s)
config.tickTimesToSynchronizationDBForHuman = 10;
//多少次tick后向DB同步系统数据(120次是1m)
config.tickTimesToSynchronizationDBForSystem = 120;
/**
 * 数据服务的配置
 */
config.dbServiceType = "hibernate";
config.dbConfigName="game_server_hibernate.cfg.xml"
config.getDataServiceProperties().setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/sevensoul?useUnicode=true&characterEncoding=utf-8&useServerPrepStmts=true");
config.getDataServiceProperties().setProperty("hibernate.connection.username", "root");
config.getDataServiceProperties().setProperty("hibernate.connection.password", "");
config.h2ConfigName="game_server_h2.cfg.xml,game_server_h2_query.xml"
config.turnOnH2Cache=true;
	
config.flashSocketPolicy="<cross-domain-policy>\r\n<allow-access-from domain=\"*\" to-ports=\"80-65535\" />\r\n </cross-domain-policy>\r\n"
config.gameServerCount=1;
// 服务器允许的最大在线人数;
config.maxOnlineUsers=5000;

/*
 *配置Log Server 
 */
config.logConfig.logServerIp="0.0.0.0"
 // 日志服开关
config.logServerSwitch = false;
config.logConfig.logServerPort=8002

/*
 *配置Telnet 
 */
config.telnetServerName="GameServer_telnet"
config.telnetBindIp="0.0.0.0"
config.telnetPort=8008
// 这个参数貌似是现在没有使用
config.chargeEnabled=true;
// 引导
config.guideOpened = true;
// DB操作记录开关;调试的时候可以打开方便查DB热点, 正式运营环境需要关掉, 否则会有内存泄露的风险;
config.dbOperationOpen = true;
// 数据库版本
config.dbVersion = "20140423";
// 接平台相关
/** 是否开启平台账户认证 */
config.useLocalAuthorize = false;
/** 登陆墙是否打开，默认关闭 */
config.loginWallEnabled = false;
/** 平台类型(0=不接平台、1=Qzone) */
config.localType = 1;
