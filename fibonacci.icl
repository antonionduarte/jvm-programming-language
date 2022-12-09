{
    let fibRec = new fn() -> Int {0;};
    let fib = fn(n : Int) -> Int {
        let fibRec = !fibRec;
        if(n==0){
            0;
        } else if (n<=2){
            1;
        } else {
            fibRec(n-1) + fibRec(n-2);
        }
    };
    fibRec := fib;
    let i = new 0;
    while(!i<=10){
        i := !i + 1;
        println(fib(!i));
    }
};