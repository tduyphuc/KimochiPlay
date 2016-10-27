package com.example.phuctdse61834.kimochiplay;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private GridView gridView;
    private ArrayList<Actress> actressList;
    private static String queryURL = "https://javrest-hoangph92.rhcloud.com/api/actress?hits=20&name=";
    private static String searchStr = "";
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actressList = new ArrayList<>();

        gridView = (GridView) findViewById(R.id.gridView);
        // start loading
        new LoadActresses().execute();
    }

    private class LoadActresses extends AsyncTask<Void, Void, Void>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            JSONHandler jsonHandler = new JSONHandler();

            // get JSON string
            String jsonString = jsonHandler.getJSONStringFromService(queryURL + searchStr);
            if(jsonString != null){
                try{
                    // make JSON obj from JSON string
                    JSONObject jsonObject = new JSONObject(jsonString);
                    // make JSON array which contain actresses
                    JSONArray jsonArray = jsonObject.getJSONArray(Actress.JSON_ARRAY);
                    // now convert element in jsonArray to Actress
                    for (int i = 0; i < jsonArray.length(); i++){
                        Actress actress = new Actress(jsonArray.getJSONObject(i));
                        actressList.add(actress);
                    }
                } catch (JSONException e) {
                    Log.e("Main", "JSON parsing causes error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Error !", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            else{
                Log.e("Main", "Can't get JSON");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Can't retrieve data. Please try again", Toast.LENGTH_LONG).show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(progressDialog.isShowing()){
                progressDialog.dismiss();
            }
            // adapter
            if(!actressList.isEmpty()){
                MyGridAdapter myGridAdapter = new MyGridAdapter(MainActivity.this);
                gridView.setAdapter(myGridAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Actress actress = actressList.get(position);
                        Intent intent = new Intent(MainActivity.this, InfoActivity.class);
                        Gson gson = new Gson();
                        String jsonString = gson.toJson(actress);
                        intent.putExtra("JSON_VALUE", jsonString);
                        startActivity(intent);
                    }
                });
            }
        }
    }

    public class MyGridAdapter extends BaseAdapter{
        private Context mContext;

        public MyGridAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public int getCount() {
            return actressList.size();
        }

        @Override
        public Object getItem(int position) {
            return actressList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;

            if(convertView == null){
                LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = new View(mContext);
                view = layoutInflater.inflate(R.layout.grid_view_item, parent, false);

            }else{
                view = (View) convertView;
            }

            Actress actress = actressList.get(position);
            // set name
            TextView txtName = (TextView) view.findViewById(R.id.item_text_name);
            txtName.setText(actress.getName());
            // set japan name
            TextView txtJapName = (TextView) view.findViewById(R.id.item_text_japan_name);
            txtJapName.setText(actress.getJapName());

            // dung thu vien Picasso ngoai de load hinh tu url
            ImageView imageView = (ImageView)view.findViewById(R.id.item_image);
            Picasso.with(MainActivity.this)
                    .load(actress.getImgURL())
                    .placeholder(R.drawable.up_ban)
                    .error(R.drawable.up_ban)
                    .into(imageView);

            return view;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.searchBar);
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchStr = query;
        actressList.clear();
        new LoadActresses().execute();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
