package com.example.faceregonatio.ui.fragment.profile;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.faceregonatio.R;
import com.example.faceregonatio.databinding.FragmentProfileBinding;
import com.example.faceregonatio.ui.activity.login.Login;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {


    private FragmentProfileBinding profileBinding;
    private ProfileViewModel viewModel;
    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        profileBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false);
        profileBinding.setLifecycleOwner(getActivity());

        return profileBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel= ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(ProfileViewModel.class);
        profileBinding.setViewModel(viewModel);
        viewModel.getUserProfile();


        viewModel.logout.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean){
                    startActivity(new Intent(getActivity(),Login.class));

                }
            }
        });
    }
}
