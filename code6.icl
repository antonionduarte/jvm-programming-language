{
	let colors = struct {
		green = 0;
		yellow = 1;
		red = 2
	};

	let newLight = fn(on : Bool, color : Int) -> struct{ on : Ref<Bool>, color : Ref<Int> } {
		struct{ on = new on; color = new color };
	};

	let cycleColor = fn(light : struct{on:Ref<Bool>, color:Ref<Int>}) -> Int {
		light.color := !light.color + 1;
		 if (!light.color > colors.red) {
			light.color := colors.green;
		}
		!light.color;
	};

	let toggle = fn(light : struct{on:Ref<Bool>, color:Ref<Int>}) -> Bool {
		light.on := !light.on;
		!light.on;
	};

	let light = newLight(true, colors.green);

	println !light.on;
	println !light.color;
	println toggle(light);
	println !light.on;
	println cycleColor(light);
	println !light.color;
	println cycleColor(light);
	println !light.color;
	println cycleColor(light);
	println !light.color;
}
