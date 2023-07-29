package com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Model.Products;
import com.example.asm.R;

public class ProductDetailFragment extends Fragment {

    private Products product;

    public static ProductDetailFragment newInstance(Products product) {
        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("product", product);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = (Products) getArguments().getSerializable("product");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_detail, container, false);

        // Initialize views
        ImageView imageViewProduct = view.findViewById(R.id.imageViewProduct);
        TextView textViewProductName = view.findViewById(R.id.textViewProductName);
        TextView textViewProductType = view.findViewById(R.id.textViewProductType);
        TextView textViewPrice = view.findViewById(R.id.textViewPrice);
        TextView textViewSize = view.findViewById(R.id.textViewSize);
        TextView textViewMount = view.findViewById(R.id.textViewMount);

        // Populate views with data from the Products model
        if (product != null) {
            Glide.with(view).load(product.getProductimage()).into(imageViewProduct);
            textViewProductName.setText(product.getProductname());
            textViewProductType.setText(product.getProducttype());
            textViewPrice.setText(String.valueOf(product.getPrice()));
            textViewSize.setText(product.getSize());
            textViewMount.setText(String.valueOf(product.getMount()));
        }

        return view;
    }
}
