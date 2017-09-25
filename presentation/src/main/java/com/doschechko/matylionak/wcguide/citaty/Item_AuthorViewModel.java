package com.doschechko.matylionak.wcguide.citaty;


import android.app.Activity;
import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.doschechko.matylionak.wcguide.toolbar.ToolBarFragmentActivityViewModel;



public class Item_AuthorViewModel {
    private ObservableField<String> name = new ObservableField<>("");
    private ObservableField<String> url = new ObservableField<>("");
    private FragmentManager manager;
    private Activity activity;

    public ObservableField<String> getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = new ObservableField<>(url);
    }

    public ObservableField<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name = new ObservableField<>(name);
    }

    //создаем новый xml атрибут для загрузки данных
    @BindingAdapter("android:src")
    public static void loadImage(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .apply(RequestOptions.circleCropTransform())
                .into(view);
    }

//при клике на layout передаем объект вью модели и вытаскиваем name,
// по которому уже стартует новый фрагмент
    public  void showQuotesByAuthor(Item_AuthorViewModel viewModel){

        Log.e("AAAA", viewModel.getName().get());
        Activity_Authors.AUTHOR_NAME = viewModel.getName().get();
        //стартуем новый фрагмент
        ToolBarFragmentActivityViewModel.showFragment(manager, Activity_Quote
                .newInstance(manager,"Activity_Quote"), true);

    }


    public void setManager(FragmentManager manager) {
        this.manager = manager;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }



}
