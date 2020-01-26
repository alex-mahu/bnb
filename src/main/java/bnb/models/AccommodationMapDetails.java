package bnb.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import static java.lang.String.format;

public class AccommodationMapDetails {
    private String title;
    private String name;
    private String price;
    private String rating;

    public AccommodationMapDetails(String title, String name, String price, String rating) {
        this.title = title;
        this.name = name;
        this.price = price;
        this.rating = rating;
    }

    @Override
    public String toString() {
        final String template = "{%n\"title\":\"%s\",%n\"name\":\"%s\",%n\"price\":\"%s\",%n\"rating\":\"%s\"%n}";
        return format(template, title, name, price, rating);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        AccommodationMapDetails that = (AccommodationMapDetails) o;

        return new EqualsBuilder()
                .append(title, that.title)
                .append(name, that.name)
                .append(price, that.price)
                .append(rating, that.rating)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(title)
                .append(name)
                .append(price)
                .append(rating)
                .toHashCode();
    }
}
