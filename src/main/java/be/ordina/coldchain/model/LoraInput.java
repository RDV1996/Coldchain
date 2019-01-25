package be.ordina.coldchain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LoraInput {


    String $class;
    String companyName;
    String DevEUI;
    String container;
    long value;
    Date timestamp;
    String Lrcid;
    Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDevEUI() {
        return DevEUI;
    }

    public void setDevEUI(String devEUI) {
        DevEUI = devEUI;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getLrcid() {
        return Lrcid;
    }

    public void setLrcid(String lrcid) {
        Lrcid = lrcid;
    }

    public String get$class() {
        return $class;
    }

    public void set$class(String $class) {
        this.$class = $class;
    }
}
