package be.ordina.coldchain.Service;

import be.ordina.coldchain.model.LoraInput;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;

public class BlockchainDataService {

    public void dataGen() {

        List<String> bedrijven = new ArrayList<>();
        List<String> plaatsen = new ArrayList<>();
        List<String> DevEUI = new ArrayList<>();
        List<String> temps = new ArrayList<>();
        /*1-7,2-8,mac -18*/
        List<Date> dates = new ArrayList<>();


        bedrijven.add("vers vlees");
        bedrijven.add("bevroren voeding");
        bedrijven.add("Medicatie");

        plaatsen.add("FF0109CE");
        plaatsen.add("AF4687FE");
        plaatsen.add("DE9976FE");

        DevEUI.add("0E654546546VB");
        DevEUI.add("2A654546546VB");
        DevEUI.add("3B654546546VB");
        DevEUI.add("4C654546546VB");
        DevEUI.add("1D654546546VB");
        DevEUI.add("5F654546546VB");

        temps.add("4");
        temps.add("3");
        temps.add("3");
        temps.add("5");
        temps.add("-21");
        temps.add("-20");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
        Date d = new Date(2019- 1900, 01, 21, 10, 15, 59);
        dates.add(d);
        d = new Date(2016 - 1900, 12, 20, 11, 44, 59);
        dates.add(d);
        d = new Date(2018- 1900, 11, 1, 9, 17, 59);
        dates.add(d);
        d = new Date(2018- 1900, 10, 10, 10, 10, 10);
        dates.add(d);
        d = new Date(2018- 1900, 07, 07, 07, 07, 07);
        dates.add(d);
        d = new Date(2019- 1900, 01, 01, 01, 01, 01);
        dates.add(d);

        Random rand = new Random();

        int plaats = 0;
        int i = 0;


        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.add("PRIVATE-TOKEN", "xyz");
        headers.add("Accept", MediaType.ALL_VALUE);
        JSONObject jsonObject = null;


        for (int z = 0; z < bedrijven.size(); z++) {
            for (int y = 0; y < Math.round(DevEUI.size() / bedrijven.size()); y++) {
                int ran = rand.nextInt(40 - 30) + 30;
                Calendar c = Calendar.getInstance();
                c.setTime(dates.get(i));
                for (int x = 0; x < ran; x++) {
                    temps.set(i, Integer.toString(Integer.parseInt(temps.get(i)) + (rand.nextInt(4) - 2)));
                    if (x > ran / 3 && x < (ran / 3) * 2) {
                        plaats = 1;
                    } else if (x > (ran / 3) * 2) {
                        plaats = 2;
                    }

                    Date tempDate = c.getTime();
                    /*System.out.println("--------------");
                    System.out.println(dates.get(i));
                    System.out.println(tempDate);
                    System.out.println(c);
                    System.out.println(sdf.format(tempDate));
                    System.out.println("--------------");*/


                    MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
                    requestBody.add("$class", "org.ordina.coldchain.perishable.ReadingImport");
                    requestBody.add("companyName", bedrijven.get(z));
                    requestBody.add("DevEUI", DevEUI.get(i));
                    requestBody.add("container", "temperatuur");
                    requestBody.add("value", temps.get(i));
                    requestBody.add("timestamp", sdf.format(tempDate));
                    requestBody.add("Lrcid", plaatsen.get(plaats));
                    requestBody.add("time", sdf.format(new Date()));


                    c.add(Calendar.MINUTE, 15);

                    try {
                        HttpEntity formEntity = new HttpEntity<>(requestBody, headers);

                        //System.out.println(formEntity);

                        ResponseEntity<String> responseEntity  = restTemplate.postForEntity(
                                //"http://localhost:5000/lorainput/nothing",
                                "http://hyperledger.vanhool.net:3000/api/org.ordina.coldchain.perishable.ReadingImport",
                                        formEntity, String.class);
                        System.out.println(responseEntity);
                        if (responseEntity.getStatusCode() == HttpStatus.CREATED) {
                            try {
                                jsonObject = new JSONObject(responseEntity.getBody());
                                System.out.println(jsonObject);

                            } catch (JSONException e) {
                                throw new RuntimeException("JSONException occurred");
                            }
                        }
                    } catch (final HttpClientErrorException httpClientErrorException) {
                        System.out.println(httpClientErrorException);

                    } catch (HttpServerErrorException httpServerErrorException) {
                        System.out.println(httpServerErrorException);

                    } catch (Exception exception) {
                        System.out.println(exception);
                    }
                }
                i++;
            }
        }
    }
}
