package be.ordina.coldchain.controller;

import be.ordina.coldchain.Service.BlockchainDataService;
import be.ordina.coldchain.model.LoraInput;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping(value = "/lorainput")
public class LoraInputController {


    BlockchainDataService test = new BlockchainDataService();

    @RequestMapping(value = "/BlockchainJson", method = RequestMethod.GET)
    public void createJson() {
        test.dataGen();
    }

    @RequestMapping(value = "/nothing", method = RequestMethod.POST)
    public LoraInput testJson(
            @RequestParam(value = "$class") String $class,
            @RequestParam(value = "companyName") String companyName,
            @RequestParam(value = "DevEUI") String DevEUI,
            @RequestParam(value = "container") String container,
            @RequestParam(value = "value") String value,
            @RequestParam(value = "timestamp") Date timestamp,
            @RequestParam(value = "Lrcid") String Lrcid,
            @RequestParam(value = "time") Date time) {
        LoraInput lora = new LoraInput();
        lora.set$class($class);
        lora.setCompanyName(companyName);
        lora.setDevEUI(DevEUI);
        lora.setContainer(container);
        lora.setValue(Long.parseLong(value));
        lora.setTimestamp(timestamp);
        lora.setLrcid(Lrcid);
        lora.setTime(time);

        return lora;
    }
}
