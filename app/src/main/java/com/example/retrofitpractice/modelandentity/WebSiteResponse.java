package com.example.retrofitpractice.modelandentity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WebSiteResponse {
    public WebSiteResponse() {
        this.input="NO RESP";
        this.output = "NO RESP";
    }

    @SerializedName("INPUT")
    @Expose
    private String input;

    @SerializedName("OUTPUT")
    @Expose
    private String output;

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
