package ise1005.edu.fpt.vn.myrestaurant.dto;

import java.util.HashMap;

/**
 * Created by DungNA on 10/20/17.
 */

public class TableDTO {
    private int id;
    private String name;
    private String description;
    private int status;

    public TableDTO() {
    }

    public TableDTO(int id, String name, String description, int status) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TableDTO tableDTO = (TableDTO) o;

        return id == tableDTO.id;

    }

    @Override
    public String toString() {
        return "TableDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }

    public HashMap<String, String> toHashMap(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id",""+id);
        hashMap.put("name",name);
        hashMap.put("description",description);
        hashMap.put("status",""+status);

        return hashMap;
    }
}
