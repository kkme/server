package com.hifun.soul.tools;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FIXME: crazyjohn 删除需要删好几次才能删干净;
 * 
 * @author crazyjohn
 * 
 */
public class FileTools {
	private static Logger logger = LoggerFactory.getLogger(FileTools.class);

	public static void filterdDeleteFrom(String dir, String filePostfix) {
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
		logger.info("The dir: " + fileDir + "'s file list: "
				+ getFilesInfo(fileList));
		for (File eachFile : fileList) {
			// 如果是目录
			if (eachFile.isDirectory()) {
				if (eachFile.getName().endsWith(filePostfix)) {
					boolean delete = deleteDir(eachFile);
					logger.info("IsDeleteSucceed: " + delete);
					continue;
				}
				// 递归
				filterdDeleteFrom(eachFile.getAbsolutePath(), filePostfix);
			}
		}
	}

	private static boolean deleteDir(File dir) {
		if (dir == null) {
			return false;
		}
		if (dir.list().length == 0) {
			dir.delete();
			return true;
		}
		if (dir.isFile()) {
			dir.delete();
			return true;
		}
		File[] fileList = dir.listFiles();
		for (File eachFile : fileList) {
			if (eachFile.isFile()) {
				eachFile.delete();
			} else {
				deleteDir(eachFile);
			}
		}
		return true;
	}

	private static String getFilesInfo(File[] fileList) {
		if (fileList == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		for (File eachFile : fileList) {
			sb.append(eachFile.getName() + "; ");
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		FileTools.filterdDeleteFrom("F:/resource/code/history", "svn");
	}
}
