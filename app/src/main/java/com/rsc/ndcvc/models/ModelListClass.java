package com.rsc.ndcvc.models;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "ulive",
        "uid",
        "uname",
        "uemail",
        "uphone",
        "usnumber",
        "cid",
        "cuid",
        "cpermit",
        "calias",
        "ccreated"
})
public class ModelListClass {

    @JsonProperty("ulive")
    private String ulive;
    @JsonProperty("uid")
    private String uid;
    @JsonProperty("uname")
    private String uname;
    @JsonProperty("uemail")
    private String uemail;
    @JsonProperty("uphone")
    private String uphone;
    @JsonProperty("usnumber")
    private String usnumber;
    @JsonProperty("cid")
    private String cid;
    @JsonProperty("cuid")
    private String cuid;
    @JsonProperty("cpermit")
    private String cpermit;
    @JsonProperty("calias")
    private String calias;
    @JsonProperty("ccreated")
    private String ccreated;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("ulive")
    public String getUlive() {
        return ulive;
    }

    @JsonProperty("ulive")
    public void setUlive(String ulive) {
        this.ulive = ulive;
    }

    @JsonProperty("uid")
    public String getUid() {
        return uid;
    }

    @JsonProperty("uid")
    public void setUid(String uid) {
        this.uid = uid;
    }

    @JsonProperty("uname")
    public String getUname() {
        return uname;
    }

    @JsonProperty("uname")
    public void setUname(String uname) {
        this.uname = uname;
    }

    @JsonProperty("uemail")
    public String getUemail() {
        return uemail;
    }

    @JsonProperty("uemail")
    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    @JsonProperty("uphone")
    public String getUphone() {
        return uphone;
    }

    @JsonProperty("uphone")
    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    @JsonProperty("usnumber")
    public String getUsnumber() {
        return usnumber;
    }

    @JsonProperty("usnumber")
    public void setUsnumber(String usnumber) {
        this.usnumber = usnumber;
    }

    @JsonProperty("cid")
    public String getCid() {
        return cid;
    }

    @JsonProperty("cid")
    public void setCid(String cid) {
        this.cid = cid;
    }

    @JsonProperty("cuid")
    public String getCuid() {
        return cuid;
    }

    @JsonProperty("cuid")
    public void setCuid(String cuid) {
        this.cuid = cuid;
    }

    @JsonProperty("cpermit")
    public String getCpermit() {
        return cpermit;
    }

    @JsonProperty("cpermit")
    public void setCpermit(String cpermit) {
        this.cpermit = cpermit;
    }

    @JsonProperty("calias")
    public String getCalias() {
        return calias;
    }

    @JsonProperty("calias")
    public void setCalias(String calias) {
        this.calias = calias;
    }

    @JsonProperty("ccreated")
    public String getCcreated() {
        return ccreated;
    }

    @JsonProperty("ccreated")
    public void setCcreated(String ccreated) {
        this.ccreated = ccreated;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}