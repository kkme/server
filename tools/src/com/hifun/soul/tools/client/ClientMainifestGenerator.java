package com.hifun.soul.tools.client;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户端soul_lib下mainifest.xml的生成器;
 * 
 * @author crazyjohn
 * 
 */
public class ClientMainifestGenerator {
	/** 类路径 */
	private static final String SOURCE_PATH = "F:/work/SVN/client_ios/soul_lib_ios/src/";
	/** 生成文件存放路径 */
	private static final String TO_PATH = "F:/work/SVN/client_ios/soul_lib_ios/src/";
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ClientMainifestGenerator genarator = new ClientMainifestGenerator();
		if(args == null
				|| args.length < 2
				|| args[0] == null
				|| args[1] == null){
			genarator.generateMainifestFile(genarator
					.getAllFullClassNames(SOURCE_PATH),TO_PATH);
		}
		else{
			genarator.generateMainifestFile(genarator
					.getAllFullClassNames(args[0].toString()),args[1].toString());
		}
	}

	public List<String> getAllFullClassNames(String dir) {
		List<String> result = new ArrayList<String>();
		File fileDir = new File(dir);
		if (!fileDir.isDirectory()) {
			return result;
		}
		File[] files = fileDir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				List<String> subResult = getAllFullClassNames(file
						.getAbsolutePath());
				result.addAll(subResult);
			} else {
				if (!file.getAbsolutePath().endsWith(".as")) {
					continue;
				}
				result.add(this.getFullClassName(dir, file.getAbsolutePath()));
			}
		}
		return result;
	}

	protected String getFullClassName(String baseDir, String filePath) {
		int comIndex = filePath.indexOf("com");
		String result = filePath.substring(comIndex);
		String midResult = result.substring(0, result.length() - 3);
		String realResult = midResult.replace('\\', '.');
		return realResult;
	}

	protected void generateMainifestFile(List<String> fullClassNames, String dir)
			throws IOException {
		if (fullClassNames == null || fullClassNames.isEmpty()) {
			return;
		}
		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version=\"1.0\"?>\n");
		builder.append("<componentPackage>\n");
		// 生成body
		for (String eachName : fullClassNames) {
			builder.append("<component class=\"" + eachName + "\"/>\n");
		}
		builder.append("</componentPackage>\n");
		File toFile = new File(dir + "mainifest.xml");
		FileWriter writer = new FileWriter(toFile);
		writer.write(builder.toString());
		writer.close();
		System.out.println("Genarate client mainifest.xml succeed! it's here: "
				+ toFile.getAbsolutePath());
	}

}
