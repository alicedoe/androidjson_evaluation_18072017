package mobile.beweb.fondespierre.apprenantstest.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import mobile.beweb.fondespierre.apprenantstest.DetailapprenantActivity;
import mobile.beweb.fondespierre.apprenantstest.R;

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
    public View getView(int position, View convertView, ViewGroup parent) {

//        Toast.makeText(context, "holaaaaaaaaaaaaaa"+json.length(),
//                Toast.LENGTH_LONG)
//                .show();

        final int positiontab = position;


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
//                Toast.makeText(context, "holaaaaaaaaaaaaaa",
//                        Toast.LENGTH_LONG)
//                        .show();
                Intent intent = new Intent(getContext(), DetailapprenantActivity.class);
                getContext().startActivity(intent);
            }
        });


        return convertView;
    }

}