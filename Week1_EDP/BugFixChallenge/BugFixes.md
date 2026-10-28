# Bug Fix Documentation

## Bug 1: Blocking the EDT
- **Symptom:** UI freezes and stops responding to user interactions (cannot click buttons or move the window) when the "Process" button is clicked.
- **Cause:** A long-running simulation (`Thread.sleep(5000)`) was executed directly on the Event Dispatch Thread (EDT), freezing the thread responsible for rendering and user input.
- **Fix:** Delegated the sleep operation to a background worker thread (`new Thread(...).start()`) and forwarded the final label update to the EDT via `SwingUtilities.invokeLater()`.
- **Code Change:** Wrapped `Thread.sleep(5000)` inside `new Thread(() -> { ... }).start()` and placed `statusLabel.setText("Done!")` within `SwingUtilities.invokeLater(() -> { ... })`.

---

## Bug 2: Missing Listener
- **Symptom:** Clicking the "Increment" button produces no response; the count remains at 0.
- **Cause:** The button component was instantiated and placed on the layout, but had no `ActionListener` attached to listen for click events.
- **Fix:** Registered an `ActionListener` on `incrementButton`.
- **Code Change:** Added `incrementButton.addActionListener(e -> { count++; countLabel.setText("Count: " + count); });`.

---

## Bug 3: Wrong Thread
- **Symptom:** The time label may display erratic rendering glitches or throw thread-safety exceptions during continuous updates.
- **Cause:** Swing components are not thread-safe and must only be modified on the EDT. The background clock loop was directly modifying `timeLabel.setText()` off-thread.
- **Fix:** Dispatched the UI update back to the EDT using `SwingUtilities.invokeLater()`.
- **Code Change:** Encapsulated `timeLabel.setText(...)` inside `SwingUtilities.invokeLater(() -> { timeLabel.setText(currentTime); });` within the background thread loop.

---

## Bug 4: Memory Leak
- **Symptom:** Application memory consumption grows over time as buttons are added and cleared repeatedly.
- **Cause:** `buttonPanel.removeAll()` removed the UI components visually, but the `createdButtons` `ArrayList` maintained strong references to every button instance, preventing garbage collection.
- **Fix:** Cleared the references held by the backing data structure when the visual components are removed.
- **Code Change:** Added `createdButtons.clear();` inside the `removeButton` action listener.