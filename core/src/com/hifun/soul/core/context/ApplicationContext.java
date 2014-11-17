package com.hifun.soul.core.context;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 基于spring注解的应用程序上下文;
 * 
 * @author crazyjohn
 * 
 */
public class ApplicationContext {
	private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

	/**
	 * 默认扫描所有'com'开头的包路径;
	 */
	public static void initAndScan() {
		context.scan(PackagePathType.PACKAGE_ALL.getPackagePaths());
		context.refresh();
	}

	/**
	 * 扫描指定的一组路径;
	 * 
	 * @param paths
	 */
	public static void initAndScan(String[] paths) {
		context.scan(paths);
		context.refresh();
	}

	public static AnnotationConfigApplicationContext getApplicationContext() {
		return context;
	}
}
