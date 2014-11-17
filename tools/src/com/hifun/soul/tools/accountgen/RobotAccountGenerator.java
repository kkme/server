package com.hifun.soul.tools.accountgen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

/**
 * 生成机器人的帐户自动生成
 * @author SevenSoul
 *
 */
public class RobotAccountGenerator {

	public static void main(String[] args) throws IOException {
		
		File file = new File("c:\\robot.sql");
		
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
		
		
		for (int i = 110000; i <= 150000; i++) {
			String _id = String.valueOf(i);
			String _name = "robot" + String.valueOf(i);
			StringBuffer _strBuff = new StringBuffer();
			_strBuff
				.append("INSERT INTO `t_account` (id,userName,password,state) VALUES ('");
			_strBuff.append(_id).append("','");
			_strBuff.append(_name).append("','");
			_strBuff.append(1).append("',");
			_strBuff.append(1).append(");\n");
			
			writer.write(_strBuff.toString());
		}

		writer.close();
		
	}

}
