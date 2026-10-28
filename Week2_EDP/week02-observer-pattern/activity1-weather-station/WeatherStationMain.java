public class WeatherStationMain {
    public static void main(String[] args) {
        WeatherData weatherData = new WeatherData();

        CurrentConditionsDisplay currentDisplay = new CurrentConditionsDisplay();
        StatisticsDisplay statsDisplay = new StatisticsDisplay();
        ForecastDisplay forecastDisplay = new ForecastDisplay();

        weatherData.registerObserver(currentDisplay);
        weatherData.registerObserver(statsDisplay);
        weatherData.registerObserver(forecastDisplay);

        weatherData.setMeasurements(25.0f, 65.0f, 1013.0f);
        weatherData.setMeasurements(28.0f, 70.0f, 1012.0f);
        weatherData.setMeasurements(22.0f, 60.0f, 1015.0f);

        System.out.println("\n--- Removing ForecastDisplay ---");
        weatherData.removeObserver(forecastDisplay);
        weatherData.setMeasurements(20.0f, 55.0f, 1018.0f);
    }
}