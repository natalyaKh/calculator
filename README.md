This is teo version of calculator

First version was build with using Reverse Polish notation and algoritm Dekster for reading at Stack

Work with symbols  

     - "+"
     - "-"
     - "/"
     - "*"
     - "("
     - ")"
     
Example - from string "200+5*3-2/1" we are getting stack [200, 5, 3, *, +, 2, 1, /, -] (using ReversePolishNotation)
and we can parse this stack using Algoritm of Dekster

Second Version - with using two arrays and stack. !!! Work only with symbols : 

     - "+"
     - "-"
     - "/"
     - "*"
     
Examples - from String "200+5*3-2/1" we are getting Collection {"200", "+", "15", "-", "2"] and we can solve it as a standart 
example without symbols with privilegies

Both version do not working with negativ numbers
