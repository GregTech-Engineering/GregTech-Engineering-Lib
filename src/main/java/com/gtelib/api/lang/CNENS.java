package com.gtelib.api.lang;

public record CNENS(String[] cns, String[] ens) {

    public String[] cns() {
        return this.cns;
    }

    public String[] ens() {
        return this.ens;
    }
}
