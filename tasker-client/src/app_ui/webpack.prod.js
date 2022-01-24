const { merge } = require("webpack-merge");
const common = require("./webpack.common.js");
const path = require("path");
const Dotenv = require("dotenv-webpack");

module.exports = merge(common, {
  mode: "development",
  devtool: 'eval-cheap-source-map',
  output: {
    filename: "my_app_ui.js",
    path: path.resolve(__dirname, "../main/resources/static"),
    library: "myappReactMicroAppp",
    libraryTarget: "window",
  },
  plugins: [
    new Dotenv({
      path: "./.env",
      safe: true,
      systemvars: true,
      silent: true,
      defaults: true,
    })
  ],
});
