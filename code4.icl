let x = fn (x: Int, y: Int) -> Int {
	x + y;
};

x(2, 3);

let inc = fn (x: Int) -> Int { x + 1; };

let comp = fn (f, g) {
	f(g(x))
}
