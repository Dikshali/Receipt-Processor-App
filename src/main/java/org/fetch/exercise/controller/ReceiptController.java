package org.fetch.exercise.controller;

import org.fetch.exercise.exception.ReceiptNotFoundException;
import org.fetch.exercise.model.Receipt;
import org.fetch.exercise.service.ReceiptService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private static final Logger LOG = LoggerFactory.getLogger(ReceiptController.class);
    private final ReceiptService receiptService;

    @Autowired
    public ReceiptController(final ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/process")
    public ResponseEntity<Object> processReceipt(@Valid @RequestBody final Receipt receipt) {
        LOG.info("Create receipt for retailer = {}", receipt.getRetailer());
        final Map<String, String> response = new HashMap<>();
        response.put("id", receiptService.processReceipt(receipt));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<Object> getPoints(@PathVariable @NotNull final String id) throws ReceiptNotFoundException {
        LOG.info("Get points for receipt with id = {}", id);
        final Map<String, Integer> response = new HashMap<>();
        response.put("points", receiptService.getPoints(id));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receipt> getReceipt(@PathVariable final String id) {
        LOG.info("Get receipt with id = {}", id);
        final Optional<Receipt> optionalReceipt = receiptService.getReceipt(id);
        return optionalReceipt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.badRequest().build());
    }

    @GetMapping()
    public ResponseEntity<List<Receipt>> getReceipts() {
        LOG.info("Get all receipts");
        return ResponseEntity.ok(receiptService.getReceipts());
    }
}
