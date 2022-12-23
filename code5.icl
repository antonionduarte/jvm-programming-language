{
    let bestOf = fn(a1:Int, a2:Int, a3:Int, comparator : (Int, Int) -> Bool) -> Int {
        if (comparator(a1, a2)) {
            if (comparator(a1, a3)) {
                a1;
            } else {
                a3;
            }
        } else {
            if (comparator(a2, a3)) {
                a2;
            } else {
                a3;
            }
        }
    };
    let ascending = fn(a:Int, b:Int) -> Bool { a < b; };
    let descending = fn(a:Int, b:Int) -> Bool { a > b; };
    let prioritize = fn(n : Int, then : (Int, Int) -> Bool) -> (Int, Int) -> Bool {
        fn(a:Int, b:Int) -> Bool {
            if (a == n) {
                true;
            } else if (b == n){
                false;
            } else {
                then(a, b);
            }
        };
    };
    println bestOf(1, 2, 3, ascending);
    println bestOf(1, 2, 3, descending);
    println bestOf(1, 2, 3, prioritize(1, ascending));
    println bestOf(1, 2, 3, prioritize(2, ascending));
    println bestOf(1, 2, 3, prioritize(3, ascending));
}