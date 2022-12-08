{
	let f = fn(x: Int, y: Int) -> Int {
		let b = 3;
		b + x + y
	};

	let g = fn(x: Int, y: Int) -> Int {
		let b = 5;
		b + x + 2 * y
	};

	let z = 1;
	let m = 1;

	f(z, m) + g(1, 1)
};
