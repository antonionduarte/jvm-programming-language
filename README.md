# compiler-design

Simple programming language developed for the JVM, it has got both an Interpreter and a Compiler and simple scripts to run it.
There are several code examples in the `./examples/` page.

## test expressions

```
{
 let x = 3;
 x + 1
}

# one liner:
{ let x = 3; x + 1 }
```

```
{ 
 let x = 3;
 let y = {
    let x = 5 + x;
    x + 10
  };
  x + y
}

# one liner:
{ let x = 3; let y = { let x = 5 + x; x + 10 }; x + y }
```
