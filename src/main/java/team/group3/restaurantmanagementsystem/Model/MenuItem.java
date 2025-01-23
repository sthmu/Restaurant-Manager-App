package team.group3.restaurantmanagementsystem.Model;

public class MenuItem {
    private int id;
    private String name;
    private String description;
    private double price;
    private Ingredient[]  ingredientsList;

    public MenuItem(int id, String name, String description, double price, Ingredient[] ingredientsList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.ingredientsList = ingredientsList;
    }
    public MenuItem(int id, String name,  double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public Ingredient[] getIngredientsList() {
        return ingredientsList;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIngredientsList(Ingredient[] ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return " " + id +"\t"+ name + '\t' +"\t"+price;
    }
}
