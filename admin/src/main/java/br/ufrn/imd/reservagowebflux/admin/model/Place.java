package br.ufrn.imd.reservagowebflux.admin.model;

import br.ufrn.imd.reservagowebflux.base.model.GenericModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "place")
public class Place extends GenericModel<String> {
    @Id
    private String id;

    private boolean available;
    private double stars;
    private double valuePerDay;
    private String name;
    private String location;
    private String description;
    private Integer daysAvailable;
    private User host;
    private Integer maxNumberOfGuests;

    public Place() {
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public double getStars() {
        return stars;
    }

    public void setStars(double stars) {
        this.stars = stars;
    }

    public double getValuePerDay() {
        return valuePerDay;
    }

    public void setValuePerDay(double value) {
        this.valuePerDay = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Integer daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User hostId) {
        this.host = hostId;
    }

    public Integer getMaxNumberOfGuests() {
        return maxNumberOfGuests;
    }

    public void setMaxNumberOfGuests(Integer maxNumberOfGuests) {
        this.maxNumberOfGuests = maxNumberOfGuests;
    }
}
