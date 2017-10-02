package com.doschechko.matylionak.wcguide.citaty;


import android.databinding.ObservableField;


public class Item_QuoteViewModel {

   private ObservableField<String> body = new ObservableField<>("");

    private ObservableField<String> author = new ObservableField<>("");

    public ObservableField<String> getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = new ObservableField<>(body);
    }

    public ObservableField<String> getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = new ObservableField<>(author);
    }

    public void share(Item_QuoteViewModel viewModel){

        //вызов интента поделиться

    }

}
