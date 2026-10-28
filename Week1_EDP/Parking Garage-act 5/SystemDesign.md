# Event-Driven System Design
## System Name: Parking Garage System


## Part 1: Event Sources
Components that can generate events:
1. Entry Gate Button - Generates click events when a driver takes a ticket to enter.
2. Exit Gate Button - Generates click events when a driver pays and leaves.
3. Emergency Toggle - Generates an event when the operator switches emergency mode on or off.
4. Reset Button - Generates an event when staff resets the parking count back to zero.
5. System Clock/Timer - Generates timestamps for each action logged.


## Part 2: Events
All possible events in the system:

| Event Name | Source | Description |
| Car Enters | Entry Button | User clicks the button to enter the garage |
| Car Leaves | Exit Button | User clicks the button to exit after paying |
| Emergency Mode | Emergency Toggle | User switches the toggle to raise barriers during an emergency |
| Reset System | Reset Button | Staff clicks to clear out the current vehicle count |
| Garage Full | Entry Button logic | Triggered when total cars reaches capacity and entry is blocked |


## Part 3: Event Handlers

| Event | Handler Action |
| Car Enters | Checks if slots are open. If yes, it adds 1 to parked cars, lowers available spaces, logs the entry, and updates the text. If full, it displays an error saying the garage is full. |
| Car Leaves | Checks if there is at least one car inside. If yes, it subtracts 1 from parked cars, frees up a space, logs the exit, and updates the text. If empty, it warns the user. |
| Emergency Mode | Disables normal entry/exit buttons, marks the status red to show an active alert, and logs that barriers are kept open. When turned off, it enables the buttons again. |
| Reset System | Sets occupied spaces back to 0, resets available spots to 10, and adds a reset message to the log. |
| Garage Full | Changes the status text to red, warns the driver that no spaces are left, and prevents the count from exceeding 10. |


## Part 4: Event Flow Diagram
Diagram showing how events flow through the system:

User clicks button (e.g. Vehicle Entry)
       │
       ▼
JButton generates ActionEvent
       │
       ▼
Event goes into Java's EventQueue
       │
       ▼
Event Dispatch Thread (EDT) sends event to listener
       │
       ▼
ActionListener runs logic (checks capacity, updates count)
       │
       ▼
UI updates on screen (statusLabel, capacityLabel, eventLog)


## Part 5: Potential Issues
1. Negative car count: Someone could click the exit button when no cars are in the garage. I handled this by checking `occupiedSpaces > 0` before letting an exit happen.
2. Overfilling the garage: If drivers keep clicking entry when spaces reach 10, it could overbook. I prevented this by checking if the garage is full before adding another car.
3. UI freezing up: If we add card payments or database saving later, running that directly on the button click would freeze the GUI. We would need to run those operations in a background thread and use `SwingUtilities.invokeLater()` to update the labels.