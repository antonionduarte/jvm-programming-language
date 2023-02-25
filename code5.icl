{
    let counter = {
        let val = new 0;
        struct {
            inc = fn () -> Int { val := !val + 1; };
            get = fn () -> Int { !val; }
        };
    };
    counter.inc();
    counter.inc();
    println (counter.get());
}
