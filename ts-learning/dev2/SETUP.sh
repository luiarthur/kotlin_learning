# Create `package.json`. Tracks version.
npm init --yes

# Install TS for this project
npm install typescript --save-dev  # Note that `tsc` is at `node_modules/.bin/tsc`

# Initialize
# ./node_modules/.bin/tsc --init  # generates `tsconfig.json`
npx tsc --init # is the same thing. Install npx with `npm install -g npx`

# Compile continuously
npx tsc --watch

# NOTE: https://www.typescriptlang.org/docs/handbook/triple-slash-directives.html
# The /// at the top of the scripts tell the compiler how to bundle the compiled
# scripts. 
