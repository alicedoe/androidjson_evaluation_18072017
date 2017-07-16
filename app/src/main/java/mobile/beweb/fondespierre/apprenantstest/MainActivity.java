package mobile.beweb.fondespierre.apprenantstest;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import mobile.beweb.fondespierre.apprenantstest.Adapter.ListApprenantAdapter;

public class MainActivity extends AppCompatActivity implements ListApprenantAdapter.ListApprenantAdapterOnClickHandler {
    private static final String TAG = "MainActivity";
    JSONArray apprenantsData = null;
    private RecyclerView mRecyclerView;
    private ListApprenantAdapter listeApprenantAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_liste_apprenant);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        listeApprenantAdapter = new ListApprenantAdapter(this);
        mRecyclerView.setAdapter(listeApprenantAdapter);

        RequestQueue queue = VolleySingleton.getInstance(MainActivity.this).getRequestQueue();
        JsonArrayRequest jsonreq = new JsonArrayRequest("http://900grammes.fr/api",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("Data : ", String.valueOf(response.length()));
                        listeApprenantAdapter.setWeatherData(response);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonreq);
    }
    @Override
    public void onClick(JSONObject apprenantDetails) {
        Intent intent = new Intent(MainActivity.this, DetailapprenantActivity.class);
        intent.putExtra("apprenant",apprenantDetails.toString());
        MainActivity.this.getApplicationContext().startActivity(intent);
    }

}