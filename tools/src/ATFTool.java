import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ATF工具;
 * 
 * @author crazyjohn
 * 
 */
public class ATFTool {
	/** 依赖文件的存放路径 */
	private static final String SOURCE_PATH = "F:/work/SVN/client_ios/soul_client_ipad/bin-debug/resource/areaMap/";
	/** 生成文件存放路径 */
	private static final String TO_PATH = "F:/work/SVN/client_ios/soul_client_ipad/bin-debug/resource/areaMap/";
	
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
	
	protected void generateUnitsSkillFile(String sourceDir, String toDir)
			throws IOException {
		// 先找到下面的战斗单位
		List<File> battleUnits = getDirectorys(sourceDir);
		if(battleUnits != null
				&& battleUnits.size() > 0){
			for(File battleUnit : battleUnits){
					// 每个战斗单元的技能
					List<File> skills = getSubFileEndedByEndString(battleUnit.getAbsolutePath(), ".png");
					if(skills != null
							&& skills.size() > 0){
						for(File skill : skills){
							String skillCmd = "cmd /c png2atf -p -i " 
									+ skill.getAbsolutePath() + " -o " + skill.getAbsolutePath().substring(0, skill.getAbsolutePath().length() - 4) + ".atf";
							Runtime.getRuntime().exec(skillCmd);
						}
					}
			}
		}
		System.out.println("Finished generate atfTool.");
	}


	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		ATFTool genarator = new ATFTool();
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
	
	

}
