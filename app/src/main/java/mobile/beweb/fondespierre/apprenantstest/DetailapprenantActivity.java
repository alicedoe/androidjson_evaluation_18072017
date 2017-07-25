package mobile.beweb.fondespierre.apprenantstest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class DetailapprenantActivity extends AppCompatActivity {

    //constructor
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_apprenant);
        JSONObject aprennantJson=null;
        TextView nomT = (TextView) findViewById(R.id.da_textview_nom);
        TextView prenomT = (TextView) findViewById(R.id.da_textview_prenom);
        TextView villeT = (TextView) findViewById(R.id.da_textview_ville);
        TextView descriptionT = (TextView) findViewById(R.id.da_textview_description);
        RatingBar ratingbarT = (RatingBar) findViewById(R.id.da_ratingbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Fetch string passing from the previous activity containing the apprenant details
        String apprenant  = getIntent().getExtras().getString("apprenant");

        //Insert data from JSONObject in the views
        try {

            aprennantJson = new JSONObject(apprenant);
            nomT.setText(aprennantJson.getString("nom"));
            prenomT.setText(aprennantJson.getString("prenom"));
            villeT.setText(aprennantJson.getString("ville"));
            descriptionT.setText(aprennantJson.getString("description"));
            ratingbarT.setRating(aprennantJson.getInt("skill"));

        } catch (JSONException e) {
            Toast.makeText(this, "Erreur : "+e,
                    Toast.LENGTH_LONG)
                    .show();
        }

        //Fetch from another API a random picture just for fun
        ImageView pictureView = (ImageView) findViewById(R.id.da_imageView);
        new ImageLoadApi(this, pictureView);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
