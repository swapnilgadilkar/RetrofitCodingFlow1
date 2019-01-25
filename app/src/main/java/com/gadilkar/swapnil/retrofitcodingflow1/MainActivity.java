package com.gadilkar.swapnil.retrofitcodingflow1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textView = findViewById(R.id.tvName);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaveHolderApi jsonPlaveHolderApi = retrofit.create(JsonPlaveHolderApi.class);

        Call<List<Post>> call = jsonPlaveHolderApi.getPosts();
        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                if(!response.isSuccessful())
                {
                    Toast.makeText(MainActivity.this,"Connection Failed",Toast.LENGTH_SHORT).show();
                }
                List<Post> posts = response.body();
                for (Post post: posts)
                {
                    String content = " ";
                    content+="Id:"+post.getId()+"\n";
                    content+="UserId:"+post.getUserId()+"\n";
                    content+="Title:"+post.getTitle()+"\n";
                    content+="Text:"+post.getBody()+"\n\n";
                    textView.append(content);

                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });


    }
}
