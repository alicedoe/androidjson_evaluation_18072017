package mobile.beweb.fondespierre.apprenantstest.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.PreferenceScreen;
import android.util.Log;

import mobile.beweb.fondespierre.apprenantstest.R;

/**
 * Created by Aliiiiiiii on 26/07/2017.
 */

public class Preferences {

    public String getTownFilter(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String keyForTown = context.getString(R.string.filter_town_label);
        String defaultTown = context.getString(R.string.filter_town_key_all);
        String preferredTown = prefs.getString(keyForTown, defaultTown);
        return preferredTown;
    }

    public String getPromoFilter(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String keyForPromo = context.getString(R.string.filter_promo_label);
        String defaultPromo = context.getString(R.string.filter_promo_key_all);
        String preferredTown = prefs.getString(keyForPromo, defaultPromo);
        return preferredTown;
    }

    public String getSkillFilter(Context context) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(context);
        String keyForSkill = context.getString(R.string.filter_skill_label);
        String defaultSkill = context.getString(R.string.filter_skill_key_all);
        String preferredTown = prefs.getString(keyForSkill, defaultSkill);
        return preferredTown;
    }

    public void resetFilters(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}
