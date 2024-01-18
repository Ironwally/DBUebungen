package dbClasses;

import jakarta.persistence.*;
import org.jetbrains.annotations.NotNull;

import java.sql.Date;

@Entity
public class Query_db {

    /**
     * unique id of the query
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Day when query was executed
     */
    @NotNull
    @Column(name = "queryDate")
    private Date queryDate;

    public Query_db(Long id, @NotNull Date queryDate) {
        this.id = id;
        this.queryDate = queryDate;
    }

    public Query_db() {

    }

    public Long getId() { return id; }
    @NotNull
    public Date getQueryDate() { return queryDate; }
}
