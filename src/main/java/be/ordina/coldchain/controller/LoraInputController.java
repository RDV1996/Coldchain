package be.ordina.coldchain.controller;

import be.ordina.coldchain.Service.BlockchainDataService;
import be.ordina.coldchain.model.LoraInput;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/lorainput")
public class LoraInputController {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");

    BlockchainDataService blockchainDataService = new BlockchainDataService();

    @RequestMapping(value = "/GenerateJson", method = RequestMethod.GET)
    public void createJson() {
        blockchainDataService.dataGen();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public MultiValueMap<String, String> testJson(
            @RequestParam(value = "companyName") String companyName,
            @RequestParam(value = "DevEUI") String DevEUI,
            @RequestParam(value = "container") String container,
            @RequestParam(value = "value") String value,
            @RequestParam(value = "timestamp") Date timestamp,
            @RequestParam(value = "Lrcid") String Lrcid) {

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("$class", "org.ordina.coldchain.perishable.ReadingImport");
        requestBody.add("companyName", companyName);
        requestBody.add("DevEUI", DevEUI);
        requestBody.add("container", container);
        requestBody.add("value", value);
        requestBody.add("timestamp", sdf.format(new Date()));
        requestBody.add("Lrcid",Lrcid);
        requestBody.add("time", sdf.format(timestamp));


        blockchainDataService.sendToApi(requestBody, "http://hyperledger.vanhool.net:3000/api/org.ordina.coldchain.perishable.ReadingImport");

        return requestBody;
    }
}
