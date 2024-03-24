package com.onlyforchris.store.core.model.express;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author: Chris
 * @create: 2024-03-24 01:51
 **/
public class Traces {

    @JsonProperty("AcceptStation")
    private String AcceptStation;

    @JsonProperty("AcceptTime")
    private String AcceptTime;

    public String getAcceptStation() {
        return AcceptStation;
    }

    public void setAcceptStation(String AcceptStation) {
        this.AcceptStation = AcceptStation;
    }

    public String getAcceptTime() {
        return AcceptTime;
    }

    public void setAcceptTime(String AcceptTime) {
        this.AcceptTime = AcceptTime;
    }

}
