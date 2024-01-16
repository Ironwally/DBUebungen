package dbClasses;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

@Entity
public class WeatherForecast {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private Date date;

    // Tagestiefstemperatur in Zentelgrad.
    @Column(name = "minTemp")
    private float minTemp;

    // Tageshoechstemperatur in Zentelgrad.
    @Column(name = "maxTemp")
    private float maxTemp;

    // Niederschlagsmenge

    @Column(name = "precipitation")
    private int precipitation;
    // Dauer des Sonnenscheins in Minuten

    @Column(name = "sunshine")
    private int sunshine;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "query_id")
    private Query query;

    public WeatherForecast(Long id, Date date, float minTemp, float maxTemp, int precipitation, int sunshine, @NotNull Query query) {
        this.id = id;
        this.date = date;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.precipitation = precipitation;
        this.sunshine = sunshine;
        this.query = query;
    }

    public Long getId() {
        return id;
    }
    public Date getDate() {
        return date;
    }
    public float getMinTemp() {
        return minTemp;
    }
    public float getMaxTemp() {
        return maxTemp;
    }
    public int getPrecipitation() {
        return precipitation;
    }
    public int getSunshine() {
        return sunshine;
    }
    @NotNull
    public Query getQuery() {
        return query;
    }
}
