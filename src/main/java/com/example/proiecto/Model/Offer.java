package com.example.proiecto.Model;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String details;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id") // This is the foreign key column in the Offer table.
    private Menu menu;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Offer offer = (Offer) o;
        return id == offer.id && Objects.equals(title, offer.title) && Objects.equals(details, offer.details);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, details);
    }
}
