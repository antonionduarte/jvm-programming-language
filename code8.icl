{
    let x = struct {
        a = 0;
        b = 2;
        c = new struct {
            d = new true
        }
    };
    println x.a;
    println x.b;
    println !(!x.c).d;
    (!x.c).d := false;
    println !(!x.c).d;
    let y = struct {
        d = new true
    };
    x.c := y;
    println !(!x.c).d;
    y.d := false;
    println !(!x.c).d;
}