{
	let operations = struct{
		add = fn(a: Int, b: Int) -> Int {
			a + b;
		};
		sub = fn(a: Int, b: Int) -> Int {
			a - b;
		};
		other = struct{
			mul = fn(a: Int, b: Int) -> Int {
				a * b;
			};
			div = fn(a: Int, b: Int) -> Int {
				a / b;
			}
		}
	};
	println operations.add(1, 1);
	println operations.sub(1, 1);
	println operations.other.mul(1, 1);
	println operations.other.div(1, 1);
	let ref = new operations;
	println (!ref).add(1, 1);
}
