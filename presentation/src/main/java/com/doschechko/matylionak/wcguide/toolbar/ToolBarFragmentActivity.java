package com.doschechko.matylionak.wcguide.toolbar;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import com.doschechko.matylionak.wcguide.MyDialog;
import com.doschechko.matylionak.wcguide.R;
import com.doschechko.matylionak.wcguide.base.BaseFragmentActivity;
import com.doschechko.matylionak.wcguide.databinding.ToolbarBinding;
import com.doschechko.matylionak.wcguide.maps.Activity_Maps;

import static com.doschechko.matylionak.wcguide.toolbar.ToolBarFragmentActivityViewModel.showFragment;

/**
 * ToolBar with changeble fragments
 */

public class ToolBarFragmentActivity extends BaseFragmentActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Intent intent =getIntent();
        Boolean getPermission=intent.getBooleanExtra("getPermission",false);
        //создаем дата-биндинг
        ToolBarFragmentActivityViewModel viewModel = new ToolBarFragmentActivityViewModel();
        viewModel.setActivity(this);
        viewModel.setPermission(getPermission);
        FragmentManager manager = getSupportFragmentManager();
        viewModel.setFragmentManager(manager);
        this.viewModel = viewModel;

        ToolbarBinding binding = DataBindingUtil
                .setContentView(this, R.layout.toolbar);
        binding.setViewModel(viewModel);

        SlidingDrawer drawer = binding.SlidingDrawer;
        viewModel.setSlidingDrawer(drawer);


//        //    для загрузки карты
//        if (savedInstanceState == null) {
//        //проверка на наличие экрана
//            ToolBarFragmentActivityViewModel.showFragment(getSupportFragmentManager(),
//                    Activity_Maps.newInstance(getSupportFragmentManager(), "Activity_Maps"), true);
//          //  Toast.makeText(getBaseContext(),"Добро пожаловать!", Toast.LENGTH_SHORT).show();
//        }




        super.onCreate(savedInstanceState);

    }



}
