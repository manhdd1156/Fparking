package ise1005.edu.fpt.vn.myrestaurant.dto;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by DungNA on 10/20/17.
 */

public class ProductDTO implements Serializable {
    private int id;
    private String name;
    private double price;
    private String description;
    private String image;

    public ProductDTO() {
    }

    public ProductDTO(int id, String name, double price, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDTO that = (ProductDTO) o;
        return id == that.id;

    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }

    public HashMap<String, String> toHashMap() {
        HashMap<String, String> hm = new HashMap<>();
        hm.put("id", "" + id);
        hm.put("name", name);
        hm.put("price", "" + price);
        hm.put("description", description);
        return hm;
    }
}
