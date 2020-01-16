// https://webpack.js.org/guides/typescript/
// https://medium.com/dev-bits/everything-i-know-about-writing-modular-javascript-applications-37c125d8eddf

const path = require('path');

module.exports = {
  // mode: 'production', 
  mode: 'development', 
  entry: './src/index.ts',
  devtool: 'inline-source-map',
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: 'ts-loader',
        exclude: /node_modules/,
      },
    ],
  },
  resolve: {
    extensions: [ '.tsx', '.ts', '.js' ],
  },
  output: {
    filename: 'bundle.js',
    path: path.resolve(__dirname, 'dist'),
    library: 'myLib',
  },
};
