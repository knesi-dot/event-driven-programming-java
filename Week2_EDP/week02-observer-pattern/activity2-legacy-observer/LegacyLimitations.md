# Limitations of java.util.Observer and java.util.Observable

## Limitation 1: Single Inheritance
Java only allows a class to inherit from one superclass. Because `Observable` is a class instead of an interface, any class that extends it cannot inherit from anything else. If my class already needs to extend a Swing component like `JPanel` or a custom base class, I can't use `Observable` at all.

## Limitation 2: Cannot Change the Notification Order
The notification order isn't guaranteed or predictable. Observers are notified in whatever arbitrary order `Observable` handles them internally. If one of my displays or logs depends on another component updating first, there is no way to control that execution order.

## Limitation 3: setChanged() is Confusing
`Observable` relies on an internal boolean flag to confirm that data actually changed. If I forget to call `setChanged()` before calling `notifyObservers()`, nothing happens. It fails silently without showing any error or warning, making it frustrating to track down why the displays aren't updating.

## Limitation 4: Pull Model by Default
The default `notifyObservers()` doesn't pass any data directly in the method call. The observer gets an `Observable` object reference, has to cast it to `(LegacyWeatherData)`, and manually call getter methods to pull the values. This adds unnecessary method calls and couples the observer directly to the subject class.

## Limitation 5: Deprecated Since Java 9
Java deprecated both classes in Java 9 because of poor design. The single-inheritance limitation, lack of thread safety, unhelpful `setChanged()` requirement, and lack of support for generics make them obsolete compared to writing custom interfaces or using `PropertyChangeListener`.