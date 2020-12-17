package com.mdrsolutions.SpringJmsExample.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Customer {

    private final String customerId;
    private final String fullName;

    public Customer(@JsonProperty("customerId") String customerId,
                    @JsonProperty("fullName") String fullName) {
        this.customerId = customerId;
        this.fullName = fullName;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getFullName() {
        return fullName;
    }
}
