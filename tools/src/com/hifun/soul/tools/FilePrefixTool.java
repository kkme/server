package com.hifun.soul.tools;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 批量给文件添加前缀名工具;
 * 
 * @author crazyjohn
 * 
 */
public class FilePrefixTool {
	private static Logger logger = LoggerFactory.getLogger(FileTools.class);

	/**
	 * 给指定目录下文件添加前缀;
	 * 
	 * @param prefix
	 * @param dir
	 */
	public static void addPrefixToDirFiles(String prefix, String dir) {
		File fileDir = new File(dir);
		if (!fileDir.exists()) {
			logger.error(String.format("The dir %s dose not exist!", dir));
			return;
		}
		if (fileDir.isFile()) {
			logger.error(String.format("The dir %s is not a dir!", dir));
			return;
		}
		File[] fileList = fileDir.listFiles();
		for (File eachFile : fileList) {
			if (eachFile.isFile()) {
				System.out.println(eachFile.getParent() + "//" + prefix
						+ eachFile.getName());
				eachFile.renameTo(new File(eachFile.getParent() + "//" + prefix
						+ eachFile.getName()));
			}
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		addPrefixToDirFiles("small_", "F:/other");
	}

}
