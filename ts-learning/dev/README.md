# Learning TypeScript

Install TypeScript via:
```bash
npm install -g typescript
```

Good resources:
- [TypeScript in 5 minutes][1]
- [TypeScript modules][2]
- [TypeScript minimal setup][3]

Set up Local env:
```bash
# Installs the follow packages in project in `node_modules`
npm install typescript ts-loader webpack webpack-cli --save-dev
```

`tsconfig.json` handles build instructions for ts files (in `src/`).

`package.json` generates the virtual environment for reproducibility.

`webpack` is used to compile multiple js files into one.
The default bundler in TS is pretty annoying. `webpack.config.js` 
has some options for bundling library into `bundle.js` and 
putting it under global var `myLib` after linking in html.

[1]: https://www.typescriptlang.org/docs/handbook/typescript-in-5-minutes.html
[2]: https://www.typescriptlang.org/docs/handbook/modules.html
[3]: https://michalzalecki.com/creating-typescript-library-with-a-minimal-setup/
