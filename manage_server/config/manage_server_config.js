/**
 * 各位如果是根据自己的本地ip之类的改了配,请注意不要提交;
 */
config.bindIp = "0.0.0.0";
config.ports = "8003";
config.serverType = 6;
config.regionId="1";
config.serverGroupId="2";
config.serverIndex=2;
config.localHostId="1002";
config.serverId="10021";
config.version="0.2.0.0";
config.serverDomain="0.0.0.0";
	
/**
 * 管理服本地数据服务的配置
 */
config.dbServiceType = "hibernate";
config.dbConfigName="manage_server_hibernate.cfg.xml"
config.getDataServiceProperties().setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/sevensoul_gm?useUnicode=true&characterEncoding=utf-8&useServerPrepStmts=true");
config.getDataServiceProperties().setProperty("hibernate.connection.username", "root");
config.getDataServiceProperties().setProperty("hibernate.connection.password", "");





