package com.example.asm.MainScreen.ScreenFragment_BottomNav.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail.Model.Customer;
import com.example.asm.R;

import java.util.List;

public class Bill_Adapter extends RecyclerView.Adapter<Bill_Adapter.BillViewHolder> {
    private Context context;
    private List<Customer> customerList;


    public Bill_Adapter(Context context, List<Customer> customerList) {
        this.context = context;
        this.customerList = customerList;

    }

    @NonNull
    @Override
    public BillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill_user, parent, false);
        return new BillViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BillViewHolder holder, int position) {
        Customer customer = customerList.get(position);

        holder.customerNameTextView.setText(customer.getCustomername());

        ProductAdapter productAdapter = new ProductAdapter(context, customer.getProductList());

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        holder.item_product.setLayoutManager(layoutManager);
        holder.item_product.setAdapter(productAdapter);

        // Set the listener for the ProductAdapter


        // Set other data as needed

        // Add any event listeners here
    }

    @Override
    public int getItemCount() {
        return customerList.size();
    }

    public static class BillViewHolder extends RecyclerView.ViewHolder {
        private TextView customerNameTextView;
        RecyclerView item_product;

        public BillViewHolder(@NonNull View itemView) {
            super(itemView);
            customerNameTextView = itemView.findViewById(R.id.customername);
            item_product = itemView.findViewById(R.id.item_product);
        }
    }
}
