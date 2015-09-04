var port = process.env.port || 3031;
var isDevelopment = process.env.NODE_ENV === 'development';
var dest = './build';
var src = './javascripts';

module.exports = {
  browserSync: {
    server: {
      baseDir: './'
    },
    files: [
      dest + "/**",
      "!" + dest + "/**.map"
    ]
  },

  isDevelopment: isDevelopment,

  browserify: {

    // Enable source maps
    debug: true,

    extensions: ['.jsx'],

    // A separate bundle will be generated for each
    // bundle config in the list below
    bundleConfigs: [{
      entries: src + '/app.js',
      dest: dest,
      outputName: 'app.js'
    }, {
      entries: src + '/plugins/plugin.js',
      dest: dest,
      outputName: 'plugin.js'
    }]
  }

};
