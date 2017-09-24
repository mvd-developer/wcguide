package com.doschechko.matylionak.wcguide.about;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.doschechko.matylionak.wcguide.base.BaseFragmentViewModel;
import com.doschechko.matylionak.wcguide.databinding.ActivityAboutBinding;


public class AboutFragmentViewModel implements BaseFragmentViewModel {

    ActivityAboutBinding binding;
    Button button;
    Activity activity;
    @Override
    public void init() {

        button=binding.shareButton;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "I'm using this WC app");
                shareIntent.setType("text/plain");
                activity.startActivity(shareIntent);
                Toast.makeText(activity, "TTT", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void release() {

    }

    public void setBinding(ActivityAboutBinding binding) {
        this.binding = binding;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
