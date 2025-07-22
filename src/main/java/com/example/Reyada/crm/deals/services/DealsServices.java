package com.example.Reyada.crm.deals.services;

import com.example.Reyada.crm.deals.dto.DealResponse;
import com.example.Reyada.crm.deals.data.Deal;
import com.example.Reyada.crm.deals.data.DealsRepo;
import com.example.Reyada.crm.deals.dto.UpdateResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DealsServices {

    private final RestTemplate restTemplate;
    private final String bitrixBaseUrl;
    private final String userId;
    private final String webhook;
    @Autowired
    DealsRepo repo;


    public DealsServices(RestTemplate restTemplate, DealsRepo repo) {
        this.restTemplate = restTemplate;
        this.bitrixBaseUrl = "https://b24-0r8mng.bitrix24.com";
        this.userId = "13";
        this.webhook = "s0wmul8rocahyacy";
        this.repo = repo;
    }

    public List<Deal> fetchDeals(String stageId) {
        String url = String.format("%s/rest/%s/%s/crm.deal.list",
                bitrixBaseUrl, userId, webhook);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("filter[=STAGE_ID]", stageId);

        params.add("select[]", "ID");
        params.add("select[]", "TITLE");
        params.add("select[]", "STAGE_ID");

        params.add("select[]", "ASSIGNED_BY_ID");
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        System.out.println("Filtering deals for stage ID: " + stageId);

        try {
            DealResponse resp = restTemplate.postForObject(url, request, DealResponse.class);
            if (resp != null && resp.getResult() != null) {
                List<Deal> deals = Arrays.asList(resp.getResult());

                return deals;
            }
        } catch (Exception e) {
            System.err.println("Error fetching deals: " + e.getMessage());
            throw new RuntimeException("Failed to fetch deals from Bitrix24: " + e.getMessage(), e);
        }

        throw new RuntimeException("Failed to fetch filtered deals: empty response");
    }


    public boolean updateDealInBitrix(Long id, Map<String,Object> fields) {
        String url = String.format("%s/rest/%s/%s/crm.deal.update",
                bitrixBaseUrl, userId, webhook);

        Map<String,Object> payload = new HashMap<>();
        payload.put("ID", id);
        payload.put("FIELDS", fields);


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Map<String,Object>> req = new HttpEntity<>(payload, headers);

        try {
            ResponseEntity<UpdateResponse> resp =
                    restTemplate.exchange(url, HttpMethod.POST, req, UpdateResponse.class);

            UpdateResponse body = resp.getBody();
            return body != null && Boolean.TRUE.equals(body.result);
        } catch (Exception ex) {
            throw new RuntimeException("Bitrix update failed: " + ex.getMessage(), ex);
        }
    }

    public List<Deal> addDealsTodb(List<Deal> deals) {

      return repo.saveAllAndFlush(deals);


    }


    public Deal fetchDealById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Deal not found: " + id));
    }

    @Transactional
    public Deal updateContractAndTax(Long id, String contract, String taxRegistrationId) {
        Deal deal = fetchDealById(id);
        deal.setContract(contract);
        deal.setTaxRegistrationId(taxRegistrationId);
        return repo.save(deal);
    }
    @Transactional
    public Deal markDealWon(Long id) {
        Deal deal = fetchDealById(id);
        deal.setStageId("WON");

        Map<String, Object> fields = new HashMap<>();
        fields.put("STAGE_ID", "WON");
        updateDealInBitrix(id,fields);

        return repo.save(deal);
    }


}


