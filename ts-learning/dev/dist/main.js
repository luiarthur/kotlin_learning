import * as F1 from "./file1";
import * as F2 from "./file2";
export function main() {
    var x = 10;
    F1.println("Hey there");
    console.log('boo');
    F1.println(F2.doubleMe(3));
    return x;
}
