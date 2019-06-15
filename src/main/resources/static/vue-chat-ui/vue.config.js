// vue.config.js
module.exports = {
  // 选项...
  // 基本路径
  baseUrl: '/',
  // 输出文件目录
  outputDir: 'dist',
  // eslint-loader 是否在保存的时候检查
  lintOnSave: false,
  // webpack配置
  // see https://github.com/vuejs/vue-cli/blob/dev/docs/webpack.md
  chainWebpack: () => {},
  configureWebpack: () => {},
  // 生产环境是否生成 sourceMap 文件
  productionSourceMap: true,
  devServer: {
    port: 8090,
    proxy: {
      '/apis/*': {
        target: 'http://localhost:7070',
        changeOrigin: true,
        secure: false,
        pathRewrite: {
          '^/apis': 'http://localhost:7070' // 路径重写 (正常请求接口的简写)
        }
      }
    }
  }
}
