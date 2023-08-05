package com.example.asm.MainScreen.ScreenFragment_BottomNav;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asm.MainScreen.MainScreen;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.API.add_history;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Adapter.Bill_Adapter;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Adapter.History_Adapter;
import com.example.asm.MainScreen.ScreenFragment_BottomNav.Model.History;
import com.example.asm.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistoryFragment extends Fragment {
    MainScreen mainScreen;
    RecyclerView recyclerView;
    List<History> historyList;
    String uid;
    History_Adapter History_Adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_history, container, false);
        mainScreen = (MainScreen) getActivity();
        uid = mainScreen.userId;
        historyList = new ArrayList<>();
        gethistorybyid(uid);
        anhxa(v);
        return v;
    }

    private void anhxa(View v) {
        recyclerView = v.findViewById(R.id.history_recycleview);
    }

    private void gethistorybyid(String uid) {
        add_history.api.gethistorybyuid(uid).enqueue(new Callback<List<History>>() {
            @Override
            public void onResponse(Call<List<History>> call, Response<List<History>> response) {
                if (response.isSuccessful()) {
                    List<History> historyList = response.body();
                    History_Adapter = new History_Adapter(getActivity(), historyList);
                    LinearLayoutManager manager1 = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
                    recyclerView.setLayoutManager(manager1);
                    recyclerView.setAdapter(History_Adapter);
                }
            }

            @Override
            public void onFailure(Call<List<History>> call, Throwable t) {

            }
        });
    }
}