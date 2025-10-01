# Java Introduction

## Table of Contents

- [Variables](#variables)
- [Primitive Data Types](#primitive-data-types)
- [Reference Types](#reference-types)
- [Declaration vs Initialization vs Assignment](#declaration-vs-initialization-vs-assignment)
- [Literals](#literals)
- [Operators](#operators)
- [Control Flow](#control-flow)
- [Loops](#loops)
- [Methods](#methods)
- [Access Modifiers](#access-modifiers)

## Variables

A variable is a named storage location that holds a value. In Java each variable has:

- **Type** (what kind of value it can store)
- **Name** (identifier you use to get/set it)
- **Value** (the data currently stored)

Declaration and initialization are usually two steps:

```java
int x;         // declaration — reserves space for an integer named x
x = 5;         // initialization — store the value 5 into x
// or combined:
int y = 10;    // declare and initialize in one line
```

## Primitive Data Types

Java has 8 primitive types. These hold simple values directly (not objects).

| Type      | Use                                 | Typical Size  | Example Value                   |
| --------- | ----------------------------------- | ------------- | ------------------------------- |
| `byte`    | small integers                      | 8-bit         | `byte b = 10;`                  |
| `short`   | small integers                      | 16-bit        | `short s = 3000;`               |
| `int`     | default integer                     | 32-bit        | `int i = 123456;`               |
| `long`    | large integers                      | 64-bit        | `long L = 1_000_000L;` (note L) |
| `float`   | single precision decimals           | 32-bit        | `float f = 3.14F;` (note F)     |
| `double`  | double precision decimals (default) | 64-bit        | `double d = 3.14159;`           |
| `char`    | single Unicode character            | 16-bit        | `char c = 'A';`                 |
| `boolean` | true/false                          | JVM dependent | `boolean ok = true;`            |

### Notes

- Numeric literals without suffix are `int` (for integers) or `double` (for decimals). Add `L` or `F` when needed.
- Local variables must be initialized before use. Field (instance/static) variables get default values (0, false, '\u0000' etc).

## Reference Types

Reference types store a reference (pointer) to an object on the heap.

**Example:**

```java
String name = "Alice"; // name refers to a String object
int[] arr = {1,2,3};   // arr refers to an array object
```

- Reference variables can be `null` (no object).
- Methods operate differently on reference types (pass reference by value).

## Declaration vs Initialization vs Assignment

```java
int a;          // declaration
a = 2;          // assignment (giving a value)
a = a + 3;      // re-assignment (update value)
int b = 5;      // declaration + initialization
```

### Variable Scope and Lifetime

Where variables are accessible:

- **Local variables**: declared inside a method/loop — exist only while the method/block runs.
- **Instance (non-static) fields**: declared in a class, outside methods — one copy per object.
- **Static (class) fields**: declared with `static` — one copy shared by all objects of the class.
- **Parameters**: variables passed into methods — local to that method.

**Example:**

```java
class Example {
    static int count;       // static field — shared across all instances
    private int id;         // instance field — unique per object
    void doSomething(int param) { // param is local to method
         int local = 10;           // local variable
    }
}
```

### Constants — Using `final`

`final` makes a variable constant (value can't change after assignment).

```java
final double PI = 3.14159;
```

**Naming convention:** `ALL_CAPS_WITH_UNDERSCORES`

### Type Conversion & Casting

- **Widening (safe)**: `int` → `long` (automatic).
- **Narrowing (requires cast)**: `double` → `int` (may lose info).

```java
int i = 10;
long L = i;            // widening — OK
double d = 9.7;
int j = (int) d;       // narrowing — explicit cast, j becomes 9 (decimal lost)
```

## Literals

How to write values:

- **Integer**: `123`, `0`, `0b101` (binary), `0xFF` (hex)
- **Long**: `123L`
- **Float**: `3.14F`
- **Double**: `3.14`
- **Char**: `'A'`
- **String**: `"hello world"`

## Operators

Brief overview with examples:

- **Arithmetic**: `+ - * / %`
- **Assignment**: `=`, `+=`, `-=`
- **Increment/decrement**: `++`, `--`
- **Relational**: `==`, `!=`, `>`, `<`, `>=`, `<=`
- **Logical**: `&&` (AND), `||` (OR), `!` (NOT)
- **Ternary**: `condition ? valueIfTrue : valueIfFalse`

**Example:**

```java
int a = 5, b = 2;
int sum = a + b;                // 7
double div = (double)a / b;     // 2.5 — cast to get decimal
boolean check = (a > b) && (b > 0); // true
int max = (a > b) ? a : b;      // ternary operator
```

## Control Flow

How programs decide & repeat.

### If / Else

```java
if (score >= 50) {
    System.out.println("Pass");
} else {
    System.out.println("Fail");
}
```

### Switch

```java
switch (day) {
    case 1: System.out.println("Mon"); break;
    case 2: System.out.println("Tue"); break;
    default: System.out.println("Other");
}
```

## Loops

- `for`, `while`, `do-while`, enhanced for (for-each for arrays/collections)

```java
for (int i = 0; i < 5; i++) System.out.println(i);
for (String s : list) System.out.println(s); // enhanced for loop
```

### 1. Using for loop

```java
public class LoopExample {
     public static void main(String[] args) {
       System.out.println("Using for loop:");
        for(int i = 1; i <= 10; i++) {
           System.out.print(i + " ");
        }
    }
}
```

**Explanation:**

- `int i = 1` → Initialization (starts at 1)
- `i <= 10` → Condition (loop runs while true)
- `i++` → Increment by 1 after each iteration
- `System.out.print(i + " ")` → Prints numbers in a single line

**Output:**

```
Using for loop:
1 2 3 4 5 6 7 8 9 10
```

### 2. Using while loop

```java
public class LoopExample {
    public static void main(String[] args) {
         System.out.println("\nUsing while loop:");
        int i = 1; // Initialization
        while(i <= 10) { // Condition
            System.out.print(i + " ");
            i++; // Increment
        }
    }
}
```

**Explanation:**

- Initialization happens before the loop (`i = 1`)
- Condition checked before each iteration
- Increment inside the loop
- If condition is false initially, loop won't execute

**Output:**

```
Using while loop:
1 2 3 4 5 6 7 8 9 10
```

### 3. Using do-while loop

```java
public class LoopExample {
    public static void main(String[] args) {
        System.out.println("\nUsing do-while loop:");
        int i = 1; // Initialization
        do {
            System.out.print(i + " ");
            i++; // Increment
        } while(i <= 10); // Condition checked after loop
    }
}
```

**Explanation:**

- `do-while` executes at least once even if condition is false initially
- Condition is checked after execution

**Output:**

```
Using do-while loop:
1 2 3 4 5 6 7 8 9 10
```

### ✅ Summary of Loops

| Loop Type  | Condition Checked | Minimum Execution | Usage                                |
| ---------- | ----------------- | ----------------- | ------------------------------------ |
| `for`      | Before            | May not execute   | Known number of iterations           |
| `while`    | Before            | May not execute   | Condition-based iteration            |
| `do-while` | After             | Executes once     | When loop must execute at least once |

## Methods

A method groups statements into a reusable block. Signature parts:

- access modifier (public/private), return type, method name, parameters, body.

```java
public int add(int x, int y) { // method returns int, takes two int params
     return x + y;
}
```

- `void` means no return value.
- Method overloading: same name, different parameter lists.
- Parameters are local variables inside the method.

### 1. What is a Method?

- A method is a block of code designed to perform a specific task.
- Helps in reusing code, improving readability, and modular programming.
- Methods can take inputs (parameters), perform operations, and return outputs (return values).

### 2. Purpose of Methods

1. **Code Reusability** – Write once, use multiple times.
2. **Modularity** – Break a program into smaller, manageable pieces.
3. **Improved Readability** – Easier to understand logic.
4. **Easier Debugging & Maintenance** – If a method has an issue, you only fix it in one place.
5. **Avoid Code Duplication** – No need to write the same logic multiple times.

### 3. Structure of a Method

```java
modifier returnType methodName(parameters) {
    // Method body
    // Statements
    return value; // (if returnType is not void)
}
```

**Components:**

| Part         | Description                                                      |
| ------------ | ---------------------------------------------------------------- |
| modifier     | Access specifier (public, private, etc.)                         |
| returnType   | Type of value returned (int, double, String, or void if nothing) |
| methodName   | Name of the method (follows naming conventions)                  |
| parameters   | Input values passed to the method (can be empty)                 |
| method body  | The code statements to execute                                   |
| return value | Value returned to caller (optional if void)                      |

### 4. Types of Methods

**1. No parameters, no return value**

```java
public void greet() {
    System.out.println("Hello, Java!");
}
```

**2. Parameters, no return value**

```java
public void greetUser(String name) {
    System.out.println("Hello, " + name + "!");
}
```

**3. No parameters, with return value**

```java
public int getRandomNumber() {
     return 42;
}
```

**4. Parameters with return value**

```java
public int add(int a, int b) {
     return a + b;
}
```

### 5. Example Program Using Methods

```java
public class MethodExample {

    // Method 1: No parameter, no return
    public void greet() {
        System.out.println("Welcome to Java Methods!");
    }

    // Method 2: Parameter, with return
    public int add(int x, int y) {
         return x + y;
    }

    public static void main(String[] args) {
        MethodExample obj = new MethodExample();
        // Call method with no parameter
        obj.greet();
        // Call method with parameters and return value
        int sum = obj.add(10, 20);
        System.out.println("Sum = " + sum);
    }
}
```

**🔹 Output:**

```
Welcome to Java Methods!
Sum = 30
```

### 6. Calling a Method

- **Object Method**: Called using an object (non-static methods).
- **Static Method**: Called using class name or directly from main (if in same class).

**Example of Static Method:**

```java
public static int multiply(int a, int b) {
    return a * b;
}
```

**Called as:**

```java
int result = multiply(5, 6); // no object needed
```

### 7. Key Points

- Methods can call other methods.
- Parameters are passed by value in Java.
- Methods make the program clean, readable, and maintainable.
- Can be overloaded (same name, different parameters) for flexibility.

## Access Modifiers

Access modifiers control the visibility and accessibility of classes, methods, and variables in Java. They determine which parts of your code can access other parts.

### Types of Access Modifiers

| Modifier                | Visibility           | Description                                          |
| ----------------------- | -------------------- | ---------------------------------------------------- |
| `public`                | Everywhere           | Accessible from any class in any package             |
| `protected`             | Package + Subclasses | Accessible within the same package and by subclasses |
| `default` (no modifier) | Package only         | Accessible only within the same package              |
| `private`               | Class only           | Accessible only within the same class                |

### 1. Public Access Modifier

**Most permissive** - accessible from anywhere.

```java
public class PublicExample {
    public int publicVariable = 10;

    public void publicMethod() {
        System.out.println("This is a public method");
    }
}

// Can be accessed from anywhere
PublicExample obj = new PublicExample();
obj.publicVariable = 20;  // OK
obj.publicMethod();       // OK
```

### 2. Protected Access Modifier

Accessible within the same package and by subclasses (even in different packages).

```java
package com.example;

public class Parent {
    protected int protectedVariable = 5;

    protected void protectedMethod() {
        System.out.println("This is a protected method");
    }
}

// In the same package
public class SamePackageClass {
    public void test() {
        Parent parent = new Parent();
        parent.protectedVariable = 10;  // OK - same package
        parent.protectedMethod();       // OK - same package
    }
}

// In a different package but extends Parent
package com.other;
import com.example.Parent;

public class Child extends Parent {
    public void test() {
        protectedVariable = 15;  // OK - subclass access
        protectedMethod();       // OK - subclass access
    }
}
```

### 3. Default (Package-Private) Access Modifier

Accessible only within the same package (no keyword needed).

```java
package com.example;

class DefaultExample {  // No public keyword = default access
    int defaultVariable = 3;

    void defaultMethod() {
        System.out.println("This is a default method");
    }
}

// In the same package
public class SamePackageTest {
    public void test() {
        DefaultExample obj = new DefaultExample();
        obj.defaultVariable = 5;  // OK - same package
        obj.defaultMethod();      // OK - same package
    }
}

// In a different package - this would cause compilation error
package com.other;
import com.example.DefaultExample;  // Error: DefaultExample is not public
```

### 4. Private Access Modifier

**Most restrictive** - accessible only within the same class.

```java
public class PrivateExample {
    private int privateVariable = 1;

    private void privateMethod() {
        System.out.println("This is a private method");
    }

    public void publicMethod() {
        // Can access private members within the same class
        privateVariable = 2;  // OK - same class
        privateMethod();      // OK - same class
    }
}

// Outside the class - this would cause compilation error
public class TestClass {
    public void test() {
        PrivateExample obj = new PrivateExample();
        obj.privateVariable = 5;  // Error: privateVariable has private access
        obj.privateMethod();      // Error: privateMethod() has private access
    }
}
```

### 5. Complete Example with All Access Modifiers

```java
package com.example;

public class AccessModifierExample {
    // Public - accessible everywhere
    public String publicField = "Public";

    // Protected - accessible in same package and subclasses
    protected String protectedField = "Protected";

    // Default - accessible only in same package
    String defaultField = "Default";

    // Private - accessible only in this class
    private String privateField = "Private";

    // Public method
    public void publicMethod() {
        System.out.println("Public method called");
        // Can access all fields within the same class
        System.out.println(privateField);
    }

    // Protected method
    protected void protectedMethod() {
        System.out.println("Protected method called");
    }

    // Default method
    void defaultMethod() {
        System.out.println("Default method called");
    }

    // Private method
    private void privateMethod() {
        System.out.println("Private method called");
    }

    // Public method that uses private method (encapsulation)
    public void usePrivateMethod() {
        privateMethod();  // OK - calling private method from same class
    }
}
```

### 6. Best Practices

1. **Use `private` by default** - Start with the most restrictive access and increase as needed
2. **Use `public` for API methods** - Methods that other classes need to use
3. **Use `protected` for inheritance** - When you want subclasses to access but not external classes
4. **Use `default` for package organization** - When you want to group related functionality

### 7. Access Modifier Summary

| Access Level | Same Class | Same Package | Subclass (Different Package) | Different Package |
| ------------ | ---------- | ------------ | ---------------------------- | ----------------- |
| `public`     | ✅         | ✅           | ✅                           | ✅                |
| `protected`  | ✅         | ✅           | ✅                           | ❌                |
| `default`    | ✅         | ✅           | ❌                           | ❌                |
| `private`    | ✅         | ❌           | ❌                           | ❌                |

**Key Points:**

- Access modifiers help implement **encapsulation** (data hiding)
- They control the **interface** of your classes
- Use the **principle of least privilege** - give minimum access needed
- `private` fields with `public` getter/setter methods is a common pattern
