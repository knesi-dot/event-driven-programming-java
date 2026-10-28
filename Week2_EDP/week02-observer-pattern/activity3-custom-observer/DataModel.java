import java.util.ArrayList;
import java.util.List;

public class DataModel implements DataSubject {
    private List<DataObserver> observers = new ArrayList<>();
    private int value;

    @Override
    public void addObserver(DataObserver o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(DataObserver o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (DataObserver o : observers) {
            o.onDataChanged(value);
        }
    }

    public void setValue(int value) {
        this.value = value;
        notifyObservers();
    }
}