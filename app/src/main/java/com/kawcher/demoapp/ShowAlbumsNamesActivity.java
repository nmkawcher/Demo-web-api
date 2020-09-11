package com.kawcher.demoapp;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ShowAlbumsNamesActivity extends AppCompatActivity {

   // public  static final String BASE_URL="https://jsonplaceholder.typicode.com/";
    private ImageButton logoutBtn;
    private Adapter adapter;
    private RecyclerView recyclerView;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_show_albums_names);

        recyclerView=findViewById(R.id.recyclerview);
        logoutBtn=findViewById(R.id.logoutBtn);

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiResponse apiResponse=retrofit.create(ApiResponse.class);

        Call<List<ApiModel>> listCall = apiResponse.getResponse();


        listCall.enqueue(new Callback<List<ApiModel>>() {
            @Override
            public void onResponse(Call<List<ApiModel>> call, Response<List<ApiModel>> response) {

          if(response.isSuccessful()){



              List<ApiModel>albumDataList=response.body();


              adapter=new Adapter(getApplicationContext(),albumDataList);
              recyclerView.setAdapter(adapter);


          }

            }

            @Override
            public void onFailure(Call<List<ApiModel>> call, Throwable t) {

            }
        });


        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                firebaseAuth=FirebaseAuth.getInstance();

               firebaseAuth.signOut();
               startActivity(new Intent(getApplicationContext(),LoginActivity.class));

            }
        });




    }
}