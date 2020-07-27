package cvjetkovic.retrofit2rest;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import cvjetkovic.retrofit2rest.adapter.AdapterItem;
import cvjetkovic.retrofit2rest.api.ApiRequest;
import cvjetkovic.retrofit2rest.api.Retroserver;
import cvjetkovic.retrofit2rest.api.response.ItemListResponse;
import cvjetkovic.retrofit2rest.model.Item;
import cvjetkovic.retrofit2rest.utils.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Vladimir Cvjetkovic
 */
public class ItemFragment extends Fragment {

    private RecyclerView mRecycler;
    private AdapterItem mAdapter;
    private RecyclerView.LayoutManager mManager;
    private List<Item> mItems = new ArrayList<>();
    ProgressDialog progressDialog;

    private static final int REQUEST_CODE_ADD = 1;
    private static final int REQUEST_CODE_EDIT = 2;

    public ItemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View x = inflater.inflate(R.layout.fragment_item, container, false);

        FloatingActionButton fab = (FloatingActionButton) x.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ItemActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADD);
            }
        });


        progressDialog = new ProgressDialog(getActivity());
        mRecycler = (RecyclerView) x.findViewById(R.id.itemRecycler);
        mManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecycler.setLayoutManager(mManager);

        load();

        return x;

    }

    private void load() {
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
        Call<ItemListResponse> getItem = api.getItem();


        System.out.println(api.getItem());
        getItem.enqueue(new Callback<ItemListResponse>() {
            @Override
            public void onResponse(Call<ItemListResponse> call, Response<ItemListResponse> response) {
                progressDialog.hide();
                Log.d("Response", "onResponse: " + response.body().getData());

//                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();

                if (response.body() != null) {


                    mItems = response.body().getData();
                }

                mAdapter = new AdapterItem(mItems);
                mRecycler.setAdapter(mAdapter);
                mRecycler.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new
                        RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Item item = mAdapter.getItem(position);
                                Intent intent = new Intent(getActivity(), ItemActivity.class);
                                intent.putExtra("item", item);
                                startActivityForResult(intent, REQUEST_CODE_EDIT);
                            }
                        }));

                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<ItemListResponse> call, Throwable t) {
                progressDialog.hide();
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                t.printStackTrace();

            }
        });


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_ADD: {
                if (resultCode == RESULT_OK && null != data) {
                    if (data.getStringExtra("refreshFlag").equals("1")) {
                        load();
                    }
                }
                break;
            }
            case REQUEST_CODE_EDIT: {
                if (resultCode == RESULT_OK && null != data) {
                    if (data.getStringExtra("refreshFlag").equals("1")) {
                        load();
                    }
                }
                break;
            }
        }
    }

}
