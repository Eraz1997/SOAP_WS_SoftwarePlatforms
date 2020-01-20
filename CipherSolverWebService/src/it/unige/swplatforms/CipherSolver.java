package it.unige.swplatforms;

public class CipherSolver {

	public String decodeRot(String encodedString, int n_rotations) {
		StringBuffer decoded = new StringBuffer("");
		for (int i = 0; i < encodedString.length(); i++) {
			int code = ((int)encodedString.charAt(i));
			if (code >= 'a' && code <= 'z') {
				code -= n_rotations;
				if (code <= 'a')
					code += (int)('a');
			} else if (code >= 'A' && code <= 'Z') {
				code -= n_rotations;
				if (code <= 'A')
					code += (int)('A');
			}
			decoded.append((char)code);
		}
		return decoded.toString();
	}
}
