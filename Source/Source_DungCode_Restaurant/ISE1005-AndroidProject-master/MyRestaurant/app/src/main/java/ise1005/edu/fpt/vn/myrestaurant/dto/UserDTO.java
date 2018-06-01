package ise1005.edu.fpt.vn.myrestaurant.dto;

import java.util.HashMap;

/**
 * Created by DungNA on 10/20/17.
 */

public class UserDTO {
    private int id;
    private String name;
    private String username;
    private String password;
    private int role_id;
    private String role_name;
    private int status;

    public UserDTO() {
    }

    public UserDTO(int id, String name, String username, String password, int role_id, int status) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role_id = role_id;
        this.status = status;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRole_name() {
        switch (role_id) {
            case 1:
                return "Manager";
            case 2:
                return "Cooker";
            default:
                return "Staff";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;
        return id == ((UserDTO) o).getId();
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role_id=" + role_id +
                ", status=" + status +
                '}';
    }

    public HashMap<String, String> toHashMap(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id",""+id);
        hashMap.put("name",name);
        hashMap.put("username",username);
        hashMap.put("role_name",getRole_name());

        return hashMap;
    }
}
