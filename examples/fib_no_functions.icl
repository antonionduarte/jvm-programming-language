{
    let head = "fib(";
    let mid = ") = ";
	let fibMinus2 = new 0;
	let fibMinus1 = new 1;
	let i = new 2;
	println head @ toString 0 @ mid @  !fibMinus2;
	println head @ toString 1 @ mid @ !fibMinus1;
	while(!i <= 10) {
		let fibI = !fibMinus2 + !fibMinus1;
		println head @ !i @ mid @ fibI;
		fibMinus2 := !fibMinus1;
		fibMinus1 := fibI;
		i := !i + 1;
	}
}
