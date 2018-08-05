package ise1005.edu.fpt.vn.myrestaurant.dto;

import java.io.Serializable;

/**
 * Created by DungNA on 10/22/17.
 */

public class OrderDetailDTO implements Serializable {
    private int id;
    private int order_id;
    private int product_id;
    private ProductDTO product;
    private TableDTO table;
    private double price;
    private int quantity;
    private String note;
    private int status;

    public OrderDetailDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public ProductDTO getProduct() {
        return product;
    }

    public void setProduct(ProductDTO product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public TableDTO getTable() {
        return table;
    }

    public void setTable(TableDTO table) {
        this.table = table;
    }

    @Override
    public String toString() {
        return "OrderDetailDTO{" +
                "id=" + id +
                ", order_id=" + order_id +
                ", product_id=" + product_id +
                ", product=" + product +
                ", table=" + table +
                ", price=" + price +
                ", quantity=" + quantity +
                ", note='" + note + '\'' +
                ", status=" + status +
                '}';
    }
}
