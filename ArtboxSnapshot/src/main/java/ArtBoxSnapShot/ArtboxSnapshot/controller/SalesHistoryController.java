package ArtBoxSnapShot.ArtboxSnapshot.controller;


import ArtBoxSnapShot.ArtboxSnapshot.dto.SalesHistoryDTO;
import ArtBoxSnapShot.ArtboxSnapshot.model.SalesHistory;
import ArtBoxSnapShot.ArtboxSnapshot.service.SalesHistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("sales")
public class SalesHistoryController {

    private final SalesHistoryService salesHistoryService;

    public SalesHistoryController(SalesHistoryService salesHistoryService){
        this.salesHistoryService = salesHistoryService;
    }

    @PostMapping
    public ResponseEntity<SalesHistory> createSale(@RequestBody SalesHistoryDTO salesHistoryDTO) {
        SalesHistory sale = salesHistoryService.createSale(salesHistoryDTO);
        return ResponseEntity.ok(sale);
    }
}
