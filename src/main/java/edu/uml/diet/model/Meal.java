package edu.uml.diet.model;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Kurt Johnson on 3/13/2015.
 */
@Entity
@Table(name = "MEALS", schema = "", catalog = "DietTracker")
public class Meal {
    private Integer id;
    private String name;
    @ManyToOne    @JoinColumn(name = "day_id")
    private Day day;
    @OneToMany(mappedBy = "meal" )
    private Collection<Portion> portions;




    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne    @JoinColumn(name = "day_id")
    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    @OneToMany(mappedBy = "meal" )
    public Collection<Portion> getPortions() {
        return portions;
    }

    public void setPortions(Collection<Portion> portions) {
        this.portions = portions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Meal)) return false;

        Meal meal = (Meal) o;

        if (day != null ? !day.equals(meal.day) : meal.day != null) return false;
        if (id != null ? !id.equals(meal.id) : meal.id != null) return false;
        if (name != null ? !name.equals(meal.name) : meal.name != null) return false;
        if (portions != null ? !portions.equals(meal.portions) : meal.portions != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (day != null ? day.hashCode() : 0);
        result = 31 * result + (portions != null ? portions.hashCode() : 0);
        return result;
    }
}
