package mobile.beweb.fondespierre.apprenantstest.data;

import android.content.Context;
import android.content.Intent;
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
import org.json.JSONException;
import org.json.JSONObject;

import mobile.beweb.fondespierre.apprenantstest.Adapter.ListApprenantAdapter;
import mobile.beweb.fondespierre.apprenantstest.DetailapprenantActivity;
import mobile.beweb.fondespierre.apprenantstest.R;
import mobile.beweb.fondespierre.apprenantstest.VolleySingleton;


public class GetJsonApi implements ListApprenantAdapter.ListApprenantAdapterOnClickHandler {

    private static final String TAG = "MainActivity";

    private ListApprenantAdapter listeApprenantAdapter;
    private Context context;
    private RecyclerView mRecyclerView;

    //constructor
    public GetJsonApi(Context context, RecyclerView mRecyclerView){
        this.context = context;
        this.mRecyclerView = mRecyclerView;

    }

    /**
     * Create & set the RecyclerView
     * Volley request to fetch API json
     *
     * @param spinnerPromo
     * @param spinnerTown
     * @param spinnerSkill
     */
    public void filterRequest(String spinnerPromo, String spinnerTown, String spinnerSkill) {
        //Spinner item are sent to sort the data
        final String promo = spinnerPromo;
        final String town = spinnerTown;
        final String skill = spinnerSkill;

        //parameter for layout verticale orientations and false for reverse layout
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        //improve performance avoid to check size each times
        mRecyclerView.setHasFixedSize(true);

        //The listeApprenantAdapter is used to bind the data with the views
        listeApprenantAdapter = new ListApprenantAdapter(this);
        mRecyclerView.setAdapter(listeApprenantAdapter);

        //Volley request create a queue with the request from the url
        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();
        JsonArrayRequest jsonreq = new JsonArrayRequest("http://900grammes.fr/api/",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        //response came back with all data and is sent to the adapter
                        response = sortArray(response, promo, town, skill);
                        listeApprenantAdapter.setApprenantsData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        //add request to queue and start it
        queue.add(jsonreq);
    }

    //Display details of apprenant
    @Override
    public void onClick(JSONObject apprenantDetails) {
        Intent intent = new Intent(context, DetailapprenantActivity.class);
        intent.putExtra("apprenant",apprenantDetails.toString());
        context.getApplicationContext().startActivity(intent);
    }


    /**
     *
     *Using parameters from spinners this method is used to sorted data
     *
     * @param response JSONArray with data from api
     * @param spinnerPromo Item selected from spinnerpromo
     * @param spinnerTown Item selected from spinnersession
     * @param spinnerSkill Item selected from spinnerskill
     * @return JSONArray response containing the data filtered
     */
    public JSONArray sortArray(JSONArray response, String spinnerPromo, String spinnerTown, String spinnerSkill) {
        //sort for promo
        if(!spinnerPromo.equals("toutes")){
            JSONArray filterTab = new JSONArray();
            for (int i=1;i<=response.length();i++){
                try {
                    JSONObject ligne = response.getJSONObject(i);
                    if(ligne.get("promotion").equals(spinnerPromo)){
                        filterTab.put(ligne);
                    };
                } catch (JSONException e){
                }
            }
            //result stock in response JSONArray
            response = filterTab;
        }
        //sort for session
        if(!spinnerTown.equals("toutes")){
            JSONArray filterTab = new JSONArray();
            for (int i=1;i<=response.length();i++){
                try {
                    JSONObject ligne = response.getJSONObject(i);
                    if(ligne.get("ville").equals(spinnerTown)){
                        Log.d("beziers",spinnerTown.toString());
                        switch (spinnerTown) {
                            case "beziers":
                                JSONObject ligneBeziers = ligne;
                                ligneBeziers.put("ville", context.getString(R.string.filter_town_label_beziers));
                                filterTab.put(ligneBeziers);
                                break;
                            case "lunel":
                                JSONObject ligneLunel = ligne;
                                ligneLunel.put("ville", context.getString(R.string.filter_town_label_lunel));
                                filterTab.put(ligneLunel);
                                break;
                        }
                    };
                } catch (JSONException e){

                }
            }
            //result stock in response JSONArray
            response = filterTab;
        }
        //sort for skill
        if(!spinnerSkill.equals("tous")){
            JSONArray filterTab = new JSONArray();
            for (int i=1;i<=response.length();i++){
                try {
                    JSONObject ligne = response.getJSONObject(i);
                    switch (spinnerSkill) {
                        case "niveau1": spinnerSkill=String.valueOf(1); break;
                        case "niveau2": spinnerSkill=String.valueOf(2); break;
                        case "niveau3": spinnerSkill=String.valueOf(3); break;
                        case "niveau4": spinnerSkill=String.valueOf(4); break;
                        case "niveau5": spinnerSkill=String.valueOf(5); break;
                    }
                    if(ligne.get("skill").equals(spinnerSkill)){
                        filterTab.put(ligne);
                    };
                } catch (JSONException e){
                }
            }
            //result stock in response JSONArray
            response = filterTab;
        }
        Toast.makeText(context, "Nombre d'apprenants : "+String.valueOf(response.length()),Toast.LENGTH_SHORT).show();

        return response;
    }
}
