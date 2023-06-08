package org.fetch.exercise.service;

import org.fetch.exercise.exception.ReceiptNotFoundException;
import org.fetch.exercise.model.Item;
import org.fetch.exercise.model.Receipt;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class ReceiptServiceTest {

    private final ReceiptService receiptService;

    @Autowired
    public ReceiptServiceTest(final ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @Test
    void getPoints_givenReceipt_returnPoints() throws ReceiptNotFoundException {
        final Item item1 = new Item.Builder()
                .withShortDescription("Mountain Dew 12PK")
                .withPrice("6.49")
                .build();
        final Item item2 = new Item.Builder()
                .withShortDescription("Emils Cheese Pizza")
                .withPrice("12.25")
                .build();
        final Item item3 = new Item.Builder()
                .withShortDescription("Knorr Creamy Chicken")
                .withPrice("1.26")
                .build();
        final Item item4 = new Item.Builder()
                .withShortDescription("Doritos Nacho Cheese")
                .withPrice("3.35")
                .build();
        final Item item5 = new Item.Builder()
                .withShortDescription("   Klarbrunn 12-PK 12 FL OZ  ")
                .withPrice("12.00")
                .build();

        final List<Item> items = new ArrayList<>(List.of(item1, item2, item3, item4, item5));
        final Receipt receipt = new Receipt.Builder()
                .withRetailer("Target")
                .withPurchaseDate(LocalDate.parse("2022-01-01"))
                .withPurchaseTime("13:01")
                .withItems(items)
                .withTotal("35.35")
                .build();
        final String receiptId = receiptService.processReceipt(receipt);

        final int actualPoints = receiptService.getPoints(receiptId);
        Assertions.assertEquals(28, actualPoints);
    }

    @Test
    void getPoints_receiptIdDoNotExist_throwsException() {
        final String receiptId = "abc123";
        final ReceiptNotFoundException receiptNotFoundException = Assertions.assertThrows(
                ReceiptNotFoundException.class,
                () -> receiptService.getPoints(receiptId)
        );
        Assertions.assertTrue(receiptNotFoundException.getMessage()
                .contains("Receipt does not exist with id: abc123"));
    }

    @Test
    void processReceipt_givenReceipt_returnId() {
        final Item item1 = new Item.Builder()
                .withShortDescription("Pepsi - 12-oz")
                .withPrice("1.25")
                .build();
        final Item item2 = new Item.Builder()
                .withShortDescription("Dasani")
                .withPrice("1.40")
                .build();
        final List<Item> items = new ArrayList<>(List.of(item1, item2));
        final Receipt receipt = new Receipt.Builder()
                .withRetailer("Walgreens")
                .withPurchaseDate(LocalDate.parse("2022-01-02"))
                .withPurchaseTime("08:13")
                .withItems(items)
                .withTotal("2.65")
                .build();

        final String receiptId = receiptService.processReceipt(receipt);
        Assertions.assertNotNull(receiptId);
    }
}
