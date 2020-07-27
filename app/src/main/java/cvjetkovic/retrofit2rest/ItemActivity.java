package cvjetkovic.retrofit2rest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cvjetkovic.retrofit2rest.api.ApiRequest;
import cvjetkovic.retrofit2rest.api.Retroserver;
import cvjetkovic.retrofit2rest.api.response.StatusResponse;
import cvjetkovic.retrofit2rest.model.Item;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Vladimir Cvjetkovic
 */

public class ItemActivity extends AppCompatActivity {

    EditText txtName, txtPrice;
    Button save, update, delete;
    ProgressDialog progressDialog;

    private String refreshFlag = "0";
    String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtName = (EditText) findViewById(R.id.txtName);
        txtPrice = (EditText) findViewById(R.id.txtPrice);
        save = (Button) findViewById(R.id.save);
        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);

        Intent intent = getIntent();
        if (intent.hasExtra("item")) {

            Item item = new Item();
            item = (Item) intent.getSerializableExtra("item");

            save.setVisibility(View.GONE);
            update.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);

            id = item.getId().toString();
            txtName.setText(item.getNama());
            txtPrice.setText(item.getPrice().toString());

        }

        progressDialog = new ProgressDialog(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Loading ...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                refreshFlag = "1";

                String name = txtName.getText().toString();
                String price = txtPrice.getText().toString();

                ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                Call<StatusResponse> postItem = api.postItem(name, price);
                postItem.enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        progressDialog.hide();
                        Boolean status = response.isSuccessful();
                        if (status) {
                            Toast.makeText(ItemActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ItemActivity.this, "Error", Toast
                                    .LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {

                    }
                });
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Loading ...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                refreshFlag = "1";
                String name = txtName.getText().toString();
                String price = txtPrice.getText().toString();

                ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                Call<StatusResponse> putItem = api.putItem(id, name, price);
                putItem.enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        progressDialog.dismiss();
                        Boolean status = response.isSuccessful();
                        if (status) {
                            Toast.makeText(ItemActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ItemActivity.this, "Error", Toast.LENGTH_SHORT)
                                    .show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(ItemActivity.this, "Error Update", Toast.LENGTH_SHORT)
                                .show();
                        finish();

                    }
                });

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Loading ...");
                progressDialog.setCancelable(false);
                progressDialog.show();

                refreshFlag = "1";

                ApiRequest api = Retroserver.getClient().create(ApiRequest.class);
                Call<StatusResponse> deleteItem = api.deleteItem(id);
                deleteItem.enqueue(new Callback<StatusResponse>() {
                    @Override
                    public void onResponse(Call<StatusResponse> call, Response<StatusResponse> response) {
                        progressDialog.dismiss();
                        Boolean status = response.isSuccessful();
                        if (status) {
                            Toast.makeText(ItemActivity.this, "Success", Toast.LENGTH_SHORT)
                                    .show();
                            finish();
                        } else {
                            Toast.makeText(ItemActivity.this, "Error", Toast.LENGTH_SHORT)
                                    .show();
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<StatusResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(ItemActivity.this, "Error Delete", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

            }
        });

    }

    @Override
    public void finish() {
        System.gc();
        Intent data = new Intent();
        data.putExtra("refreshFlag", refreshFlag);

        setResult(RESULT_OK, data);

        super.finish();
    }
}
