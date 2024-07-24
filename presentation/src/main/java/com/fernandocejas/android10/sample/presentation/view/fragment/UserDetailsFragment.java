/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fernandocejas.android10.sample.presentation.databinding.FragmentUserDetailsBinding;
import com.fernandocejas.android10.sample.presentation.internal.di.components.UserComponent;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import com.fernandocejas.android10.sample.presentation.presenter.UserDetailsPresenter;
import com.fernandocejas.android10.sample.presentation.view.UserDetailsView;

import javax.inject.Inject;

public class UserDetailsFragment extends BaseFragment implements UserDetailsView {
    private static final String PARAM_USER_ID = "param_user_id";
    private FragmentUserDetailsBinding binding;

    @Inject UserDetailsPresenter userDetailsPresenter;

    public static UserDetailsFragment forUser(int userId) {
        final UserDetailsFragment userDetailsFragment = new UserDetailsFragment();
        final Bundle arguments = new Bundle();
        arguments.putInt(PARAM_USER_ID, userId);
        userDetailsFragment.setArguments(arguments);
        return userDetailsFragment;
    }

    public UserDetailsFragment() {
        setRetainInstance(true);
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getComponent(UserComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.userDetailsPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadUserDetails();
        }
    }

    @Override public void onResume() {
        super.onResume();
        this.userDetailsPresenter.resume();
    }

    @Override public void onPause() {
        super.onPause();
        this.userDetailsPresenter.pause();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.userDetailsPresenter.destroy();
    }

    @Override public void renderUser(UserModel user) {
        if (user != null) {
            this.binding.viewUserDetails.ivCover.setImageUrl(user.getCoverUrl());
            this.binding.viewUserDetails.tvFullname.setText(user.getFullName());
            this.binding.viewUserDetails.tvEmail.setText(user.getEmail());
            this.binding.viewUserDetails.tvFollowers.setText(String.valueOf(user.getFollowers()));
            this.binding.viewUserDetails.tvDescription.setText(user.getDescription());
        }
    }

    @Override public void showLoading() {
        this.binding.viewProgress.rlProgress.setVisibility(View.VISIBLE);
        this.getActivity().setProgressBarIndeterminateVisibility(true);
    }

    @Override public void hideLoading() {
        this.binding.viewProgress.rlProgress.setVisibility(View.GONE);
        this.getActivity().setProgressBarIndeterminateVisibility(false);
    }

    @Override public void showRetry() {
        this.binding.viewRetry.rlRetry.setVisibility(View.VISIBLE);
    }

    @Override public void hideRetry() {
        this.binding.viewRetry.rlRetry.setVisibility(View.GONE);
    }

    @Override public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override public Context context() {
        return getActivity().getApplicationContext();
    }

    private void loadUserDetails() {
        if (this.userDetailsPresenter != null) {
            this.userDetailsPresenter.initialize(currentUserId());
        }
    }

    private int currentUserId() {
        final Bundle arguments = getArguments();
        return arguments.getInt(PARAM_USER_ID);
    }

    void onButtonRetryClick() {
        UserDetailsFragment.this.loadUserDetails();
    }
}
