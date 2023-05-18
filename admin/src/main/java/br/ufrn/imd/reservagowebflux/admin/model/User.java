package br.ufrn.imd.reservagowebflux.admin.model;



import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "user")
public class User extends GenericModel<String> {
    @Id
    private String id;
    private String name;
    private Integer type;

    public User() {
    }

    public User(String id, String name, Integer type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public User(String name, Integer type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
