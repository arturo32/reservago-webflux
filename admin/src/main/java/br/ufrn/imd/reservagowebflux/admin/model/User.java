package br.ufrn.imd.reservagowebflux.admin.model;

import br.ufrn.imd.reservagomvc.model.GenericModel;
import jakarta.persistence.*;

@Entity
@Table(name="\"user\"")
public class User extends GenericModel<Long> {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;
    private String name;
    private Integer type;

    public User() {
    }

    public User(Long id, String name, Integer type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public User(String name, Integer type) {
        this.name = name;
        this.type = type;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
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
