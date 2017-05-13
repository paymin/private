package id.sch.smktelkom_mlg.privateassignment.xirpl114.aprivate;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.disklrucache.DiskLruCache;
import com.orm.SugarContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import static android.R.attr.button;
import static android.R.attr.state_last;
import static android.opengl.ETC1.isValid;
import static id.sch.smktelkom_mlg.privateassignment.xirpl114.aprivate.R.id.end;
import static id.sch.smktelkom_mlg.privateassignment.xirpl114.aprivate.R.id.imageView;
import static id.sch.smktelkom_mlg.privateassignment.xirpl114.aprivate.R.id.recyclerView;
import static id.sch.smktelkom_mlg.privateassignment.xirpl114.aprivate.R.id.textViewDescet;

public class DetailActivity extends AppCompatActivity {
    private Integer mPostkey = null;
    private static final String URL_DATA = "https://api.nytimes.com/svc/movies/v2/reviews/search.json?api-key=f5abb6679ae54501a9be8139e683bebc";

    public TextView textViewHeadet;
    public TextView textViewDescet;
    public TextView textViewReview;
    public ImageView imageViewDetail;
    public String url;
    public String urlGambar;
    FavouriteItem favouriteItem;
    boolean isPressed = true;
    FloatingActionButton fab;
    boolean isNew;
    ArrayList<FavouriteItem> fItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //SugarContext.init(this);

        mPostkey = getIntent().getExtras().getInt("blog_id");
        loadRecyclerViewData();

        textViewHeadet = (TextView) findViewById(R.id.textViewHeadet);
        textViewDescet = (TextView) findViewById(R.id.textViewDescet);
        textViewReview = (TextView) findViewById(R.id.textViewReview);
        imageViewDetail = (ImageView) findViewById(R.id.imageViewDetail);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPressed) {
                    doSave();
                    Snackbar.make(view, "Berhasil ditambahkan ke favorit", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    doDelete();
                }
                else{

                    Snackbar.make(view, "Artikel favorit anda", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                isPressed = !isPressed;
            }
        });


        SharedPreferences.Editor editor = getSharedPreferences("Pos", MODE_PRIVATE).edit();
        editor.putInt("posisi", mPostkey);
        editor.commit();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    private void doDelete() {
        String judul = textViewHeadet.getText().toString();
        SharedPreferences.Editor editor = getSharedPreferences(judul, MODE_PRIVATE).edit();
        editor.putBoolean("isNew", false);
        editor.commit();
    }

    private void doSave() {
        String judul = textViewHeadet.getText().toString();
        String deskripsi = textViewDescet.getText().toString();
        String urlgambar = urlGambar;
                favouriteItem = new FavouriteItem(judul, deskripsi, urlgambar);
                favouriteItem.save();

        SharedPreferences.Editor editor = getSharedPreferences(judul, MODE_PRIVATE).edit();
        editor.putBoolean("isNew", true);
        editor.commit();
    }

    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        progressDialog.dismiss();
                        try{
                            JSONObject jsonObject = new JSONObject(s);
                            JSONArray array = jsonObject.getJSONArray("results");
                            JSONObject o = array.getJSONObject(mPostkey);

                            setTitle(" ");

                            textViewHeadet.setText(o.getString("display_title"));
                            textViewDescet.setText(o.getString("byline"));
                            textViewReview.setText(o.getString("summary_short"));
                            url = o.getJSONObject("link").getString("url");
                            urlGambar = o.getJSONObject("multimedia").getString("src");

                            Glide
                                    .with(DetailActivity.this)
                                    .load(o.getJSONObject("multimedia").getString("src"))
                                    .into(imageViewDetail);





                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
