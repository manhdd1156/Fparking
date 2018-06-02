package ise1005.edu.fpt.vn.myrestaurant.dto;

import java.util.Date;

/**
 * Created by DungNA on 10/22/17.
 */

public class OrderDTO {
    private int id;
    private Date create_time;
    private int user_id;
    private UserDTO user;
    private int table_id;
    private TableDTO table;
    private int status;

    public OrderDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public int getTable_id() {
        return table_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public TableDTO getTable() {
        return table;
    }

    public void setTable(TableDTO table) {
        this.table = table;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", create_time=" + create_time +
                ", user_id=" + user_id +
                ", user=" + user +
                ", table_id=" + table_id +
                ", table=" + table +
                ", status=" + status +
                '}';
    }

}
