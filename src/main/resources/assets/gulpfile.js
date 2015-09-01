var gulp = require('gulp'),
    connect = require('gulp-connect'),
    open = require("gulp-open"),
    browserify = require('browserify'),
    babelify = require("babelify"),
    source = require('vinyl-source-stream'),
    port = process.env.port || 3031,

    isDevelopment = process.env.NODE_ENV === 'development',
    dest = "./build",
    src = './javascripts';

gulp.task('browserify', function () {
    return browserify({entries: src + '/app.jsx', extensions: ['.jsx'], debug: true})
        .transform(babelify)
        .bundle()
        .pipe(source('app.js'))
        .pipe(gulp.dest(dest));
});

// launch browser in a port
gulp.task('open', function () {
    var options = {
        url: 'http://localhost:' + port
    };
    gulp.src( 'index.html' )
        .pipe(open('', options));
});

// live reload server
gulp.task('connect', function () {
    connect.server({
        root: '',
        port: port,
        livereload: true
    });
});

// live reload js
gulp.task('js', function () {
    gulp.src(dest + '/**/*.js')
        .pipe(connect.reload());
});

// live reload html
gulp.task('html', function () {
    gulp.src('./*.html')
        .pipe(connect.reload());
});

// watch files for live reload
gulp.task('watch', function () {
    gulp.watch(dest + '/*.js', ['js']);
    gulp.watch('index.html', ['html']);
    gulp.watch(src + '/**/*.js', ['browserify']);
});

gulp.task('default', ['browserify']);

gulp.task('serve', ['browserify', 'connect', 'open', 'watch']);

gulp.task('build', ['browserify']);