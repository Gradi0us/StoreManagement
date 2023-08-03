package com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.asm.MainScreen.ScreenFragment_BottomNav.Interface_RecycleView.OnProductItemClickListener;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Model.Products;
import com.example.asm.R;

import java.util.List;

public class Detail_Adapter extends RecyclerView.Adapter<Detail_Adapter.ViewHolder> {

    private Context context;
    private List<Products> productList;
    private int productmountt = 1;
    public Detail_Adapter(Context context, List<Products> productList) {
        this.context = context;
        this.productList = productList;

    }

    @NonNull
    @Override
    public Detail_Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail, parent, false);

        return new Detail_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Products product = productList.get(position);
        holder.productName.setText(product.getProductname());
        holder.productPrice.setText(""+product.getPrice());
        holder.productType.setText(product.getProducttype());
        holder.productSize .setText(product.getSize());
        Glide.with(holder.itemView.getContext())
                .load(product.getProductimage())
                .placeholder(R.drawable.ic_history) // Ảnh placeholder hiển thị khi chờ tải ảnh
                .error(R.drawable.ic_history) // Ảnh hiển thị khi lỗi tải ảnh
                .into(holder.productimgview);
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productmountt++;
                // Gửi broadcast khi số lượng sản phẩm thay đổi
                Intent intent = new Intent("PRODUCT_MOUNT_CHANGED");
                intent.putExtra("PRODUCT_MOUNT", productmountt);
                holder.itemView.getContext().sendBroadcast(intent);
                holder.number.setText(String.valueOf(productmountt));
            }
        });
        holder.btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (productmountt > 1) {
                    productmountt--;
                    // Gửi broadcast khi số lượng sản phẩm thay đổi
                    Intent intent = new Intent("PRODUCT_MOUNT_CHANGED");
                    intent.putExtra("PRODUCT_MOUNT", productmountt);
                    holder.itemView.getContext().sendBroadcast(intent);
                    holder.number.setText(String.valueOf(productmountt));
                }
            }
        });


        holder.number.setText(String.valueOf(productmountt));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    public void setProductList(List<Products> productList) {
        this.productList = productList;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView productName,productPrice,productSize,productType,number;
        ImageView productimgview;
        CardView btn_add,btn_minus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productName= itemView.findViewById(R.id.name_product);
            productPrice = itemView.findViewById(R.id.cost_product);
            productSize = itemView.findViewById(R.id.size_product);
            productType = itemView.findViewById(R.id.type_product);
            productimgview = itemView.findViewById(R.id.img_product);
            number = itemView.findViewById(R.id.number);
            btn_add = itemView.findViewById(R.id.button_plus);
            btn_minus = itemView.findViewById(R.id.button_minus);
        }
    }
}
