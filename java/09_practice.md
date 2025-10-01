# Java Practice Programs

A collection of simple Java programs to practice core concepts with input/output examples.

## Table of Contents

- [Array Operations](#array-operations)
- [Basic Calculator](#basic-calculator)
- [String Operations](#string-operations)
- [Conditional Programs](#conditional-programs)
- [Loop Programs](#loop-programs)

---

## Array Operations

### 1. Find Maximum Number in Array

**Problem:** Given an array of numbers, find the maximum number.

```java
public class FindMax {
    public static void main(String[] args) {
        int[] numbers = {45, 12, 78, 23, 56, 89, 34, 67};

        int max = numbers[0]; // Assume first element is maximum

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > max) {
                max = numbers[i];
            }
        }

        System.out.println("Array: " + java.util.Arrays.toString(numbers));
        System.out.println("Maximum number: " + max);
    }
}
```

**Input:** `{45, 12, 78, 23, 56, 89, 34, 67}`

**Output:**

```
Array: [45, 12, 78, 23, 56, 89, 34, 67]
Maximum number: 89
```

### 2. Find Minimum Number in Array

**Problem:** Given an array of numbers, find the minimum number.

```java
public class FindMin {
    public static void main(String[] args) {
        int[] numbers = {45, 12, 78, 23, 56, 89, 34, 67};

        int min = numbers[0]; // Assume first element is minimum

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] < min) {
                min = numbers[i];
            }
        }

        System.out.println("Array: " + java.util.Arrays.toString(numbers));
        System.out.println("Minimum number: " + min);
    }
}
```

**Input:** `{45, 12, 78, 23, 56, 89, 34, 67}`

**Output:**

```
Array: [45, 12, 78, 23, 56, 89, 34, 67]
Minimum number: 12
```

### 3. Print Numbers Divisible by 3

**Problem:** Given an array of numbers, print all numbers divisible by 3.

```java
public class DivisibleByThree {
    public static void main(String[] args) {
        int[] numbers = {15, 7, 24, 18, 33, 12, 45, 8, 27};

        System.out.println("Array: " + java.util.Arrays.toString(numbers));
        System.out.println("Numbers divisible by 3:");

        for (int number : numbers) {
            if (number % 3 == 0) {
                System.out.println(number);
            }
        }
    }
}
```

**Input:** `{15, 7, 24, 18, 33, 12, 45, 8, 27}`

**Output:**

```
Array: [15, 7, 24, 18, 33, 12, 45, 8, 27]
Numbers divisible by 3:
15
24
18
33
12
45
27
```

### 4. Print Numbers Divisible by 3 and 5

**Problem:** Given an array of numbers, print all numbers divisible by both 3 and 5.

```java
public class DivisibleByThreeAndFive {
    public static void main(String[] args) {
        int[] numbers = {15, 7, 30, 18, 45, 12, 60, 8, 27, 90};

        System.out.println("Array: " + java.util.Arrays.toString(numbers));
        System.out.println("Numbers divisible by both 3 and 5:");

        for (int number : numbers) {
            if (number % 3 == 0 && number % 5 == 0) {
                System.out.println(number);
            }
        }
    }
}
```

**Input:** `{15, 7, 30, 18, 45, 12, 60, 8, 27, 90}`

**Output:**

```
Array: [15, 7, 30, 18, 45, 12, 60, 8, 27, 90]
Numbers divisible by both 3 and 5:
15
30
45
60
90
```

---

## Basic Calculator

### 5. Simple Calculator

**Problem:** Create a simple calculator that performs basic arithmetic operations.

```java
import java.util.Scanner;

public class SimpleCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Simple Calculator ===");
        System.out.print("Enter first number: ");
        double num1 = scanner.nextDouble();

        System.out.print("Enter second number: ");
        double num2 = scanner.nextDouble();

        System.out.print("Enter operation (+, -, *, /): ");
        char operation = scanner.next().charAt(0);

        double result = 0;

        switch (operation) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                if (num2 != 0) {
                    result = num1 / num2;
                } else {
                    System.out.println("Error: Division by zero!");
                    return;
                }
                break;
            default:
                System.out.println("Error: Invalid operation!");
                return;
        }

        System.out.println(num1 + " " + operation + " " + num2 + " = " + result);
        scanner.close();
    }
}
```

**Input:**

```
Enter first number: 10
Enter second number: 5
Enter operation (+, -, *, /): +
```

**Output:**

```
=== Simple Calculator ===
Enter first number: 10
Enter second number: 5
Enter operation (+, -, *, /): +
10.0 + 5.0 = 15.0
```

---

## String Operations

### 6. String Reversal

**Problem:** Reverse a given string.

```java
import java.util.Scanner;

public class StringReversal {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String input = scanner.nextLine();

        String reversed = "";

        for (int i = input.length() - 1; i >= 0; i--) {
            reversed += input.charAt(i);
        }

        System.out.println("Original: " + input);
        System.out.println("Reversed: " + reversed);
        scanner.close();
    }
}
```

**Input:** `Hello World`

**Output:**

```
Enter a string: Hello World
Original: Hello World
Reversed: dlroW olleH
```

### 7. Count Vowels in String

**Problem:** Count the number of vowels in a given string.

```java
import java.util.Scanner;

public class CountVowels {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a string: ");
        String input = scanner.nextLine().toLowerCase();

        int vowelCount = 0;
        String vowels = "aeiou";

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (vowels.indexOf(ch) != -1) {
                vowelCount++;
            }
        }

        System.out.println("String: " + input);
        System.out.println("Number of vowels: " + vowelCount);
        scanner.close();
    }
}
```

**Input:** `Programming is fun`

**Output:**

```
Enter a string: Programming is fun
String: programming is fun
Number of vowels: 5
```

---

## Conditional Programs

### 10. Grade Calculator

**Problem:** Calculate and display grade based on percentage.

```java
import java.util.Scanner;

public class GradeCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter your percentage: ");
        double percentage = scanner.nextDouble();

        String grade;

        if (percentage >= 90) {
            grade = "A+";
        } else if (percentage >= 80) {
            grade = "A";
        } else if (percentage >= 70) {
            grade = "B";
        } else if (percentage >= 60) {
            grade = "C";
        } else if (percentage >= 50) {
            grade = "D";
        } else {
            grade = "F";
        }

        System.out.println("Percentage: " + percentage + "%");
        System.out.println("Grade: " + grade);
        scanner.close();
    }
}
```

**Input:** `85`

**Output:**

```
Enter your percentage: 85
Percentage: 85.0%
Grade: A
```

### 11. Even or Odd Checker

**Problem:** Check if a number is even or odd.

```java
import java.util.Scanner;

public class EvenOddChecker {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = scanner.nextInt();

        if (number % 2 == 0) {
            System.out.println(number + " is even");
        } else {
            System.out.println(number + " is odd");
        }

        scanner.close();
    }
}
```

**Input:** `17`

**Output:**

```
Enter a number: 17
17 is odd
```

---

## Loop Programs

### 12. Multiplication Table

**Problem:** Print multiplication table for a given number.

```java
import java.util.Scanner;

public class MultiplicationTable {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = scanner.nextInt();

        System.out.println("Multiplication table for " + number + ":");

        for (int i = 1; i <= 10; i++) {
            int result = number * i;
            System.out.println(number + " x " + i + " = " + result);
        }

        scanner.close();
    }
}
```

**Input:** `7`

**Output:**

```
Enter a number: 7
Multiplication table for 7:
7 x 1 = 7
7 x 2 = 14
7 x 3 = 21
7 x 4 = 28
7 x 5 = 35
7 x 6 = 42
7 x 7 = 49
7 x 8 = 56
7 x 9 = 63
7 x 10 = 70
```

### 13. Sum of Natural Numbers

**Problem:** Calculate the sum of first n natural numbers.

```java
import java.util.Scanner;

public class SumOfNaturalNumbers {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int n = scanner.nextInt();

        int sum = 0;

        for (int i = 1; i <= n; i++) {
            sum += i;
        }

        System.out.println("Sum of first " + n + " natural numbers: " + sum);
        scanner.close();
    }
}
```

**Input:** `10`

**Output:**

```
Enter a number: 10
Sum of first 10 natural numbers: 55
```

### 14. Factorial Calculator

**Problem:** Calculate factorial of a given number.

```java
import java.util.Scanner;

public class FactorialCalculator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a number: ");
        int number = scanner.nextInt();

        long factorial = 1;

        for (int i = 1; i <= number; i++) {
            factorial *= i;
        }

        System.out.println("Factorial of " + number + " = " + factorial);
        scanner.close();
    }
}
```

**Input:** `5`

**Output:**

```
Enter a number: 5
Factorial of 5 = 120
```

---

## Practice Exercises

### Try These Yourself:

1. **Array Sum**: Calculate the sum of all elements in an array
2. **Palindrome Checker**: Check if a string is a palindrome
3. **Prime Number Checker**: Check if a number is prime
4. **Fibonacci Series**: Print first n numbers of Fibonacci series
5. **Temperature Converter**: Convert between Celsius and Fahrenheit
6. **Password Validator**: Check if a password meets certain criteria
7. **Word Counter**: Count words in a sentence
8. **Number Guessing Game**: Create a simple guessing game

### Tips for Practice:

- **Start Simple**: Begin with basic programs and gradually increase complexity
- **Test Your Code**: Always test with different inputs
- **Read Error Messages**: Learn from compilation and runtime errors
- **Practice Regularly**: Consistent practice is key to learning Java
- **Experiment**: Try modifying the programs to add new features

---

**Happy Coding! 🚀**
