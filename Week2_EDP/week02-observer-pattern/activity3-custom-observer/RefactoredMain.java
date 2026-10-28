public class RefactoredMain {
    public static void main(String[] args) {
        DataModel model = new DataModel();

        model.addObserver(new LabelDisplay());
        model.addObserver(new BarChartDisplay());
        model.addObserver(new TextLogger());

        model.setValue(10);
        model.setValue(20);
        model.setValue(30);
    }
}