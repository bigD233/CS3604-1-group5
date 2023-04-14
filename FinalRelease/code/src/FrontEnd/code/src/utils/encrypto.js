import { SHA256 } from "crypto-js";

function SHA256Password(password) {
    return SHA256('white' + password + 'cloud').toString();
}

export { SHA256Password };
