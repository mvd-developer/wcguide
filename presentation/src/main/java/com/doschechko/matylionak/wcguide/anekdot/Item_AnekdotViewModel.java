package com.doschechko.matylionak.wcguide.anekdot;

import android.databinding.ObservableField;


public class Item_AnekdotViewModel {


    private ObservableField<String> body = new ObservableField<>("");

    public ObservableField<String> getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = new ObservableField<>(body);
    }
}
