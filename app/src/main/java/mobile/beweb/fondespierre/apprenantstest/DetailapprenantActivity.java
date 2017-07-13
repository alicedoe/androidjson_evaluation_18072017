package mobile.beweb.fondespierre.apprenantstest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailapprenantActivity extends AppCompatActivity {

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

        String apprenant  = getIntent().getExtras().getString("apprenant");

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

//        Toast.makeText(this, "Apprenant : "+aprennantJson,
//                Toast.LENGTH_LONG)
//                .show();
//

        Button retour = (Button) findViewById(R.id.da_button_retour);
        retour.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                getApplicationContext().startActivity(intent);
            }
        });
    }
}
