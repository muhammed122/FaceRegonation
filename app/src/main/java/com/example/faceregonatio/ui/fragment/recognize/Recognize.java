package com.example.faceregonatio.ui.fragment.recognize;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.faceregonatio.R;
import com.example.faceregonatio.databinding.FragmentRecognizeBinding;
import com.squareup.picasso.Picasso;

import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Recognize extends Fragment {

    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    Uri image_uri;

    public Recognize() {
        // Required empty public constructor
    }

    private FragmentRecognizeBinding binding;
    private RecognizeViewModel viewModel;

   private AlertDialog.Builder builder;
   private AlertDialog alertDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recognize, container, false);
        binding.setLifecycleOwner(getActivity());
        View v = binding.getRoot();
        viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(RecognizeViewModel.class);
        binding.setViewModel(viewModel);

        viewModel.getImage.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                    getImage();
            }
        });


        viewModel.recoginze.observe(getViewLifecycleOwner(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean)
                    if (image_uri == null)
                        Toast.makeText(getContext(), "No Image picked", Toast.LENGTH_SHORT).show();
                    else {
                        initAlertDialog();
                        alertDialog.show();
                        viewModel.recognize(image_uri, getActivity());


                    }
            }
        });

        viewModel.image_url.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                alertDialog.dismiss();
                Picasso.get().load(s).into(binding.imagePicked);
            }
        });

        viewModel.message.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                alertDialog.dismiss();
                Toast.makeText(getActivity(), ""+s, Toast.LENGTH_SHORT).show();
            }
        });


        return v;
    }

    protected void getImage() {
        Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
        openGalleryIntent.setType("image/*");
        startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GALLERY_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
                image_uri = data.getData();
                binding.imagePicked.setImageURI(image_uri);
                binding.textInfo.setVisibility(View.GONE);
            }
        }
    }

    private void initAlertDialog() {
        builder =new AlertDialog.Builder(getContext())
                .setMessage("recognizing");
        alertDialog=builder.create();

    }


}
