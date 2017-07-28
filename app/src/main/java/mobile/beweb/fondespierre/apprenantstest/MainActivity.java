package mobile.beweb.fondespierre.apprenantstest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import mobile.beweb.fondespierre.apprenantstest.data.GetJsonApi;
import mobile.beweb.fondespierre.apprenantstest.data.Preferences;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    //In the MainActivity we initialize and create the RecyclerView & Textview who are displaying the preferences filters
    RecyclerView mRecyclerView;
    TextView mTextViewTown;
    TextView mTextViewSkill;
    TextView mTextViewPromo;
    //boolean to check if pref have been updated and so refresh data
    private static boolean PREFERENCES_HAVE_BEEN_UPDATED = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_liste_apprenant);

        //Textview to display pref filters
        mTextViewTown = (TextView) findViewById(R.id.la_filtre_ville_value);
        mTextViewSkill = (TextView) findViewById(R.id.la_filtre_skill_value);
        mTextViewPromo = (TextView) findViewById(R.id.la_filtre_promotion_value);

        //Get the filters prefs display them in TextView and Called filterRequest in GetJsonAPI
        //to make the volley request and fill the RecyclerView
        String[] pref = getPref();
        mTextViewTown.setText(pref[0]);
        mTextViewSkill.setText(pref[2]);
        mTextViewPromo.setText(pref[1]);
        new GetJsonApi(this, mRecyclerView).filterRequest(pref[1],pref[0],pref[2]);

        //Register the changelistener to receive a callback if preference has been updated
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Use AppCompatActivity's method getMenuInflater to get a handle on the menu inflater */
        MenuInflater inflater = getMenuInflater();
        /* Use the inflater's inflate method to inflate our menu layout to this menu */
        inflater.inflate(R.menu.options, menu);
        /* Return true so that the menu is displayed in the Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Catch the id of the menu item selected
        int id = item.getItemId();

        //if we select filters activity filters is launched
        if (id == R.id.filters) {
            Intent startFiltersActivity = new Intent(this, Filtres.class);
            startActivity(startFiltersActivity);
            return true;
        }
        //if we select reset_filters all filters are reset data are refresh
        else if (id == R.id.reset_filters) {
            new Preferences().resetFilters(this);
            String[] pref = getPref();
            mTextViewTown.setText(pref[0]);
            mTextViewSkill.setText(pref[2]);
            mTextViewPromo.setText(pref[1]);
            new GetJsonApi(this, mRecyclerView).filterRequest("toutes","toutes","tous");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //when we come back to mainActivity we getPref and update data
    @Override
    protected void onStart() {
        super.onStart();
        if (PREFERENCES_HAVE_BEEN_UPDATED) {
            String[] pref = getPref();
            mTextViewTown.setText(pref[0]);
            mTextViewSkill.setText(pref[2]);
            mTextViewPromo.setText(pref[1]);
            PREFERENCES_HAVE_BEEN_UPDATED = false;
            new GetJsonApi(this, mRecyclerView).filterRequest(pref[1],pref[0],pref[2]);
        }
    }

    //Get each pref of each filter and return
    public String[] getPref() {
        String[] pref = new String[3];
        pref[0] = new Preferences().getTownFilter(this);
        pref[1] = new Preferences().getPromoFilter(this);
        pref[2] = new Preferences().getSkillFilter(this);
        return pref;
    }

    // COMPLETED (8) Override onDestroy and unregister MainActivity as a SharedPreferenceChangedListener
    @Override
    protected void onDestroy() {
        super.onDestroy();

        /* Unregister MainActivity as an OnPreferenceChangedListener to avoid any memory leaks. */
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        PREFERENCES_HAVE_BEEN_UPDATED = true;
    }
}