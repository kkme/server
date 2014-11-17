import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户端unitsSkill.xml的生成器;
 * @author magicstone
 */
public class ClientUnitsSkillGenerator {
	/** 依赖文件的存放路径 */
	private static final String SOURCE_PATH = "F:/work/SVN/client_ios/soul_client_ipad/bin-debug/resource/battle/unit/";
	/** 生成文件存放路径 */
	private static final String TO_PATH = "F:/work/SVN/client_ios/soul_client_ipad/bin-debug/resource/battle/unit/";
	
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		ClientUnitsSkillGenerator genarator = new ClientUnitsSkillGenerator();
		if(args == null
				|| args.length < 2
				|| args[0] == null
				|| args[1] == null){
			genarator.generateUnitsSkillFile(SOURCE_PATH,TO_PATH);
		}
		else{
			genarator.generateUnitsSkillFile(args[0].toString(),args[1].toString());
		}
	}

	/**
	 * 找到以固定字符串结尾的文件(不考虑子目录)
	 * @param dir
	 * @param endString
	 * @return
	 */
	private List<File> getSubFileEndedByEndString(String dir, String endString) {
		List<File> result = new ArrayList<File>();
		File fileDir = new File(dir);
		if (!fileDir.isDirectory()) {
			return result;
		}
		File[] files = fileDir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				continue;
			} else {
				if (!file.getAbsolutePath().endsWith(endString)) {
					continue;
				}
				result.add(file);
			}
		}
		return result;
	}
	
	/**
	 * 找到某个目录下的子目录(不循环查找)
	 * @param dir
	 * @return
	 */
	private List<File> getDirectorys(String dir) {
		List<File> result = new ArrayList<File>();
		File fileDir = new File(dir);
		if (!fileDir.isDirectory()) {
			return result;
		}
		File[] files = fileDir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				result.add(file);
			}
		}
		return result;
	}

	protected void generateUnitsSkillFile(String sourceDir, String toDir)
			throws IOException {
		StringBuilder builder = new StringBuilder();
		builder.append("<?xml version=\"1.0\"?>\n");
		builder.append("<units>\n");
		// 生成body
		// 先找到下面的战斗单位
		List<File> battleUnits = getDirectorys(sourceDir);
		if(battleUnits != null
				&& battleUnits.size() > 0){
			for(File battleUnit : battleUnits){
				builder.append("<unit id=\"" + battleUnit.getName() + "\">\n");
					// 每个战斗单元的技能
					List<File> skills = getSubFileEndedByEndString(battleUnit.getAbsolutePath(), ".png");
					if(skills != null
							&& skills.size() > 0){
						for(File skill : skills){
							builder.append("	<skill id=\"" + skill.getName() + "\"/>\n");
						}
					}
				builder.append("</unit>\n");
			}
		}
		builder.append("</units>\n");
		File toFile = new File(toDir + "unitsSkill.xml");
		FileWriter writer = new FileWriter(toFile);
		writer.write(builder.toString());
		writer.close();
		System.out.println("Genarate client unitsSkill.xml succeed! it's here: "
				+ toFile.getAbsolutePath());
	}

}
