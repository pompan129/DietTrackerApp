package edu.uml.diet.model;

import javax.persistence.*;

/**
 * Created by Kurt Johnson on 3/13/2015.
 */
@Entity
@Table(name = "PORTIONS", schema = "", catalog = "DietTracker")
public class Portion {
    private Integer id;
    private Double portionSize;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "food_id")
    private BasicFood food;
    @ManyToOne
    @JoinColumn(name = "meal_id")
    private Meal meal;

    public Portion(){ this.portionSize = 1.0;}; //for persistence mapping

    public Portion(BasicFood food){
        this.food = food;
        this.portionSize = 1.0;
    }



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //@ManyToOne
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "food_id")
    //@JoinColumn(name = "food_id", referencedColumnName = "id", nullable = true)
    public BasicFood getFood(){
        return food;
    }

    public void setFood(BasicFood food){
        this.food = food;
    }

    @Basic
    @Column(name = "portion_size")
    public Double getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(Double portionSize) {
        this.portionSize = portionSize;
    }

    @ManyToOne
    @JoinColumn(name = "meal_id")
    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }


    /**
     * method to return calories present in this portion size or this food.
     *based on common household wieght. If no household weight present in DB, then calories
     * based on 100 grams.
     * @return int number of calories in portion size
     */
    @Transient
    public int getCalories(){
        double householdWeight = food.getHouseholdWeight();

        if(householdWeight == 0){householdWeight = 100.0;}
        return (int) ((food.getCalories()/100.0 )*householdWeight* portionSize);
    }

    /**
     * Method to return the common household weight of a food (ie. 1 cup, 1 tablespoon)
     * if there is no common wieht in the DB the method will return the standard 100 grams
     * @return String representing household weight description
     */
    @Transient
    public String getHouseholdWeightDescription(){
        String description = food.getHouseholdWeightDescription();
        if(description.equals("0")){description = "100 grams";}

        return description;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Portion portion = (Portion) o;

        if (id != null ? !id.equals(portion.id) : portion.id != null) return false;
        if (meal != null ? !meal.equals(portion.meal) : portion.meal != null) return false;
        if (portionSize != null ? !portionSize.equals(portion.portionSize) : portion.portionSize != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (portionSize != null ? portionSize.hashCode() : 0);
        result = 31 * result + (meal != null ? meal.hashCode() : 0);
        return result;
    }
}
