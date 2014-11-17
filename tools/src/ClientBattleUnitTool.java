import java.io.File;

/**
 * 处理client的战斗单元的工具;
 * 
 * @author crazyjohn
 * 
 */
public class ClientBattleUnitTool {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		doSomething("F:\\work\\SVN\\client_ios\\soul_client_ipad\\bin-debug\\resource\\battle\\unit");
	}

	public static void doSomething(String dir) {
		File fileDir = new File(dir);
		if (!fileDir.isDirectory()) {
			return;
		}
		// 取出所有unit单元
		File[] fileList = fileDir.listFiles();
		for (File eachDir : fileList) {
			if (!eachDir.isDirectory()) {
				continue;
			}
			// 取出单个单元所有文件
			File[] subList = eachDir.listFiles();
			for (File eachSubFile : subList) {
				// 如果此文件是一个目录
				if (eachSubFile.isDirectory()) {
					File[] srcList = eachSubFile.listFiles();
					for (File eachSrc : srcList) {
						if (eachSrc.isFile()) {
							// move to baseDir and delete
							eachSrc.renameTo(new File(eachDir.getAbsolutePath()
									+ "\\" + eachSrc.getName()));
						}
					}
					// delete dir
					eachSubFile.delete();
				}
			}
		}
		System.out.println("done!");
	}

}
