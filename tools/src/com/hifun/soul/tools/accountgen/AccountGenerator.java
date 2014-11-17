package com.hifun.soul.tools.accountgen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 账号自动生成
 * 
 * 
 */
public class AccountGenerator {
	private static String namePrefix = "bot";

	public static void main(String[] args) throws IOException {

		File file = new File("d:\\account.sql");

		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file)));

		for (int i = 1; i <= 200; i++) {
			String _name = String.valueOf(i);
			StringBuffer _strBuff = new StringBuffer();
			_strBuff.append("INSERT INTO `t_account` (id,userName,password,state,permissionType) VALUES (");
			_strBuff.append(_name).append(",'");
			_strBuff.append(namePrefix).append(_name).append("','");
			_strBuff.append(namePrefix).append(_name).append("',");
			_strBuff.append("1,");
			_strBuff.append("2").append(");\r\n");
			System.out.println(_strBuff.toString());
			writer.write(_strBuff.toString());
		}

		writer.close();

	}

}
