package com.hifun.soul.swf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SoulSwf {

	/**
	 * 获取指定目录下所有符合条件的文件前缀;
	 * 
	 * @param dir
	 * @return
	 */
	public List<String> getSuitableFilePrefixes(String dir) {
		List<String> result = new ArrayList<String>();
		File fileDir = new File(dir);
		File[] fileList = fileDir.listFiles();
		for (File eachFile : fileList) {
			if (isASuitFile(eachFile, fileList)) {
				result.add(eachFile.getName());
			}
		}

		return result;
	}

	private boolean isASuitFile(File eachFile, File[] fileList) {
		
		return false;
	}

	/**
	 * 在当前目录下生成所有的xml描述文件;
	 * 
	 * @param dir
	 * @return
	 * @throws IOException
	 */
	public void generateXmlDescFiles(List<String> suitPrefixes)
			throws IOException {
		for (String prefix : suitPrefixes) {
			genarateXmlDescFile(prefix);
		}
	}

	/**
	 * 生成单个xml描述文件;
	 * 
	 * @param prefix
	 * @return
	 * @throws IOException
	 */
	private void genarateXmlDescFile(String prefix) throws IOException {
		FileWriter writer = new FileWriter(new File(prefix + ".xml"));
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"utf-8\"?><lib allowDomain=\"*\">");
		sb.append("<bitmapdata file=\"" + prefix
				+ ".png\" class=\"PNGBitmapData\"/>");
		sb.append("<bytearray file=\"" + prefix
				+ ".json\" class=\"JSONData\"/>");
		sb.append("</lib>");
		writer.write(sb.toString());
		writer.close();
	}

	/**
	 * 把指定的xml描述文件生成swf文件;
	 * 
	 * @param xmlFile
	 */
	public void generateSwfFiles(String xmlFile) {
		// TODO: call jar
	}

	public static void main(String[] args) {

	}
}
