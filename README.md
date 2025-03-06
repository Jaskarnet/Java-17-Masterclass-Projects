# Java 17 Masterclass Projects

This repository contains my implementations of mini challenges from the Java 17 Masterclass by Tim Buchalka. Each folder in this repository corresponds to a different project that demonstrates the concepts and techniques covered in the course.

## Folder Structure

Each project folder includes:
- **`src/`**: Source code for the project.

## Getting Started

### Prerequisites
- Java 17 or higher
- IntelliJ IDEA or another Java IDE

### Installation
1. Clone the repository: `git clone https://github.com/Jaskarnet/Java-17-Masterclass-Projects.git`
2. Navigate to the repository: `cd java-17-masterclass-projects`
3. Open the desired project folder in IntelliJ IDEA or your preferred Java IDE.

## **Project List & Summaries** 

### 1. Enhanced Switch Expression

#### **📂 Main Classes**  
- [`DayOfWeek`](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/001%20-%20EnhancedSwitchExpression/src/DayOfWeek.java)

#### **📖 Project Description**  
This project demonstrates the use of enhanced switch expressions in Java. It shows how to use the new syntax to replace traditional `switch` statements with more concise and flexible expressions.

#### **🔑 Key Takeaways**  
- **Switch Expressions**: They return values, unlike traditional `switch` statements.  
- **Arrow (`->`) Syntax**: Eliminates the need for `break` statements in `switch` cases.  
- **`yield` Keyword**: Used in block-based switch cases to return values.

---

### 2. Reading User Input

#### **📂 Main Classes**  
- [`Main`](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/002%20-%20ReadingUserInputChallenge/src/Main.java)

#### **📖 Project Description**  
This project demonstrates reading user input in Java. It uses the `Scanner` class to read multiple values and stores them in a list for further processing.

#### **🔑 Key Takeaways**  
- Uses `Scanner` to read multiple inputs and stores them in a list for easy management.

---

### 3. MinMax Challenge

#### **📂 Main Classes**  
- [`Main`](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/003%20-%20MinMaxChallenge/src/Main.java)

#### **📖 Project Description**  
This project allows users to input a series of numbers and tracks the minimum and maximum values entered, continuously updating as new numbers are entered.

#### **🔑 Key Takeaways**  
- Continuously reads inputs to track the minimum and maximum values in real-time.

---

### 4. Constructor Chaining

#### **📂 Main Classes**  
- [`Main`](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/004%20-%20ConstructorChallenge/src/Main.java)
- [`Customer`](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/004%20-%20ConstructorChallenge/src/Customer.java)

#### **📖 Project Description**  
This project demonstrates the concept of constructor chaining in Java. It shows how constructors can call other constructors within the same class to streamline object creation.

#### **🔑 Key Takeaways**  
- Demonstrates **constructor chaining**, where one constructor calls another within the same class.

---

### 5. Composition

#### **📂 Main Classes**  
- [`Main`](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/005%20-%20CompositionChallenge/src/Main.java)  
- [`CoffeeMaker`](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/005%20-%20CompositionChallenge/src/CoffeeMaker.java)  
- [`DishWasher`](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/005%20-%20CompositionChallenge/src/DishWasher.java)  
- [`Refrigerator`](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/005%20-%20CompositionChallenge/src/Refrigerator.java)  
- [`SmartKitchen`](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/005%20-%20CompositionChallenge/src/SmartKitchen.java)

#### **📖 Project Description**  
This project simulates a smart kitchen with appliances like a coffee maker, dishwasher, and refrigerator. The `SmartKitchen` class coordinates the tasks of these appliances based on flags passed to their methods.

#### **🔑 Key Takeaways**  
- **Composition vs. Inheritance**: Composition (HAS A) is more flexible than inheritance (IS A).  
- **Flexibility of Composition**: Allows easier changes and better reuse of components compared to inheritance.
- **Encapsulation**: Composition maintains better encapsulation as classes interact through interfaces rather than inheriting behavior.

---

### 7. Polymorphism

#### **📂 Main Classes**
- [`Main`](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/007%20-%20PolymorphismChallenge/src/Main.java)
- [`Car`](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/007%20-%20PolymorphismChallenge/src/Car.java)
- [`GasPoweredCar`](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/007%20-%20PolymorphismChallenge/src/Car.java)
- [`ElectricCar`](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/007%20-%20PolymorphismChallenge/src/Car.java)

#### **📖 Project Description**  
This project demonstrates the use of polymorphism in Java. It simulates a car race where different types of cars, including a regular `Car`, `GasPoweredCar`, and `ElectricCar`, perform actions like starting the engine and driving. The program uses polymorphism to call methods that are overridden in the subclasses. The `Car` class is the superclass, while `GasPoweredCar` and `ElectricCar` extend it and modify specific behaviors related to starting the engine and driving the car.

#### **🔑 Key Takeaways**  
- **Polymorphism**: Enables one method (`runRace`) to interact with different car types (`Car`, `GasPoweredCar`, `ElectricCar`) by using overridden methods.
- **Method Overriding**: Subclasses customize behaviors like `startEngine` and `runEngine`.
- **Constructor Overloading**: Allows creating cars with different properties, such as fuel consumption or battery size.

---

### 8. Minimum Element

#### **📂 Main Classes**
- [`Main`](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/008%20-%20MinimumElementChallenge/src/Main.java)

#### **📖 Project Description**  
This project demonstrates array manipulation in Java. The program reads a list of integers, finds the minimum value, and reverses the array in two ways: one that modifies the original array and another that creates a reversed copy.

