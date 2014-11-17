import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * XML和CSV的转化工具;
 * 
 * @author crazyjohn
 * 
 */
public class XML2CSVTool {

	/**
	 * 加载配置文件;
	 * 
	 * @param fileName
	 * @return
	 * @throws JDOMException
	 * @throws IOException
	 */
	public static List<String> loadXML(String fileName) throws JDOMException,
			IOException {
		List<String> items = new ArrayList<String>();
		SAXBuilder builder = new SAXBuilder(false);
		Document document = builder.build(fileName);
		Element root = document.getRootElement();
		Element targetNode = root.getChild("target");
		@SuppressWarnings("unchecked")
		List<Element> entryNodeList = targetNode.getChildren("entry");
		for (Element each : entryNodeList) {
			Element wcNode = each.getChild("wc-status");
			if (wcNode == null) {
				continue;
			}
			Element commitNode = wcNode.getChild("commit");
			if (commitNode == null) {
				continue;
			}
			String prefix = "resource\\";
			if (!each.getAttributeValue("path").startsWith(prefix)) {
				continue;
			}
			if (each.getAttributeValue("path").endsWith(".png")
					|| each.getAttributeValue("path").endsWith(".swf")
					|| each.getAttributeValue("path").endsWith(".xml")
					|| each.getAttributeValue("path").endsWith(".atf")) {
				String strItem = each.getAttributeValue("path").substring(
						prefix.length(),
						each.getAttributeValue("path").length())
						+ "," + commitNode.getAttributeValue("revision") + "\r\n";
				String newStrItem = strItem.replaceAll("\\\\", "/");
				items.add(newStrItem);

			}
		}
		return items;
	}

	/**
	 * 把数据写入csv;
	 * 
	 * @param items
	 * @param outFileName
	 * @throws IOException
	 */
	private static void writeToCSVFile(List<String> items, String outFileName)
			throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(
				new FileOutputStream(outFileName), "UTF-8");
		for (String item : items) {
			writer.write(item);
		}
		writer.close();
	}

	/**
	 * 写入最后的版本内容;
	 * 
	 * @param outFileName
	 * @param content
	 * @throws IOException
	 */
	private static void mockBigCodeSwfVersion(String outFileName, String content)
			throws IOException {
		OutputStreamWriter writer = new OutputStreamWriter(
				new FileOutputStream(outFileName, true), "UTF-8");
		String[] versions = content.split(";");
		for (String version : versions) {
			writer.write(version + "\r\n");
		}
		writer.close();
	}

	public static void main(String[] args) throws JDOMException, IOException {
		// args[0] 目标xml文件;
		// args[1] 输出的csv文件;
		// args[2] 尾部要添加的内容;
		String xmlPath;
		String csvPath;
		String version = "20140421";
		if (args.length < 3) {
			xmlPath = "F:\\TX_CDN\\client\\revisions.xml";
			csvPath = "F:\\TX_CDN\\client\\revisions.csv";
		} else {
			xmlPath = args[0];
			csvPath = args[1];
		}
		List<String> items = loadXML(xmlPath);
		writeToCSVFile(items, csvPath);
		mockBigCodeSwfVersion(csvPath, version);// args[0] 目标xml文件;
	}

}
