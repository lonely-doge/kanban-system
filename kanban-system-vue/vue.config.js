module.exports = {
	configureWebpack: {
		resolve: {
			alias: {
        'assets':'@/assets',
        'components': '@/components',
        'views': '@/views',
        'layouts': '@/layouts',
        'api': '@/api',
      },
		},
	},
	css: {
		loaderOptions: {
      scss: {
        prependData: `
          @import "assets/scss/index.scss";
          @import "assets/scss/variable.scss";
        `
      },
		},
	},
};
