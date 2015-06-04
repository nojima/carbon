'use strict';

var fs = require('fs');
var glob = require('glob');
var path = require('path');
var Promise = require('es6-promise').Promise;

function findFiles(pattern) {
  return new Promise(function(resolve, reject) {
      glob(pattern, function(err, files) {
          err ? reject(err) : resolve(files);
      });
  });
}

function readUtf8File(path) {
  return new Promise(function(resolve, reject) {
      fs.readFile(path, 'utf8', function(err, contents) {
          err ? reject(err) : resolve(contents);
      });
  });
}


if (5 <= process.argv.length) {
  var tsConfigPath = process.argv[2];
  var tsBasePath = process.argv[3];
  var typingBasePath = process.argv[4];

  var configDir = path.dirname(tsConfigPath);

  Promise.all([readUtf8File(tsConfigPath),
               findFiles(tsBasePath + '/**/*.ts')]).then(function(values) {
    var tsFiles = values[1];
    tsFiles.push(typingBasePath + '/bundle.d.ts');

    var json = JSON.parse(values[0]);
    json['files'] = tsFiles.map(function(p) { return path.relative(configDir, p); });
    fs.writeFile(tsConfigPath, JSON.stringify(json, '', '  '), 'utf8');
  });
}
