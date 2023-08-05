package com.example.asm.MainScreen.ScreenFragment_BottomNav.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Model.Products;
import com.example.asm.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private Context context;
    private List<Products> productsList;
    int priceperproduct;
    private int priceproduct;
    public ProductAdapter(Context context, List<Products> productsList) {
        this.context = context;
        if (productsList == null) {
            this.productsList = new ArrayList<>(); // hoặc một danh sách rỗng tùy bạn
        } else {
            this.productsList = productsList;
        }
    }


    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill_user_item, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Products products = productsList.get(position);

        holder.productNameTextView.setText(products.getProductname());
        holder.productTypeTextView.setText(products.getProducttype());
        Glide.with(holder.itemView.getContext())
                .load(products.getProductimage())
                .placeholder(R.drawable.ic_history) // Ảnh placeholder hiển thị khi chờ tải ảnh
                .error(R.drawable.ic_history) // Ảnh hiển thị khi lỗi tải ảnh
                .into(holder.item_img_product);
        holder.productMountTextView.setText(String.valueOf(products.getPrice()));

    }

    @Override
    public int getItemCount() {
        if (productsList == null) {
            return 0; // hoặc trả về số phần tử mặc định
        }
        return productsList.size();
    }


    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        private TextView productNameTextView, productTypeTextView, productMountTextView;
        ImageView item_img_product;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.item_name_product);
            productTypeTextView = itemView.findViewById(R.id.item_type_product);
            productMountTextView = itemView.findViewById(R.id.item_product_mount);
            item_img_product = itemView.findViewById(R.id.item_img_product);

        }
    }
}

