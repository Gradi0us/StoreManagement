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
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Interface_RecycleView.OnProductItemClickListener;
import com.example.asm.R;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {

    private Context context;
    private List<Products> productList;
    private OnProductItemClickListener productItemClickListener;
    public HomeAdapter(Context context, List<Products> productList, OnProductItemClickListener itemClickListener) {
        this.context = context;
        this.productList = productList;
        this.productItemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_products, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products product = productList.get(position);
        holder.productNameTextView.setText(product.getProductname());
        holder.productPriceTextView.setText(""+product.getPrice());
        Glide.with(holder.itemView.getContext())
                .load(product.getProductimage())
                .placeholder(R.drawable.ic_history) // Ảnh placeholder hiển thị khi chờ tải ảnh
                .error(R.drawable.ic_history) // Ảnh hiển thị khi lỗi tải ảnh
                .into(holder.productimgview);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Gọi phương thức onProductItemClick của Interface khi click vào item
                if (productItemClickListener != null) {
                    productItemClickListener.onProductItemClick(product);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public void setProductList(List<Products> productList) {
        this.productList = productList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView productNameTextView,productPriceTextView;
        ImageView productimgview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView= itemView.findViewById(R.id.nameitem);
            productPriceTextView = itemView.findViewById(R.id.costitem);
            productimgview = itemView.findViewById(R.id.imgitem);
        }
    }
}
