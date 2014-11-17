/**
 * 压缩指定目录的文件;
 * @author crazyjohn;
 */
var https = require("https");
var fs = require("fs");
/**
 * [s0NqNc55u2JqRDabFxPLoltbBA1x7yrB, eworld2009]
 * [0rYc_Mjt07J2xLRhjAF2t3Lh_xb9JOjj, ewold2009]
 * [4vBiUtZZGibL-XOU_WtWTzTaWI1u9_7b, hkdg2009]
 */
var key = "s0NqNc55u2JqRDabFxPLoltbBA1x7yrB";

// 这里需要补的逻辑是判断所有unit文件夹下的png文件，然后进行压缩，最好压缩完一个就给些响应
// 战斗单元目录
//战斗单元目录 \soul_client_ipad\unpackedRes\needCompress
var srcDir = "F:\\work\\SVN\\client_ios\\soul_client_ipad\\unpackedRes\\needCompress\\";
var unitDirs = fs.readdirSync(srcDir);
var pngFileCount = 0;
var unitBgein = 303;
var unitEnd = 304;

/**
 * 压缩目录;
 */
function compressPngDir(pngFiles) {
	pngFiles.forEach(function(file) {
		// 不能是目录
		if (fs.statSync(srcDir + file).isDirectory()) {
			return;
		}
		if (file.indexOf("png") == -1) {
			return;
		}
		console.log("Begin compress: " + file);
		// 安全证书验证
		var boundaries = /-----BEGIN CERTIFICATE-----[\s\S]+?-----END CERTIFICATE-----\n/g;
		var certs = fs.readFileSync("cacert.pem").toString();
		https.globalAgent.options.ca = certs.match(boundaries);

		var options = require("url").parse("https://api.tinypng.com/shrink");
		options.auth = "api:" + key;
		options.method = "POST";
		// 输入输出流
		var input = fs.createReadStream(srcDir + "/" + file);
		var output = fs.createWriteStream(srcDir + "/out_" + file);
		var request = https.request(options, function(response) {
		  if (response.statusCode === 201) {
		    /* Compression was successful, retrieve output from Location header. */
		    https.get(response.headers.location, function(response) {
		      console.log("Finished compress: " + file);
		      response.pipe(output);
		    });
		  } else {
		    /* Something went wrong! You can parse the JSON body for details. */
		    console.log("Compression failed: " + response.head);
		  }
		});
		input.pipe(request);
	});
}

// 遍历指定目录, 并且执行操作
compressPngDir(unitDirs);

