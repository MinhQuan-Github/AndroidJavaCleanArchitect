package com.fernandocejas.android10.sample.presentation.view.activity;

import android.os.Bundle;

import com.fernandocejas.android10.sample.presentation.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnLoadData.setOnClickListener(view -> navigateToUserList());
    }

    void navigateToUserList() {
        this.navigator.navigateToUserList(this);
    }
}
