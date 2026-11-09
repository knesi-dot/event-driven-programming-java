# Week 2: Observer Pattern Self-Assessment & Summary

## Learning Outcome 1: Design and Implement the Observer Pattern

### 1. UML Diagram Structure
```text
+-----------------------+              +-----------------------+
|      <<interface>>    |              |     <<interface>>     |
|        Subject        |<>----------->|       Observer        |
+-----------------------+              +-----------------------+
| + registerObserver()  |              | + update(...)         |
| + removeObserver()    |              +-----------------------+
| + notifyObservers()   |                          ^
+-----------------------+                          | implements
           ^                                       |
           | implements                +-----------------------+
+-----------------------+              |   ConcreteObserver    |
|    ConcreteSubject    |              +-----------------------+
+-----------------------+              | + update(...)         |
| - observers: List     |              +-----------------------+
| - stateData           |
+-----------------------+
| + getState()          |
| + setState()          |
+-----------------------+
2. Subject Interface (Java)
Java


public interface Subject {
    void registerObserver(Observer o);
    void removeObserver(Observer o);
    void notifyObservers();
}
3. Why does the Subject need a list of observers?
The Subject needs a collection to maintain references to all active subscribers. Since observers subscribe and unsubscribe dynamically at runtime, the list lets the Subject loop through and invoke the update() method on each listener whenever its internal state changes.

Learning Outcome 2: Evaluate Legacy java.util.Observer
4. Three problems with java.util.Observable
Class-based design: It is a class rather than an interface, so any model that extends it cannot inherit from another class due to Java's single inheritance rule.

Silent failures with setChanged(): If setChanged() is not called prior to notifyObservers(), no notifications are dispatched and no error is raised.

Unspecified notification order: There is no standard order in which registered observers are notified.

5. Why was this API deprecated?
It was officially deprecated in Java 9 because its class-based design limits reusability, it does not guarantee thread safety, it lacks support for type-safe generics, and it has been superseded by cleaner alternatives like JavaBeans property change listeners and custom interfaces.

6. What is the "pull model" and why is it harder to use?
In a pull model, the subject simply alerts observers that something changed without passing the actual data values. The observer receives a reference to the subject, casts it to the specific concrete class, and manually invokes getter methods. This creates tighter coupling and requires repetitive casting and getter calls.

Learning Outcome 3: Create a Custom Event Notification System
7. What methods must a custom Subject interface provide?
It must provide methods to register an observer (registerObserver / addObserver), deregister an observer (removeObserver), and trigger the update loop across all active listeners (notifyObservers).

8. How does the Subject notify all observers?
The Subject iterates over its internal collection of registered observers (typically an ArrayList) using an enhanced for-loop and invokes each observer's update() method, passing either updated arguments (push model) or a reference to itself.

9. How do you prevent a memory leak when observers are no longer needed?
Always invoke removeObserver() when a listener or UI component is closed, disposed of, or no longer in use. If an observer remains in the Subject's list, the Subject maintains a strong reference to it, preventing the Java Garbage Collector from freeing its memory (known as the "lapsed listener" problem).

Learning Outcome 4: Compare Different Mechanisms
10. Four event notification mechanisms in Java
Custom Observer / Subject interfaces

java.util.Observer and Observable (Legacy/Deprecated)

PropertyChangeListener with PropertyChangeSupport (JavaBeans/Swing)

JavaFX Properties and Bindings

11. Which one is best for JavaFX?
JavaFX Properties and Bindings, because they provide built-in reactive binding, listener invalidation, and UI synchronization directly integrated into the JavaFX toolkit.

12. Which one is deprecated?
java.util.Observer and java.util.Observable (deprecated since Java 9).

Final Reflection
The Observer pattern is a behavioral software design pattern that defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically. It forms the foundational backbone of event-driven programming because it decouples event producers (subjects) from event consumers (listeners). In event-driven architectures, user actions or state changes trigger events that listeners react to without either component needing tight compile-time coupling. For my own modular Java projects, I prefer using a custom Observer interface because it provides full control over data signatures, supports clean interface inheritance, and avoids third-party library dependencies. For GUI applications, PropertyChangeListener or modern JavaFX bindings are the practical choice because they eliminate boilerplate and provide built-in event dispatching.