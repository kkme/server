package com.hifun.soul.tools.deploy;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 批量执行指定sql脚本的工具
 * 
 * @author crazyjohn
 * 
 */
public class BatchExecSqlTool {
	private Logger logger = Logger.getLogger("BatchExecSqlTool");
	private int dateLength = 8;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	/** 可以处理的后缀名列表 */
	private List<String> suffixList = new ArrayList<String>();
	/** 数据库连接 */
	private Connection connection;
	/** sql脚本执行器 */
	private StatementSqlFileExecutor sqlFileExecutor;
	/** sql脚本解析器 */
	private static SqlScriptParser sqlParser = new SqlScriptParser();
	/** 查询数据库版本的SQL */
	private String queryVersionSql = "select * from ";

	/**
	 * 
	 * @param cfgFile
	 *            配置文件
	 * @throws IOException
	 */
	private void run(String cfgFile) throws IOException {
		// 加载配置资源
		String url = "jdbc:mysql://127.0.0.1:3306/sevensoul";
		String prefix = "db_soul_update_";
		InputStream in;
		URL cfgUrl = Thread.currentThread().getContextClassLoader()
				.getResource(cfgFile);
		if (cfgUrl != null) {
			in = cfgUrl.openStream();
		} else {
			in = new FileInputStream(System.getProperty("user.dir")
					+ File.separator + cfgFile);
		}
		Properties properties = new Properties();
		properties.load(in);
		// 添加后缀处理sql脚本
		suffixList.add(".sql");
		try {
			url = properties.getProperty("dbUrl");
			prefix = properties.getProperty("dbUpdateFilePrefix");
			// 构建查询版本sql
			queryVersionSql = queryVersionSql
					+ properties.getProperty("versionTableName");
			// 获取数据库连接
			connection = JDBCUtil.getConnection(url,
					properties.getProperty("userName"),
					properties.getProperty("password"));
			sqlFileExecutor = new StatementSqlFileExecutor(connection);
			batchExecuteSqlFile(properties.getProperty("updateSqlDir"), prefix);
		} catch (Exception e) {
			throw new RuntimeException("BatchExecSqlTool run error", e);
		} finally {
			// 释放资源
			try {
				if (connection != null) {
					connection.close();
					connection = null;
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 获取最新的版本号
	 * 
	 * @return
	 */
	private String getRecentVersion() {
		String querySql = queryVersionSql;
		logger.info("Query db version sql: " + querySql);
		String version = null;
		ResultSet rs = null;
		try {
			rs = JDBCUtil.query(connection.createStatement(), querySql);
			if (rs.last()) {
				version = rs.getString("version");
			}
			return version;
		} catch (SQLException e) {
			throw new RuntimeException("Get version failed", e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
			} catch (SQLException e) {
				logger.error("Close resultSet error", e);
			}
		}
	}

	/**
	 * 对更新文件夹目录下的文件进行过滤
	 * <p>
	 * 返回经过排序的列表;排序方式基于{@link String #compareTo(String)}方法
	 * </p>
	 * 
	 * @param fileDir
	 *            更新文件目录
	 * @return 返回符合更新条件的文件名列表;如果没有符合条件的文件返回size = 0 的List
	 * @throws ParseException
	 */
	private List<String> doFileFilter(String fileDir, String prefix)
			throws ParseException {
		List<String> fileList = new ArrayList<String>();
		File dir = new File(fileDir.trim());
		if (!dir.isDirectory()) {
			logger.error("Update sql file dir is not a file dir, path : "
					+ dir.getAbsolutePath());
			throw new RuntimeException(
					"Update sql file dir is not a file dir, path : "
							+ dir.getAbsolutePath());
		}
		if (!dir.exists()) {
			logger.error("Update sql file dir is not exist, path : "
					+ dir.getAbsolutePath());
			throw new RuntimeException(
					"Update sql file dir is not exist, path : "
							+ dir.getAbsolutePath());
		}
		logger.info("Update sql file dir : " + dir.getAbsolutePath());
		String version = getRecentVersion();
		logger.info("Recent version : " + version);
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (File file : files) {
				if (!checkFileName(file, prefix)) {
					continue;
				}
				String fileDate = getFileDate(file.getName(), prefix);
				if (compareVersion(version, fileDate)) {
					fileList.add(file.getAbsolutePath());
				}
			}

		}
		// 对list进行排序
		Collections.sort(fileList);
		return fileList;
	}

	/**
	 * 检测相应的文件是否符合条件
	 * 
	 * @param file
	 *            被检测的文件
	 * @return true 符合条件;false 不符合条件;
	 */
	private boolean checkFileName(File file, String prefix) {
		// 检测后缀名
		if (!checkFileSuffix(file)) {
			logger.warn("Bad suffix file, fileName is : "
					+ file.getAbsolutePath());
			return false;
		}
		// 检测文件名前缀
		if (!checkFilePrefix(file, prefix)) {
			logger.warn("Bad prefix file, fileName is : "
					+ file.getAbsolutePath());
			return false;
		}
		return true;
	}

	/**
	 * 检测文件前缀是否符合规格
	 * 
	 * @param file
	 *            需要检查的文件
	 * @param prefix
	 *            文件的前缀
	 * @return
	 */
	private boolean checkFilePrefix(File file, String prefix) {
		if (file.getName().startsWith(prefix)) {
			return true;
		}
		return false;
	}

	/**
	 * 检测文件后缀是否符合规格
	 * 
	 * @param file
	 * @return
	 */
	private boolean checkFileSuffix(File file) {
		for (String suffix : suffixList) {
			if (file.getName().endsWith(suffix)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 对比版本号
	 * <p>
	 * 使用基于long的比较实现;也可以使用String本身的比较方法
	 * </p>
	 * 
	 * @param version
	 *            DB中的最近的版本号
	 * @param fileDate
	 *            更新文件的版本号
	 * @return 如果更新文件版本号 > DB版本号 返回true;否则返回 false;
	 * @throws ParseException
	 */
	private boolean compareVersion(String version, String fileDate)
			throws ParseException {
		// 大于等于version的符合
		if (dateFormat.parse(version).getTime() < dateFormat.parse(fileDate)
				.getTime()) {
			return true;
		}
		return false;
	}

	/**
	 * 从文件名中截取出日期
	 * 
	 * @param fileName
	 * @return
	 */
	private String getFileDate(String fileName, String prefix) {
		return fileName
				.substring(prefix.length(), prefix.length() + dateLength);
	}

	/**
	 * 批量执行更新文件目录下的文件
	 * 
	 * @param fileDir
	 *            更新文件路径
	 * @param prefix
	 *            文件的前缀
	 * @throws ParseException
	 */
	private void batchExecuteSqlFile(String fileDir, String prefix)
			throws ParseException {
		List<String> fileList = doFileFilter(fileDir, prefix);
		if (fileList.size() == 0) {
			logger.info("No suitable sql file to update.");
			return;
		}
		for (String filePath : fileList) {
			try {
				executeSqlFile(filePath);
			} catch (Exception e) {
				logger.error("Execute sql file error : " + filePath, e);
				// 如果一个文件执行错误,则终止执行过程
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * 执行单个更新文件
	 * 
	 * @param filePath
	 *            文件路径
	 * @throws SQLException
	 *             数据库执行异常
	 * @throws IOException
	 *             读取文件异常
	 */
	private void executeSqlFile(String filePath) throws SQLException,
			IOException {
		List<String> sqls = sqlParser.splitSqlFile(filePath);
		sqlFileExecutor.executeSqlFile(sqls);
		logger.info("Succeeded execute sql file : " + filePath);
	}

	public static void main(String[] args) throws IOException {
		String cfgFile = "updateSqlTool.properties";
		BatchExecSqlTool tool = new BatchExecSqlTool();
		tool.run(cfgFile);
	}

}
