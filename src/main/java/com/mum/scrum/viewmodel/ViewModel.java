package com.mum.scrum.viewmodel;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: nadim
 * Date: 4/6/16
 * Time: 5:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class ViewModel {
    private int statusCode;
    private List<String> statusMessage = new ArrayList<>();
    @JsonProperty("data")
    Map<String, Object> dataMap = new HashMap<>();

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public List<String> getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(List<String> statusMessage) {
        this.statusMessage = statusMessage;
    }

    public Map<String, Object> getDataMap() {
        return dataMap;
    }

    public void setDataMap(Map<String, Object> dataMap) {
        this.dataMap = dataMap;
    }
}
