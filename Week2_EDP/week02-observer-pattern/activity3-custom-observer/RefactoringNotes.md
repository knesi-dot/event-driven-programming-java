# Refactoring Comparison Notes

## The Problem with Tight Coupling
In the original version, `DataModel` directly instantiated and invoked methods on specific display classes like `LabelDisplay` and `BarChartDisplay`. Whenever I wanted to add a new display, take one out, or rename a class, I had to open and edit `DataModel` directly. This breaks good object-oriented design and makes it impossible to unit test `DataModel` on its own.

## How the Observer Pattern Solves This
By using `DataSubject` and `DataObserver` interfaces, `DataModel` no longer knows or cares which concrete display classes exist. It just holds a generic list of `DataObserver` references and notifies each one when `setValue()` gets called. Adding `TextLogger` was simple and didn't require touching a single line of `DataModel` code.

## Comparison Table

| Aspect | Tightly Coupled | Observer Pattern |
| :--- | :--- | :--- |
| **Adding a new display** | Must modify `DataModel` directly | Just create the class implementing `DataObserver` and register it |
| **Testing `DataModel` in isolation** | Hard because it directly creates and depends on UI classes | Easy because we can test it alone or with mock observers |
| **Removing a display** | Must edit `DataModel` code to delete method calls | Just call `removeObserver()` or skip registering it |