package mobile.beweb.fondespierre.apprenantstest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;

public class DetailapprenantActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailapprenant);
        Bundle extras = getIntent().getExtras();
        final int position = extras.getInt("position");

        String[] xmlidentity = getResources().getStringArray(R.array.identity);
        String[] detail = xmlidentity[position].split(" ");

        Log.d("detail", Arrays.toString(xmlidentity));

//        TextView nomT = (TextView) findViewById(R.id.da_textview_nom);
//        nomT.setText(detail[1]);
//
//        TextView prenomT = (TextView) findViewById(R.id.da_textview_prenom);
//        prenomT.setText(detail[2]);
//
//        TextView villeT = (TextView) findViewById(R.id.da_textview_ville);
//        villeT.setText(detail[4]);

        Button button = (Button) findViewById(R.id.da_button_retour);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent retour = new Intent(DetailapprenantActivity.this, MainActivity.class);
                startActivity(retour);
            }
        });
    }
}
