ctf grammar:
```letter = “a” | “b” | ... | “z”.
digit = “0” | “1” | ... | “9”.
identifier = letter {letter | digit}.
number = digit {digit}.
factor = identifier | number | “(“ expression “)”.
term = factor { (“*” | “/”) factor}.
expression = term {(“+” | “-”) term}.
computation = “computation”
{ “var” identifier “<-” expression “;” }
expression { “;” expression } “."
```
- assumes no syntax error in infile
- assumes no divide by 0 error
- no var name matches reserved word of grammar

