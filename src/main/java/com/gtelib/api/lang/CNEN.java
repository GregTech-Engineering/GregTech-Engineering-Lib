package com.gtelib.api.lang;

import com.gtelib.api.annotation.DataGeneratorScanned;

@DataGeneratorScanned
public record CNEN(String en, String cn) {

    public static CNEN create(String en, String cn) {
        return new CNEN(en, cn);
    }
}
