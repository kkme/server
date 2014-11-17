/**
 * 清理目录;
 * @author crazyjohn;
 */
var fs = require("fs");
var srcDir = "F:\\work\\SVN\\client_ios\\soul_client_ipad\\bin-debug\\resource\\battle\\unit\\";
var unitDirs = fs.readdirSync(srcDir);
var unitBgein = 401;
var unitEnd = 516;
unitDirs.forEach(function(eachDir) {
	if (!fs.statSync(srcDir + eachDir).isDirectory()) {
		return;
	}
	if (eachDir < unitBgein || eachDir > unitEnd) {
		return;
	}
	var pngFiles = fs.readdirSync(srcDir + eachDir + "/");
	console.log("Clear " + eachDir + "'s file: ");
	pngFiles.forEach(function(eachFile) {
		// 必须是png文件
		if (eachFile.indexOf("png") == -1) {
			return;
		}
		if (eachFile.indexOf("out") == -1) {
			// 删除此文件
			var path = srcDir + eachDir + "/" + eachFile;
			fs.unlinkSync(path);
			return;
		}
		// 重命名文件
		var oldName = srcDir + eachDir + "/" + eachFile;
		var newName = srcDir + eachDir + "/" + eachFile.substring(4);
		fs.renameSync(oldName, newName);
	});
});