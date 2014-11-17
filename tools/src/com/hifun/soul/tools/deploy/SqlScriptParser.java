package com.hifun.soul.tools.deploy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.coolsql.view.sqleditor.ScriptParser;

/**
 * 使用opensource的cool.jar中的sql脚本解析器实现脚本解析的解析器
 * 
 * @author crazyjohn
 * 
 */
public class SqlScriptParser {
	/** 脚本解析器 */
	private ScriptParser parser = new ScriptParser();

	public List<String> splitSqlFile(String filePath) throws IOException {
		List<String> sqlList = new ArrayList<String>();
		parser.setFile(new File(filePath), "UTF-8");
		parser.startIterator();
		String sql;
		while (parser.hasNext()) {
			sql = parser.getNextCommand();
			if (sql == null) {
				continue;
			}
			sqlList.add(sql);
		}
		return sqlList;
	}

}
