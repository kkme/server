package com.hifun.soul.core.context;

import java.util.ArrayList;
import java.util.List;

/**
 * 包路径类型;
 * 
 * @see ApplicationContext
 * @author crazyjohn
 * 
 */
public enum PackagePathType {
	/** CORE工程 */
	PACKAGE_CORE() {
		@Override
		public String[] getPackagePaths() {
			return new String[] { "com.hifun.soul.core.*",
					"com.hifun.soul.common.*" };
		}

	},
	/** 搜集所有工程的包 */
	PACKAGE_ALL() {

		@Override
		public String[] getPackagePaths() {
			return new String[] { "com.*" };
		}

	},
	/** game_server工程 */
	PACKAGE_GAME_SERVER() {

		@Override
		public String[] getPackagePaths() {
			List<String> results = getCoreAndGameDbPath();
			results.add("com.hifun.soul.gameserver.*");
			return results.toArray(new String[0]);
		}

	},
	/** manage_server工程 */
	PACKAGE_MANAGE_SERVER() {

		@Override
		public String[] getPackagePaths() {
			List<String> results = getCoreAndGameDbPath();
			results.add("com.hifun.soul.manageserver");
			results.add("com.hifun.soul.manageserver.*");
			results.add("com.hifun.soul.gameserver.*");
			results.add("com.hifun.soul.core.*");
			results.add("com.hifun.soul.common.*");			
			return results.toArray(new String[0]);
		}

	},
	PACKAGE_MANAGE_WEB(){
		@Override
		public String[] getPackagePaths() {
			List<String> results = getCoreAndGameDbPath();
			results.add("com.hifun.soul.gameserver.*");
			results.add("com.hifun.soul.manageweb.*");		
			return results.toArray(new String[0]);
		}
	};
	/**
	 * 扫描core和DB包;
	 * 
	 * @return
	 */
	List<String> getCoreAndGameDbPath() {
		List<String> results = new ArrayList<String>();
		for (String path : PACKAGE_CORE.getPackagePaths()) {
			results.add(path);
		}
		results.add("com.hifun.soul.gamedb.*");
		return results;
	}

	public abstract String[] getPackagePaths();
}
