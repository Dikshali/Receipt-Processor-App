package org.fetch.exercise.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = Item.Builder.class)
public class Item {

    @NotBlank(message = "Invalid Short Description: EMPTY Short Description")
    @NotNull(message = "Invalid Short Description: Short Description is NULL")
    private final String shortDescription;

    @NotNull(message = "Invalid Price: Price is NULL")
    @Pattern(regexp = "^\\d+\\.?\\d{0,2}$", message = "Invalid price: Value should be a positive number up to 2 decimal places")
    private final String price;

    public Item(final Builder builder) {
        this.shortDescription = builder.shortDescription;
        this.price = builder.price;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public Double getPrice() {
        return Double.parseDouble(price);
    }


    public static final class Builder {
        private String shortDescription;

        private String price;

        public Builder withShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
            return this;
        }

        public Builder withPrice(String price) {
            this.price = price;
            return this;
        }

        public Item build() {
            return new Item(this);
        }
    }
}
