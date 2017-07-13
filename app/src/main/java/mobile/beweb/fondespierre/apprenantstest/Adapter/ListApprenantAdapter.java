package mobile.beweb.fondespierre.apprenantstest.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import mobile.beweb.fondespierre.apprenantstest.DetailapprenantActivity;
import mobile.beweb.fondespierre.apprenantstest.MainActivity;
import mobile.beweb.fondespierre.apprenantstest.R;
import mobile.beweb.fondespierre.apprenantstest.VolleySingleton;

public class ListApprenantAdapter extends ArrayAdapter {

    private JSONArray json;
    private Activity context;
    private String nom;
    private String prenom;
    private String skill;
    private JSONObject apprenant;

    public ListApprenantAdapter(Activity mainContext, JSONArray mainJson) {
        super(mainContext, 0);
        json = mainJson;
        context = mainContext;
    }

    @Override
    public int getCount() {
        return json.length();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final int positiontab = position;

        String url = "https://randomuser.me/api/";
        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();
        JsonArrayRequest jsonreq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {


                        try {
                            String picture = response.getJSONObject(0).getJSONArray("results").getJSONObject(4).getString("thumbnail");
                            Toast.makeText(context,
                                    picture, Toast.LENGTH_SHORT).show();
                        } catch (final JSONException e) {

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonreq);


        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.listeapprenantitem, parent, false);
        }

        try {
            apprenant = json.getJSONObject(position);

            this.nom = apprenant.getString("nom");
            this.prenom = apprenant.getString("prenom");
            this.skill = apprenant.getString("skill");

        } catch (final JSONException e) {

        }

        TextView nomT = (TextView) convertView.findViewById(R.id.laItem_textview_nom);
        nomT.setText(this.nom);

        TextView prenomT = (TextView) convertView.findViewById(R.id.laItem_textview_prenom);
        prenomT.setText(this.prenom);

        TextView skillT = (TextView) convertView.findViewById(R.id.laItem_textview_skill);
        skillT.setText(this.skill);

        ImageButton detail = (ImageButton) convertView.findViewById(R.id.laItem_imagebutton_detail);
        detail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context, DetailapprenantActivity.class);
                intent.putExtra("apprenant",json.optJSONObject(positiontab).toString());
                context.getApplicationContext().startActivity(intent);
            }
        });


        return convertView;
    }

}