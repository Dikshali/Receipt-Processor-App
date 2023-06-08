package org.fetch.exercise.service;

import org.fetch.exercise.exception.ReceiptNotFoundException;
import org.fetch.exercise.model.Item;
import org.fetch.exercise.model.Receipt;
import org.fetch.exercise.util.PointsUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class ReceiptService {

    private final Map<String, Receipt> receipts;
    private final PointsUtil pointsUtil;

    @Autowired
    public ReceiptService(final PointsUtil pointsUtil) {
        receipts = new HashMap<>();
        this.pointsUtil = pointsUtil;
    }

    public Optional<Receipt> getReceipt(final String id) {
        return Optional.ofNullable(receipts.get(id));
    }

    public List<Receipt> getReceipts() {
        return new ArrayList<>(receipts.values());
    }

    public String processReceipt(final Receipt receipt) {
        final String id = generateUUID();
        receipts.put(id, receipt);
        return id;
    }

    public int getPoints(final String receiptId) throws ReceiptNotFoundException {
        final Receipt receipt = getReceipt(receiptId)
                .orElseThrow(
                        () -> new ReceiptNotFoundException(receiptId));
        int sum = 0;
        sum += pointsUtil.getAlphaNumericCharacterCount(receipt.getRetailer());
        sum += pointsUtil.isRoundNumber(receipt.getTotal()) ? 50 : 0;
        sum += pointsUtil.isMultipleOf(0.25, receipt.getTotal()) ? 25 : 0;
        sum += getPointsForNumberOfItems(receipt.getItems().size());
        sum += getPointsForItemDescription(receipt.getItems());
        sum += pointsUtil.isOdd(receipt.getPurchaseDate().getDayOfMonth()) ? 6 : 0;
        sum += pointsUtil.isTimeBetween2pmAnd4pm(receipt.getPurchaseTime()) ? 10 : 0;

        return sum;
    }

    public int getPointsForNumberOfItems(final int size) {
        return (size / 2) * 5;
    }

    public int getPointsForItemDescription(final List<Item> items) {
        int points = 0;
        for (final Item item : items) {
            points += getPointsForItemDescription(item.getShortDescription(), item.getPrice());
        }
        return points;
    }

    private int getPointsForItemDescription(final String description, final double price) {
        int points = 0;
        if (pointsUtil.isMultipleOf(3, description.trim().length())) {
            points = (int) Math.ceil(price * 0.2);
        }
        return points;
    }

    private String generateUUID() {
        final UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
