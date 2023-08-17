'use strict';
const gulp = require('gulp');
const rename = require('gulp-rename');
const exec = require('child_process');
const fse = require('fs-extra');

gulp.task('build-server', function(done) {
    exec.execSync('mvn clean package', { cwd: 'jdtext/', stdio: [0,1,2]});
    gulp.src('jdtext/org.gap.vscode.javaranking.site/target/repository/plugins/org.gap.vscode.javaranking.ext*.jar').pipe(rename('org.gap.vscode.javaranking.ext.jar')).pipe(gulp.dest('jars/'));
    done();
});