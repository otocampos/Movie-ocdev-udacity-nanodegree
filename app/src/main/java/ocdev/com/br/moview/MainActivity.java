package ocdev.com.br.moview;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;

import ocdev.com.br.moview.utils.NetworkUtils;
import ocdev.com.br.moview.utils.OpenMovieJsonUtils;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private ProgressBar mLoadingIndicator;
    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorMessageDisplay;
    private String jsonMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);


        GridLayoutManager layoutManager
                = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(layoutManager);


        mRecyclerView.setHasFixedSize(true);


        mMovieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(mMovieAdapter);


        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        loadMovieData();

    }

    private void loadMovieData() {
        showMovieDataView();

        new FetchMovieTask().execute("popular");
    }


    @Override
    public void onClick(String indice) {
        Context context = this;
        Intent intent = new Intent(context, Details.class);
        intent.putExtra(Intent.EXTRA_TEXT, jsonMovies);
        intent.putExtra("indice",indice);
        startActivity(intent);


    }


    private void showMovieDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }


    public class FetchMovieTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String[] doInBackground(String... params) {

            if (params.length == 0) {
                return null;
            }

            String movies = params[0];
            URL moviesrequest = NetworkUtils.buildUrl(movies);

            try {
                jsonMovies = NetworkUtils
                        .getResponseFromHttpUrl(moviesrequest);

                String[] jsonmoviesData = OpenMovieJsonUtils
                        .getSimpleMovieStringsFromJson(MainActivity.this, jsonMovies);

                return jsonmoviesData;

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] moviedata) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (moviedata != null) {
                showMovieDataView();
                mMovieAdapter.setMovieData(moviedata);
            } else {
                showErrorMessage();
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.popular) {
            Carregatipos("popular");
            return true;
        }
        if (id == R.id.rated) {
            Carregatipos("top_rated");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Carregatipos(String categoia) {
        showMovieDataView();
        new FetchMovieTask().execute(categoia);

    }
}
