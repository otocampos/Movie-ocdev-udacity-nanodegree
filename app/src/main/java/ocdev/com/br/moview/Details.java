package ocdev.com.br.moview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Details extends AppCompatActivity {
    private TextView txttitulo;
    private ImageView imgcartaz;
    private TextView txtavaliacao;
    private TextView txtlancamento;
    private TextView txtsinopse;
    private String titulo, imgurl, sinopse, avaliacao, lancamento;
    private static final String URL__IMAGE_BASE = "http://image.tmdb.org/t/p/w185//";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details2);
        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT) && intent.hasExtra("indice")) {
            String arraycompleto = intent.getStringExtra(Intent.EXTRA_TEXT);
            String indicedebusca = intent.getStringExtra("indice");

            getDetails(arraycompleto, indicedebusca);


            txttitulo = (TextView) findViewById(R.id.titulo);
            imgcartaz = (ImageView) findViewById(R.id.imgcartaz);
            txtavaliacao = (TextView) findViewById(R.id.avaliacao);
            txtlancamento = (TextView) findViewById(R.id.lancamento);
            txtsinopse = (TextView) findViewById(R.id.sinopse);

            txttitulo.setText(titulo);
            Picasso.with(getApplicationContext()).load(URL__IMAGE_BASE + imgurl).into(imgcartaz);
            txtavaliacao.setText(avaliacao);
            txtlancamento.setText(lancamento);
            txtsinopse.setText(sinopse);
        }


    }

    public void getDetails(String arraycompleto, String indicedebusca) {
        JSONObject movieJson = null;
        try {
            movieJson = new JSONObject(arraycompleto);
            JSONArray MovieArray = movieJson.getJSONArray("results");

            JSONObject c = MovieArray.getJSONObject(Integer.valueOf(indicedebusca));
            this.imgurl = c.getString("poster_path");
            this.titulo = c.getString("title");
            this.sinopse = c.getString("overview");
            this.avaliacao = c.getString("vote_count");
            this.lancamento = c.getString("release_date");

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
