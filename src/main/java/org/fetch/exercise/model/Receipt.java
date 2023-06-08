package org.fetch.exercise.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = Receipt.Builder.class)
public class Receipt {

    @NotBlank(message = "Invalid Retailer name: EMPTY Retailer name")
    @NotNull(message = "Invalid Retailer name: Retailer name is NULL")
    private final String retailer;
    @NotNull(message = "Invalid Purchase Date: Purchase Date is NULL")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private final LocalDate purchaseDate;
    @NotBlank(message = "Invalid Purchase time: EMPTY Purchase time")
    @NotNull(message = "Invalid Purchase time: Purchase time is NULL")
    @Pattern(regexp = "^(0\\d|1\\d|2[0-3]):[0-5]\\d$", message = "Purchase Time 24 hour format should be a valid HH:MM")
    private final String purchaseTime;
    @NotEmpty(message = "Item list cannot be empty.")
    @Size(min = 1)
    @Valid
    private final List<Item> items;

    @NotBlank(message = "Invalid Total price: EMPTY Total price")
    @NotNull(message = "Invalid Total price: Total is NULL")
    @Pattern(regexp = "^\\d+\\.?\\d{0,2}$", message = "Invalid Total price: Value should be a positive number up to 2 decimal places")
    private final String total;

    public Receipt(final Builder builder) {
        this.retailer = builder.retailer;
        this.purchaseDate = builder.purchaseDate;
        this.purchaseTime = builder.purchaseTime;
        this.items = builder.items;
        this.total = builder.total;
    }

    public String getRetailer() {
        return retailer;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public LocalTime getPurchaseTime() {
        return LocalTime.parse(purchaseTime);
    }

    public List<Item> getItems() {
        return items;
    }

    public Double getTotal() {
        return Double.parseDouble(total);
    }

    public static final class Builder {
        private String retailer;
        private LocalDate purchaseDate;
        private String purchaseTime;
        private List<Item> items;
        private String total;

        public Builder withRetailer(String retailer) {
            this.retailer = retailer;
            return this;
        }

        public Builder withPurchaseDate(LocalDate purchaseDate) {
            this.purchaseDate = purchaseDate;
            return this;
        }

        public Builder withPurchaseTime(String purchaseTime) {
            this.purchaseTime = purchaseTime;
            return this;
        }

        public Builder withItems(List<Item> items) {
            this.items = items;
            return this;
        }

        public Builder withTotal(String total) {
            this.total = total;
            return this;
        }

        public Receipt build() {
            return new Receipt(this);
        }
    }
}
