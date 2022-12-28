{
	let x = true;
	let y = false;
	let z = 2;
	let w = 1;

	if (z > w) {
		println x;
	}

	if (y) {
		println 91;
	} else if (z < w) {
		println 92;
	} else if (z <= w) {
		println 93;
	} else if (x == y) {
		println 94;
	} else if (z == w) { 
		println 95;
	} else if (z >= w && x && not y && (x || y)) {
		println true;
	} else {
		println 96;
	}
}
