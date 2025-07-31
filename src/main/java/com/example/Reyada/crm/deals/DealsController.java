package com.example.Reyada.crm.deals;

import com.example.Reyada.crm.deals.data.Deal;
import com.example.Reyada.crm.deals.services.DealsServices;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/crm/deals")
public class DealsController {
    @Autowired
    private DealsServices dealsService;

    @GetMapping("")
    public ResponseEntity<?> getDeals() {
        System.out.println("23807480273847u23984u79.");
        List<Deal> deals;
        try {

          deals = dealsService.fetchDeals();
            dealsService.addDealsTodb(deals);
            return ResponseEntity.ok(deals);
        } catch (Exception e) {
         deals= dealsService.fetchDealsOffline();
            return ResponseEntity.ok(deals);
        }
    }



    @GetMapping("/{id}")
    public Deal getOne(@PathVariable Long id) {
        return dealsService.fetchDealById(id);
    }
    @PutMapping("/update/{id}")
    public void update(
            @PathVariable Long id,
            @RequestBody UpdateRequest req
    ) {
        dealsService.updateContractAndTax(id, req.contract, req.taxRegistrationId);
    }

    @PostMapping("/{id}/won")
    public void markWon(@PathVariable Long id) {
        dealsService.markDealWon(id);
    }

    public static class UpdateRequest {
        public String contract;
        public String taxRegistrationId;
    }

}



