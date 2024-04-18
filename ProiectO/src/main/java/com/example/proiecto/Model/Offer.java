package com.example.proiecto.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Offer {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "description")
    private String description;
    @Basic
    @Column(name = "conditions")
    private String conditions;
    @Basic
    @Column(name = "Admin_id")
    private Integer adminId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return id == offer.id && Objects.equals(description, offer.description) && Objects.equals(conditions, offer.conditions) && Objects.equals(adminId, offer.adminId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, conditions, adminId);
    }
}
