/**
 * Copyright (C) 2014 android10.org. All rights reserved.
 *
 * @author Fernando Cejas (the android10 coder)
 */
package com.fernandocejas.android10.sample.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.fernandocejas.android10.sample.presentation.databinding.FragmentUserListBinding;
import com.fernandocejas.android10.sample.presentation.internal.di.components.UserComponent;
import com.fernandocejas.android10.sample.presentation.model.UserModel;
import com.fernandocejas.android10.sample.presentation.presenter.UserListPresenter;
import com.fernandocejas.android10.sample.presentation.view.UserListView;
import com.fernandocejas.android10.sample.presentation.view.adapter.UsersAdapter;
import com.fernandocejas.android10.sample.presentation.view.adapter.UsersLayoutManager;

import java.util.Collection;

import javax.inject.Inject;

public class UserListFragment extends BaseFragment implements UserListView {

    private FragmentUserListBinding binding;
    public interface UserListListener {
        void onUserClicked(final UserModel userModel);
    }

    @Inject UserListPresenter userListPresenter;
    @Inject UsersAdapter usersAdapter;


    private UserListListener userListListener;

    public UserListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof UserListListener) {
            this.userListListener = (UserListListener) context;
        }
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = FragmentUserListBinding.inflate(getLayoutInflater());
        this.getComponent(UserComponent.class).inject(this);
        this.binding.viewRetry.btRetry.setOnClickListener(v -> onButtonRetryClick());
    }

    @Override public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setupRecyclerView();
        return binding.getRoot();
    }

    @Override public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.userListPresenter.setView(this);
        if (savedInstanceState == null) {
            this.loadUserList();
        }
    }

    @Override public void onResume() {
        super.onResume();
        this.userListPresenter.resume();
    }

    @Override public void onPause() {
        super.onPause();
        this.userListPresenter.pause();
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        this.binding.rvUsers.setAdapter(null);
    }

    @Override public void onDestroy() {
        super.onDestroy();
        this.userListPresenter.destroy();
    }

    @Override public void onDetach() {
        super.onDetach();
        this.userListListener = null;
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

    @Override public void renderUserList(Collection<UserModel> userModelCollection) {
        if (userModelCollection != null) {
            this.usersAdapter.setUsersCollection(userModelCollection);
        }
    }

    @Override public void viewUser(UserModel userModel) {
        if (this.userListListener != null) {
            this.userListListener.onUserClicked(userModel);
        }
    }

    @Override public void showError(String message) {
        this.showToastMessage(message);
    }

    @Override public Context context() {
        return this.getActivity().getApplicationContext();
    }

    private void setupRecyclerView() {
        this.usersAdapter.setOnItemClickListener(onItemClickListener);
        this.binding.rvUsers.setLayoutManager(new UsersLayoutManager(context()));
        this.binding.rvUsers.setAdapter(usersAdapter);
    }

    /**
     * Loads all users.
     */
    private void loadUserList() {
        this.userListPresenter.initialize();
    }

    void onButtonRetryClick() {
        UserListFragment.this.loadUserList();
    }

    private final UsersAdapter.OnItemClickListener onItemClickListener = userModel -> {
        if (UserListFragment.this.userListPresenter != null && userModel != null) {
            UserListFragment.this.userListPresenter.onUserClicked(userModel);
        }
    };
}
