import * as React from "react";

export interface HelloProps {
  compiler: string
  framework: string
}

export function Hello(props: HelloProps) {
  return <h1 className="bla">
           Hello from {props.compiler} and {props.framework}!
         </h1>
}

// export class Hello extends React.Component<HelloProps, {}> {
//     render() {
//         return <h1>Hello from {this.props.compiler} and {this.props.framework}!</h1>;
//     }
// }
