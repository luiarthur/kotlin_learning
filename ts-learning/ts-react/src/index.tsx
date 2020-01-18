import * as React from "react";
import * as ReactDOM from "react-dom";

import { Hello } from "./components/Hello";

ReactDOM.render(
  [1, 2, 3].map(x => 
    <Hello  compiler="TypeScript"  framework={x.toString()}  key={x} />
  ),
  document.getElementById("root")
);
