package com.example.asm.MainScreen.ScreenFragment_BottomNav.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.API.findcustomerbyid_api;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.API.findproductbyid_api;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Fragment_Detail.Model.Customer;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Model.History;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Model.Products;
import com.example.asm.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class History_Adapter extends RecyclerView.Adapter<History_Adapter.ViewHolder> {
    private Context context;
    private List<History> historyList;
    String pid,customerid;
    String img,productname,customername;
    public History_Adapter(Context context, List<History> historyList) {
        this.context = context;
        this.historyList = historyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        History history = historyList.get(position);
        pid = history.getPid();
        customerid = history.getCustomerid();
        getimglinkAndCustomer(pid, customerid, holder, history);
//        holder.itemName.setText(productname);
//        holder.itemCustomer.setText(customername);
//        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
//        try {
//            Date date = inputFormat.parse(history.getDate());
//            String formattedDate = outputFormat.format(date);
//            holder.itemDate.setText(formattedDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//            holder.itemDate.setText(history.getDate()); // Nếu không thể định dạng, hiển thị nguyên gốc
//        }
//        holder.itemPrice.setText(String.valueOf(history.getPrice()));
//        holder.itemMount.setText(String.valueOf(history.getMountbuy()));
//
//        if(img != null){
//            Glide.with(holder.itemView.getContext())
//                    .load(img)
//                    .placeholder(R.drawable.ic_history) // Ảnh placeholder hiển thị khi chờ tải ảnh
//                    .error(R.drawable.ic_history) // Ảnh hiển thị khi lỗi tải ảnh
//                    .into(holder.itemImage);
//        }else {
//            Toast.makeText(context, "Lỗi lấy ảnh link bị null", Toast.LENGTH_SHORT).show();
//        }

    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemImage;
        TextView itemName, itemCustomer, itemDate, itemPrice, itemMount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_img_history);
            itemName = itemView.findViewById(R.id.item_name_history);
            itemCustomer = itemView.findViewById(R.id.item_namecustomer_history);
            itemDate = itemView.findViewById(R.id.ỉtem_time_history);
            itemPrice = itemView.findViewById(R.id.item_price_history);
            itemMount = itemView.findViewById(R.id.item_history_mount);
        }
    }
    private void updateViewHolder(ViewHolder holder, String productName, String customerName, String imgLink, History history) {
        holder.itemName.setText(productName);
        holder.itemCustomer.setText(customerName);
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = inputFormat.parse(history.getDate());
            String formattedDate = outputFormat.format(date);
            holder.itemDate.setText(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            holder.itemDate.setText(history.getDate());
        }
        holder.itemPrice.setText(String.valueOf(history.getPrice()));
        holder.itemMount.setText(String.valueOf(history.getMountbuy()));

        if (imgLink != null) {
            Glide.with(holder.itemView.getContext())
                    .load(imgLink)
                    .placeholder(R.drawable.ic_history)
                    .error(R.drawable.ic_history)
                    .into(holder.itemImage);
        } else {
            Toast.makeText(context, "Lỗi lấy ảnh link bị null", Toast.LENGTH_SHORT).show();
        }
    }

    private void getimglinkAndCustomer(String pid, String customerid, ViewHolder holder, History history) {
        findproductbyid_api.api.getproductbyid(pid).enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (response.isSuccessful()) {
                    List<Products> productsList = response.body();
                    if (productsList != null) {
                        Products products = productsList.get(0);
                        String imgLink = products.getProductimage();
                        String productName = products.getProductname();

                        // Gọi cuộc gọi API lấy thông tin khách hàng sau khi đã có thông tin sản phẩm
                        findcustomerbyid(customerid, holder, history, productName, imgLink);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(context, "Lỗi không lấy dữ liệu lịch sử thành công:" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findcustomerbyid(String customerid, ViewHolder holder, History history, String productName, String imgLink) {
        findcustomerbyid_api.api.getcustomerbyid(customerid).enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if (response.isSuccessful()) {
                    List<Customer> customerList = response.body();
                    if (customerList != null) {
                        Customer customer = customerList.get(0);
                        String customerName = customer.getCustomername();

                        // Sau khi có thông tin cả sản phẩm và khách hàng, cập nhật dữ liệu cho mục trong RecyclerView
                        updateViewHolder(holder, productName, customerName, imgLink, history);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                // Xử lý lỗi (nếu cần)
            }
        });
    }


}
