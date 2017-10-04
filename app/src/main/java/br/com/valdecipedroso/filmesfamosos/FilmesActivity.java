package br.com.valdecipedroso.filmesfamosos;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.net.URL;

import br.com.valdecipedroso.filmesfamosos.data.FilmesPreferences;
import br.com.valdecipedroso.filmesfamosos.utilities.NetworkUtils;

public class FilmesActivity extends AppCompatActivity {

    private TextView mErrorMessageDisplay;
    private GridView mGridview;
    private ProgressBar mLoadingIndicator;
    private FilmesAdapter mFilmesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filmes);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        mFilmesAdapter = new FilmesAdapter(this);
        mGridview = (GridView) findViewById(R.id.gridview);
        mGridview.setAdapter(mFilmesAdapter);

        mGridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Filme filmSelected = mFilmesAdapter.getItem(i);
                Intent it = new Intent(getBaseContext(), DetailActivity.class);
                it.putExtra(Intent.EXTRA_TEXT, filmSelected);
                startActivity(it);
            }
        });

        loadFilmesData();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item;
        getMenuInflater().inflate(R.menu.discovery, menu);
        Boolean popularChecked = FilmesPreferences.getPreferredFilmesOrderPopular(this);

        if (popularChecked){
            item  = menu.findItem(R.id.action_check_popular);
        }else{
            item = menu.findItem(R.id.action_check_rate);
        }
        item.setChecked(true);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_check_popular:
            case R.id.action_check_rate:
                item.setChecked(!item.isChecked());
                FilmesPreferences.setPreferredFilmesOrderPopular(this,id );
                loadFilmesData();
                break;
            case R.id.action_refresh:
                loadFilmesData();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadFilmesData() {
        showFilmesDataView();
        mLoadingIndicator.setVisibility(View.VISIBLE);
        Boolean orderbyMostPopular = FilmesPreferences.getPreferredFilmesOrderPopular(this);
        new FetchFilmesTask().execute(orderbyMostPopular);
    }

    private class FetchFilmesTask extends AsyncTask<Boolean, Void, Filme[]> {
        @Override
        protected Filme[] doInBackground(Boolean... booleen) {
            Boolean orderedByPopular = booleen[0];
            URL filmesRequestUrl = NetworkUtils.buildUrl(getBaseContext(), orderedByPopular);

            try {
                String jsonFilmesResponse = NetworkUtils
                        .getResponseFromHttpUrl(filmesRequestUrl);

                return OpenFilmesJsonUtils
                        .getFilmesStringsFromJson(jsonFilmesResponse);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Filme[] filmes) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (filmes != null) {
                showFilmesDataView();
                mFilmesAdapter.setFilmesData(filmes);
            } else {
                showErrorMessage();
            }
        }
    }

    private void showFilmesDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mGridview.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage() {
        mGridview.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }
}
