{
	let fibMinus2 = new 0;
	let fibMinus1 = new 1;
	let i = new 2;
	println !fibMinus2;
	println !fibMinus1;
	while(!i <= 10) {
		let fibI = !fibMinus2 + !fibMinus1;
		println fibI;
		fibMinus2 := !fibMinus1;
		fibMinus1 := fibI;
		i := !i + 1;
	}
}
