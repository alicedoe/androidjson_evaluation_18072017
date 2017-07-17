package mobile.beweb.fondespierre.apprenantstest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import mobile.beweb.fondespierre.apprenantstest.Adapter.GetJsonApi;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //In the MainActivity we initialize and create the RecyclerView and the 3 spinners
    //Set adapter on each Spinner and attach a listener to check when items are selected
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_liste_apprenant);

        Spinner spinnerPromo = (Spinner) findViewById(R.id.la_spinner_promo);
        ArrayAdapter<CharSequence> adapterSpinnerPromo = ArrayAdapter.createFromResource(this,
                R.array.promotion, android.R.layout.simple_spinner_item);
        adapterSpinnerPromo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPromo.setAdapter(adapterSpinnerPromo);

        Spinner spinnerSession = (Spinner) findViewById(R.id.la_spinner_session);
        ArrayAdapter<CharSequence> adapterSpinnerSession = ArrayAdapter.createFromResource(this,
                R.array.session, android.R.layout.simple_spinner_item);
        adapterSpinnerSession.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSession.setAdapter(adapterSpinnerSession);

        Spinner spinnerSkill = (Spinner) findViewById(R.id.la_spinner_skills);
        ArrayAdapter<CharSequence> adapterSpinnerSkill = ArrayAdapter.createFromResource(this,
                R.array.skill, android.R.layout.simple_spinner_item);
        adapterSpinnerSkill.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSkill.setAdapter(adapterSpinnerSession);

        spinnerPromo.setOnItemSelectedListener(this);
        spinnerSession.setOnItemSelectedListener(this);
        spinnerSkill.setOnItemSelectedListener(this);

        //Called filterRequest in GetJsonAPI to make the volley request and fill the RecyclerView
        new GetJsonApi(this, mRecyclerView).filterRequest("Toutes","Toutes","Tout");

    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        //When a filter is set in a spinner we get selected item on each spinner and called filterRequest in
        //GetJsonAPI with items selected
        Spinner spinnerPromo = (Spinner) findViewById(R.id.la_spinner_promo);
        String choixSpinnerPromo = spinnerPromo.getSelectedItem().toString();
        Spinner spinnerSession = (Spinner) findViewById(R.id.la_spinner_session);
        String choixSpinnerSession = spinnerSession.getSelectedItem().toString();
        Spinner spinnerSkill = (Spinner) findViewById(R.id.la_spinner_skills);
        String choixSpinnerSkill = spinnerSkill.getSelectedItem().toString();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_liste_apprenant);
        new GetJsonApi(this, mRecyclerView).filterRequest(choixSpinnerPromo,choixSpinnerSession,choixSpinnerSkill);

    }

    public void onNothingSelected(AdapterView<?> parent) {
    }

}