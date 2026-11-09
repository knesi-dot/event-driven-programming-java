# Comparison of Event Notification Mechanisms in Java

| Feature | Custom Observer | java.util.Observer | PropertyChangeListener | JavaFX Bindings |
| :--- | :--- | :--- | :--- | :--- |
| **Push or Pull?** | Both possible | Pull | Push (carries old + new) | Push |
| **Is it deprecated?** | No | Yes (Java 9+) | No | No |
| **Requires inheritance?** | No (interface) | Yes (class) | No | No |
| **Thread-safe?** | Depends on implementation | No | Yes (with thread-safe listeners) | Yes |
| **Auto-generates boilerplate?** | No | No | Yes (via PropertyChangeSupport) | Yes |
| **Best for...** | Custom decoupled systems | Legacy code only | JavaBeans / Swing desktop | Modern JavaFX apps |
| **Can have multiple listeners?** | Yes | Yes | Yes | Yes |
| **Can be typed to send any data?** | Yes (custom arguments) | No (raw Object) | Yes (passes Object/primitives) | Yes |
| **Recommended today?** | Yes | No | Yes (for Swing/JavaBeans) | Yes |

---

## Which One Should You Use?

- For a plain Java project with custom requirements: **Custom Observer** (offers full control without extra dependencies).
- For a JavaFX desktop application: **JavaFX Bindings** (built specifically for reactive UI updates).
- For older desktop apps using Swing or JavaBeans: **PropertyChangeListener** (built into `java.beans` and handles listener registration boilerplate).
- Never use: **java.util.Observer / Observable** (deprecated since Java 9 and structurally flawed).

---

## Self-Check Answers

1. **Which mechanism carries both the old and new value of a property?**  
   `PropertyChangeListener`, via `PropertyChangeEvent.getOldValue()` and `getNewValue()`.

2. **Which mechanism requires a helper class to manage the listener list?**  
   `PropertyChangeListener`, which uses `PropertyChangeSupport` to hold listeners and fire events.

3. **Which two mechanisms are recommended for modern Java?**  
   Custom Observer interfaces and JavaFX Properties/Bindings.

4. **Which mechanism is deprecated and should be avoided?**  
   `java.util.Observer` and `java.util.Observable`.