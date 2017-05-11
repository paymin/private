package id.sch.smktelkom_mlg.privateassignment.xirpl114.aprivate;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static id.sch.smktelkom_mlg.privateassignment.xirpl114.aprivate.R.id.recyclerViewBanner;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    //private static final String URL_DATA = "https://gateway.marvel.com/v1/public/characters?nameStartsWith=s&ts=3000&apikey=16100b69a5d13288028d2c560b952df9&hash=0e633446d286a31949660cc2528af4ec";
    //private static final String URL_DATA = "https://gateway.marvel.com/v1/public/comics?ts=3000&apikey=16100b69a5d13288028d2c560b952df9&hash=0e633446d286a31949660cc2528af4ec";
    private static final String URL_DATA = "https://api.nytimes.com/svc/movies/v2/reviews/search.json?api-key=f5abb6679ae54501a9be8139e683bebc";

    private RecyclerView recyclerView;
    private RecyclerView recyclerViewBanner;
    private RecyclerView.Adapter adapter;
    private RecyclerView.Adapter adaptera;

    private List<MainListItem> listItems;
    private List<BannerItem> bannerItemList;

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        LinearLayoutManager layoutManagera
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        recyclerViewBanner = (RecyclerView) view.findViewById(R.id.recyclerViewBanner);
        recyclerViewBanner.setHasFixedSize(true);
        recyclerViewBanner.setLayoutManager(layoutManagera);

        listItems = new ArrayList<>();
        bannerItemList = new ArrayList<>();

        loadRecyclerViewData();
        //loadRecyclerViewData2();

       /*for(int i = 0; i<=10; i++){
            MainListItem listItem = new MainListItem(
                    "heading" + (i+1),
                    "Lorem ipsum"

            );

            listItems.add(listItem);
        }

        adapter = new MainAdapter(listItems, getActivity());

        recyclerView.setAdapter(adapter);*/
        return view;
    }

    private void loadRecyclerViewData() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
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

                            //JSONArray array = jsonObject.getJSONObject("data").getJSONArray("results");
                            //JSONArray array2 = jsonObject.getJSONArray("multimedia");

                            for(int i = 0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                MainListItem item = new MainListItem(
                                        o.getJSONObject("multimedia").getString("src"),
                                        o.getString("display_title"),
                                        o.getString("byline")
                                );
                                listItems.add(item);
                            }
                            adapter = new MainAdapter(listItems, getActivity().getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    private void loadRecyclerViewData2() {
        final ProgressDialog progressDialog = new ProgressDialog(getActivity());
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

                            //JSONArray array = jsonObject.getJSONObject("data").getJSONArray("results");
                            JSONArray array = jsonObject.getJSONArray("results");
                            //JSONArray array2 = jsonObject.getJSONArray("multimedia");

                            for(int i = 0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                BannerItem itemi = new BannerItem(
                                        o.getJSONObject("multimedia").getString("src")
                                );
                                bannerItemList.add(itemi);
                            }
                            adaptera = new BannerAdapter(bannerItemList, getActivity().getApplicationContext());
                            recyclerViewBanner.setAdapter(adaptera);

                        } catch (JSONException e){
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity().getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

}
