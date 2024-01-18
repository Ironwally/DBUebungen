import de.hska.iwii.db1.weather.model.Weather;
import de.hska.iwii.db1.weather.model.WeatherForecast;
import de.hska.iwii.db1.weather.reader.WeatherReader;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

/**
 * Demo-Klasse fuer den Zugriff auf das Wetter der Stadt Karlsruhe. 
 */
public class DemoWeather {

	public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {

		//Start connection to DB
		jdbcTalker app = new jdbcTalker();
		WeatherReader reader = new WeatherReader();
		WeatherForecast forecast = reader.readWeatherForecast(10519);

		app.addWeather(forecast, new Date(System.currentTimeMillis()), 10519);

		app.addWeather(reader.readWeatherForecast(10517), new Date(System.currentTimeMillis()), 10517);
		app.addWeather(reader.readWeatherForecast(10516), new Date(System.currentTimeMillis()), 10516);
		app.addWeather(reader.readWeatherForecast(10515), new Date(System.currentTimeMillis()), 10515);
		app.addWeather(reader.readWeatherForecast(10514), new Date(System.currentTimeMillis()), 10514);

		app.getWeather(10509);

		app.getStations(2024,1,12, 5, 4);

		if (forecast != null) {
			for (Weather weather: forecast.getWeather()) {
				System.out.println(weather.getDate() + ", " + weather.getMinTemp() / 10.0 + ", " + weather.getMaxTemp() / 10.0);
			}
		}
	}
}


/*
	 1. Erzeugt ein WeatherReader-Objekt fuer die komplette
		Serverkommunikation. Fuer den Zugriff uber den
		Proxy der Hochschule muss der zweite Konstruktur mit
		den Proxy-Parametern verwendet werden.
		Proxy-Server: proxy.hs-karlsruhe.de
		Port des Proxy-Servers: 8888
	 2. Auslesen von Informationen ueber einen oder mehrere Orte.
	 Die Liste der Stationen ist hier verlinkt (4. Spalte enthaelt die ID):
	 https://www.dwd.de/DE/leistungen/klimadatendeutschland/statliste/statlex_html.html
	 Beispiele:
	 10519: Karlsruhe Durlach
	 10321: Stuttgart-Degerloch
	 Direktes Aufrufen der API:
	 https://dwd.api.bund.dev/
 */