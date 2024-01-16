package dbClasses;

import de.hska.iwii.db1.weather.model.Weather;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.util.ArrayList;

@Entity
public class Query {

    /**
     * unique id of the query
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * the weather-forecast information
     */
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "queries")
    private ArrayList<Weather> forecast;

    /**
     * Day when query was executed
     */
    @NotNull
    @Column(name = "queryDate")
    private Date queryDate;

    public Query(Long id, ArrayList<Weather> forecast, @NotNull Date queryDate) {
        this.id = id;
        this.forecast = forecast;
        this.queryDate = queryDate;
    }

    public Long getId() { return id; }
    public ArrayList<Weather> getForecast() { return forecast; }
    @NotNull
    public Date getQueryDate() { return queryDate; }
}
