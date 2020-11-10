# Polymorphism 


---
### Today
+ A little more about constructors.
+ Example of inheritance.
+ Polymorphism.
+ `instanceof` and casting.
+ `StringBuilder`
+ Abstract classes.




---
### Resources
+ Deitel, Chapter 10.
+ Oracle's Java tutorial on [Interfaces and Inheritance](https://docs.oracle.com/javase/tutorial/java/IandI/index.html).
It also covers our next lecture.
+ The `StringBuilder` [java 8 documentation](https://docs.oracle.com/javase/8/docs/api/java/lang/StringBuilder.html)



---
### Constructors
+ Constructors of the superclass are not constructors of the subclass.
  + `super(...)` calls a constructor of the superclass.
  + Must appear as the **first line** in the constructor!
+ If no `super` constructor call, then java implicitly adds `super()` 
as a first line.
  + unless first line is a `this()` constructor call...
  


---
```java code-noblend
public class Building {
    Building() { System.out.print("b "); }
    Building(String name) {
		this(); 
		System.out.print("bn " + name);
    }
}
```
```java code-noblend
public class House extends Building {
	House() { System.out.print("h "); }
	House(String name) {
		this(); 
		System.out.print("hn " + name);
	}
}
```

What will `new House("x ")` print?
@css[fragment](b h hn x)


---
## Example

Before talking about **polymorphism**, 
Let's go through an example of inheritance. 

*It's also a good rehearsal of last week.*


---
Here's the plan:

@img[span-50](resources/shapes.png)


---
@code[java code-max code-noblend](src/Dot.java)
A point in 2D.



---
@code[java code-max code-noblend](src/Shape.java)
A shape, no real area or center.



---
@code[java code-max code-noblend](src/Circle.java)
@[1-10](can we omit the superclass constructor call?)
@[12-26](`getRadius` is new, and the others overridden.)



---
@code[java code-max code-noblend](src/Square.java)
@[1-14]
@[16-31](Calculating the center is a little more interesting.)


---
@code[java code-max code-noblend](src/MainForShapes.java)
`Circle` and `Square` objects assigned to a `Shape` variable?


---
## Polymorphism


If `B` is a subclass of `A`, then we say `B` is an `A`.
+ In this case, a variable of type `A` can hold a reference to an object of type `B`. 
+ Methods are called according to the **actual object**, and not the type.
+ But we can only call the methods appearing in the type.

@css[fragment](*What is this good for?*)
@css[fragment](**a lot of stuff!**)



---
@code[java code-max code-noblend](src/MainForShapes2.java)
This uses the same code for all subclasses of `Shape`.
However, we can't write `s.getRadius()`.


---
Let us write a simple class for keeping a set of `Shape`.

An important observation is that a function requiring a `Shape` parameter 
can be called with an instance of `Square`.

It should be used as follows:




---
@code[java code-max code-noblend](src/MainForShapeSet.java)
`getCenter()` returns the average center of all shapes in the set.

So we don't need a special `SquareSet` or `CircleSet`, and can also mix these. 




---
@code[java code-max code-noblend](src/ShapeSet.java)
@[2-16](Function `add` expects a `Shape` but is actually called with a subclass.)




---
What if we wanted an `double avgRadius()` method, which considers only the circles?
+ `Shape` does not have a `getRadius()` method.
+ If we know a shape variable actually references a `Circle`, we can **cast** it back (also called **downcast**).
+ To know, we can use `instanceof`.



---
@code[java code-max code-noblend](src/ShapeSet.java)
@[18-28]()



---
### `x instanceof A`
Checks if `x` is an `A`. 

It is true if `x` is a reference to an object of type `A` or a subclass of `A`.

---
### Casting
+ From a subclass to a superclass doesn't need a cast.
+ From a superclass to a subclass does need one. It is called **downcasting**.
  + If it doesn't fit, throws a `ClassCastException`, 
    e.g., when casting `Shape` to a `Circle`, but it is actually a `Square`.
+ Even before that, the compiler does not allow casts to unrelated types (like `Circle` to `Square`).

@css[fragment](**Try to avoid downcasting as much as possible!**)



---
### Arrays

If `Y` is a subclass of `X`, then
`Y[]` is a subclass of `X[]`.


---
## Question

Recalling that `Object` is the superclass of every class,
write a method that takes any non-primitive array, and returns a string representation of it.

Actually, this is exactly `Arrays.toString()`.

@css[fragment](*Let's first make our own version.*)



---
@code[java code-max code-noblend](src/MyArrays.java)
@[2-12](This `toString` has nothing to do with our normal `toString`! it is a static method taking an argument)
@[5](By Polymorphism, this will call the `toString` according to what `a[i]` really is, and not necessarily that of `Object`.)
@[2-12](What if `a` is `null`? what if some `a[i]` is `null`? what about the efficiency of `+`?)


---
Googling "java 8 source code Arrays", we find:

@img[span-50](resources/toString.png)


---
+ `StringBuilder` is efficient compared to using many `+`'s (explained next).
+ The code of `String.valueOf`:
```java code-noblend
public static String valueOf(Object obj) {
        return (obj == null) ? "null" : obj.toString();
}
```
+ `Arrays.toString` is overloaded and has many versions: 
one for each primitive array,
and this one.


---
### StringBuilder
+ When using + on two strings, a new string is created, and the two strings are copied there.
+ This results in $O(n^2)$ complexity, where $n$ is the number of `+` calls.
+ `StringBuilder` avoids this, and gives $O(m)$ complexity, where $m$ is the size of the resulting string.
+ Use it when creating a string from many (not $O(1)$) strings.



---
@code[java code-max code-noblend](src/StringBuilderExample.java)

When possible, avoid `insert`, and use only `append`.



---
### Abstract Classes

Recall that `Shape` had two "empty" methods:
```java code-noblend
public double getArea() { return 0.0; }
public Dot getCenter() { return null; } 
```	
+ No one should really use them, but we want subclasses to override them.
+ We do want them to be part of Shape - so we can use them in `ShapeSet`.

Moreover, we never really want a `Shape` instance.


---
@code[java code-max code-noblend](src/Shape2.java)
Replacing the original `Shape` with this will work perfectly without any more changes.





---
### Abstract Classes

+ An abstract class cannot be instantiated.
  + but it can have constructors!
+ If a class has an abstract method, then it must be declared `abstract`.
+ an abstract method has only the signature and no body.






---
### Example (from a test)

Here are two very similar classes. To avoid code duplication, write a new abstract class `AbsPrinter`, and rewrite `Printer1` and `Printer2` as subclasses of `AbsPrinter`.

@code[java code-max code-noblend](src/Printer1.java)



---
@code[java code-max code-noblend](src/Printer2.java)

@css[fragment](*solution:*)



---
@code[java code-max code-noblend](src/AbsPrinter.java)

---
@code[java code-max code-noblend](src/Printer1new.java)
and the second:
@code[java code-max code-noblend](src/Printer2new.java)




---
### A Simple Game

In this game there are two players.
+ In each round, each player is asked for a number.
+ The player who gave the larger number wins the round.
+ The total sum of numbers given by a player is the cost, and they want to minimize it.


---
@img[span-80](resources/game.png)

---
1. `nextMove()` is specific to each player - given the last move the second player made, it says what to play now.
  + In `ConstantPlayer`, this function is always some given number.
  + In `IncreasePlayer`, it is what the other player did, plus one.
1. `play` calls the abstract `nextMove()`, and also adds the cost to `totalCost`.
1. `Game` calls `play` of the players, compares, and prints the result.



---
@code[java code-max code-noblend](src/Game.java)
@[1-7](We will define `Player` soon.)
@[9-13](Each player is informed of the other player's last move.)
@[15-29]


---
@code[java code-max code-noblend](src/Player.java)
@[1-11](A player with a name.)
@[13-22](We will discuss `final` soon. Why not simply let `nextMove` do everything? )



---
@code[java code-max code-noblend](src/ConstantPlayer.java)
Our first player just sticks to one constant.



---
@code[java code-max code-noblend](src/IncreaserPlayer.java)
The second player uses the second player's last move.


---
@code[java code-max code-noblend](src/MainForGame.java)



---

The beauty of this is that we wrote `Game` so that it is instantiated with two `Player`s. 

We can plug in whatever `Player` we wish, without changing the code of `Game` and it will work correctly because of Polymorphism.
  
@box[](Methods are called according to the **object**, and not according to the **type** of variable holding the reference.)
  
  

---
### `final`
+ **Variables:** cannot change after initialization,
  + must happen upon declaration, or in constructor.
  + Notice that if the variable is a reference, the referenced object **can** change.
+ **Classes:** cannot be subclassed,
  + like `String`. Good for security and efficiency.
+ **Methods:** cannot be overridden in subclasses.
  + prevents possible bugs.

@css[fragment](*In our case, prevents players from changing the accounting of `cost`.*)