#### **🔑 Key Takeaways**  
- Working with arrays to find minimum values and reverse arrays using different approaches.

---

### 9. ArrayList

#### **📂 Main Classes**
- [`Main`](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/009%20-%20ArrayListChallenge/src/Main.java)

#### **📖 Project Description**  
This project is a simple console-based grocery list application that allows users to add and remove items, displaying them in a formatted manner. The list is dynamically sorted, and the program handles user inputs for different actions like adding or removing items from the list.

#### **🔑 Key Takeaways**  
- **ArrayList Operations**: Demonstrates adding, removing, and sorting items in an `ArrayList`.  
- **String Manipulation**: Splits and formats input strings for display.  
- **User Input Handling**: Uses `Scanner` to handle and validate user choices.

---

### 10. LinkedList

#### **📂 Main Classes**
- [`Main`](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/010%20-%20LinkedListChallenge/src/Main.java)
- [`Town`](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/010%20-%20LinkedListChallenge/src/Main.java)

#### **📖 Project Description**  
This project demonstrates the use of a `LinkedList` to store and manage a list of `Town` objects. Users can navigate through the list of towns in both forward and backward directions using commands, while the towns are sorted based on their distance from Sydney.

#### **🔑 Key Takeaways**  
- **LinkedList**: Effective for adding, removing, and navigating elements.  
- **ListIterator**: Enables forward and backward traversal of a list.  
- **Duplicate Prevention**: Ensures no duplicates when adding new `Town` objects.

---

### 11. Autoboxing

#### **📂 Classes**
- [Main](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/011%20-%20AutoboxingChallenge/src/Main.java)
- [Bank](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/011%20-%20AutoboxingChallenge/src/Bank.java)
- [Customer](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/011%20-%20AutoboxingChallenge/src/Bank.java)
 
#### **📖 Description**
Simulates a bank system where autoboxing is used when storing and managing `double` values in `ArrayList<Double>` for transactions. Customers can add transactions and print their account statements.

#### **🔑 Key Takeaways**
- **Autoboxing**: Demonstrated how Java automatically converts primitive types (e.g., `double`) to their wrapper classes (e.g., `Double`) and vice versa when needed, making it easier to work with collections like `ArrayList`.

---

### 12. Abstract Classes

#### **📂 Classes**
- [ArtObject](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/012%20-%20AbstractChallenge/src/net/jaskar/ArtObject.java)
- [Furniture](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/012%20-%20AbstractChallenge/src/net/jaskar/Furniture.java)
- [OrderItem](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/012%20-%20AbstractChallenge/src/net/jaskar/OrderItem.java)
- [ProductForSale](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/012%20-%20AbstractChallenge/src/net/jaskar/ProductForSale.java)
- [Store](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/012%20-%20AbstractChallenge/src/net/jaskar/Store.java)

#### **📖 Description**
Simulates a store with products like art and furniture. Products extend the abstract class `ProductForSale`, each providing details through the `showDetails()` method.

#### **🔑 Key Takeaways**
- **Abstract Classes**: Like interfaces, abstract classes cannot be instantiated but can contain both abstract and concrete methods.
- **Fields**: Abstract classes can have instance fields (non-static, non-final), unlike interfaces.
- **Access Modifiers**: Abstract classes allow access modifiers for both concrete and abstract methods (except private for abstract methods).
- **Single Inheritance**: Abstract classes can extend only one class but can implement multiple interfaces.
- **Subclass Implementation**: Subclasses must implement all abstract methods unless they are also abstract.

#### **🛠 When to Use an Abstract Class**
- Share common code and fields among closely related classes (e.g., `Animal` with `name`, `age`).
- Provide default behavior for methods, with the flexibility for subclasses to override.
- Define non-static, non-final fields to manage object state (e.g., `name`, `age`).
- Allow subclasses to provide specific implementations for certain methods while sharing common behavior.

---

### 13. Interface Challenge

#### **📂 Classes**
- [Building](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/013%20-%20InterfaceChallenge/src/net/jaskar/Building.java)
- [Main](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/013%20-%20InterfaceChallenge/src/net/jaskar/Main.java)
- [Mappable](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/013%20-%20InterfaceChallenge/src/net/jaskar/Mappable.java)
- [UtilityLine](https://github.com/Jaskarnet/Java-17-Masterclass-Projects/blob/main/013%20-%20InterfaceChallenge/src/net/jaskar/UtilityLine.java)

#### **📖 Description**
The project demonstrates the use of an interface Mappable for different types of objects like buildings and utility lines. Each class implements the Mappable interface, providing specific behavior for labeling, shapes, markers, and JSON formatting.

#### **🔑 Key Takeaways**
- **Interfaces**: An interface defines the "what" (methods) an object can do, without providing the implementation details. Classes that implement the interface must provide the implementation for these methods.
- **Contract**: Interfaces form a contract between the class and the outside world, ensuring that the class will provide certain methods.
- **Instantiating Interfaces**: Interfaces cannot be instantiated. They define methods without implementation (implicitly `public abstract`), but can also include default methods with implementation and static methods.
- **Extending Interfaces**: An interface can extend another interface, creating a hierarchy of behaviors.
- **Decoupling**: Interfaces decouple the "what" from the "how," allowing you to plug in different implementations.

#### **🛠 When to Use an Interface**
- When you expect that unrelated classes will implement your interface (e.g., `Comparable`, `Cloneable`).
- When you want to specify behavior for a data type without concern for who implements it.
- When you need to separate behavior (e.g., implementing common behavior across different classes that are not in the same class hierarchy).



