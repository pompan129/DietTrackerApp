package edu.uml.diet;

/**
 * Container class to represent one food item and it's portion size
 */
public class Portion {

    private BasicFood food;
    private Double portionSize;

    public Portion(BasicFood food) {
        this.food = food;
        this.portionSize = 1.0;
    }

    public Portion(BasicFood food, Double portionSize) {
        this.food = food;
        this.portionSize = portionSize;
    }

    public BasicFood getFood() {
        return food;
    }

    public Double getPortionSize() {
        return portionSize;
    }

    public void setPortionSize(Double portionSize) {
        this.portionSize = portionSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Portion portion = (Portion) o;

        if (!food.equals(portion.food)) return false;
        if (!portionSize.equals(portion.portionSize)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = food.hashCode();
        result = 31 * result + portionSize.hashCode();
        return result;
    }
}
