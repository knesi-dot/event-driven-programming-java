import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PropertyChangeDemo {

    static class Person {
        private final PropertyChangeSupport support = new PropertyChangeSupport(this);
        private String name;

        public void addPropertyChangeListener(PropertyChangeListener l) {
            support.addPropertyChangeListener(l);
        }

        public void setName(String newName) {
            String oldName = this.name;
            this.name = newName;
            support.firePropertyChange("name", oldName, newName);
        }
    }

    public static void main(String[] args) {
        Person person = new Person();

        person.addPropertyChangeListener(evt ->
            System.out.println("Listener A: " + evt.getPropertyName()
                + " changed from " + evt.getOldValue()
                + " to " + evt.getNewValue()));

        person.addPropertyChangeListener(evt ->
            System.out.println("Listener B: noticed name is now "
                + evt.getNewValue()));

        person.setName("Alice");
        person.setName("Bob");
    }
}