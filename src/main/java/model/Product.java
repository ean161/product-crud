package model;

/**
 *
 * @author CE190990 - Nguyen Hoai An
 */
public class Product {
    private int id, quantity;
    private long price;
    private String name, desc;
    private Category cat;
    
    public Product() {}

    public Product(int id, String name, String desc, int quantity, long price, Category cat) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
        this.desc = desc;
        this.cat = cat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Category getCat() {
        return cat;
    }

    public void setCat(Category cat) {
        this.cat = cat;
    }

    
}
