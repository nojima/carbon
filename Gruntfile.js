'use strict';

module.exports = function(grunt) {
  grunt.loadNpmTasks('grunt-browserify');
  grunt.loadNpmTasks('grunt-contrib-uglify');
  grunt.loadNpmTasks('grunt-shell');
  grunt.loadNpmTasks('grunt-tslint');

  var jsBaseDir = 'src/main/webapp/js';
  var tsBaseDir = 'src/main/webapp/ts';

  grunt.initConfig({
    shell: {
      tsc: {
        command: 'node_modules/typescript/bin/tsc -p ./'
      },

      fixts: {
        command: 'node ./bin/fix-tsconfig.js tsconfig.json src/main/webapp/ts/ typings'
      },

      clean: {
        command: 'rm -rf src/main/webapp/js/bundle.js src/main/webapp/js/compiled'
      }
    },

    tslint: {
      options: {
        configuration: grunt.file.readJSON("tslint.json")
      },
      dist: {
        src: [tsBaseDir + '/**/*.ts']
      }
    },

    browserify: {
      dist: {
        src: jsBaseDir + '/compiled/**/*.js',
        dest: jsBaseDir + '/bundle.js'
      }
    },

    uglify: {
      dist: {
        options: {
          mangle: true,
          complress: true
        },
        files: [{
          src: [jsBaseDir + '/bundle.js'],
          dest: jsBaseDir + '/bundle.min.js'
        }]
      }
    }
  });


  grunt.registerTask('fixts', 'shell:fixts');

  grunt.registerTask('tsc', 'shell:tsc');

  grunt.registerTask('clean', 'shell:clean');

  grunt.registerTask('bundle', ['tslint', 'tsc', 'browserify', 'uglify']);
};
