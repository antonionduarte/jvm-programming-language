# compiler-design
Repository for the Interpreter and Compiler Design Course.

# notes:
## environment definitions

So the regular expression to define an environment would be something like:
```
<LCURLY>

<RCURLY>
```

```
{
  // Environment 1 
  let x = 3; // Env1.DefList[0]
  let y = 4; // Env1.DefList[1]
  let m = { // Env1.DefList[3]
    // Environment 2 
    let z = 5; // Env2.DefList[0]
    z + x // Body Environment 2
  }
  m + y // Body Environment 1
}
```

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
