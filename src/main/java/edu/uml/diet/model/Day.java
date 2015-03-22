package edu.uml.diet.model;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Kurt Johnson on 3/13/2015.
 */
@Entity
@Table(name = "DAYS", schema = "", catalog = "DietTracker")
public class Day {
    private Integer id;
    private DateTime date;
    @OneToMany(mappedBy = "day" )
    private Collection<Meal> meals;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "date")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "day")
    public Collection<Meal> getMeals() {
        return meals;
    }

    public void setMeals(Collection<Meal> meals) {
        this.meals = meals;
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Day day = (Day) o;

        if (date != null ? !date.equals(day.date) : day.date != null) return false;
        if (id != null ? !id.equals(day.id) : day.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}
