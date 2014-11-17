/**
 * 清理目录;
 * @author crazyjohn;
 */
var fs = require("fs");
var srcDir = "F:\\work\\SVN\\client_ios\\soul_client_ipad\\unpackedRes\\needCompress\\";
var unitDirs = fs.readdirSync(srcDir);
unitDirs.forEach(function(eachFile) {
	// 不能是目录;
	if (fs.statSync(srcDir + eachFile).isDirectory()) {
		return;
	}
	// 必须是png文件
	if (eachFile.indexOf("png") == -1) {
		return;
	}
	if (eachFile.indexOf("out") == -1) {
		// 删除此文件
		var path = srcDir + "/" + eachFile;
		fs.unlinkSync(path);
		return;
	}
	// 重命名文件
	var oldName = srcDir + "/" + eachFile;
	var newName = srcDir + "/" + eachFile.substring(4);
	fs.renameSync(oldName, newName);
});