package mobile.beweb.fondespierre.apprenantstest;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ImageLoadApi {

    Context context;

    public ImageLoadApi(Context context, ImageView pictureView){
        this.context = context;
        final ImageView pictureViewToFill = pictureView;
        getPicture(pictureViewToFill);

    }

    public void getPicture (ImageView imageView) {
        final ImageView pictureViewToFill = imageView;
        String url = "https://randomuser.me/api/";
        RequestQueue queue = VolleySingleton.getInstance(context).getRequestQueue();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String picture = response.getJSONArray("results")
                            .getJSONObject(0)
                            .getJSONObject("picture")
                            .getString("thumbnail");
                    new ImageLoadTask(picture, pictureViewToFill).execute();
                } catch (final JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context,
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjReq);
    }
}
