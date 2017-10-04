package br.com.valdecipedroso.filmesfamosos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView mTituloOriginal;
        ImageView mCartaz;
        TextView mSinopse;
        TextView mAvaliacao;
        TextView mDataLancamento;
        String PARAM_URL_IMAGE = "http://image.tmdb.org/t/p/w185/";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mTituloOriginal = (TextView) findViewById(R.id.tv_titulo_original);
        mCartaz = (ImageView) findViewById(R.id.tv_cartaz);
        mSinopse = (TextView) findViewById(R.id.tv_sinopse);
        mAvaliacao = (TextView) findViewById(R.id.tv_avaliacao);
        mDataLancamento = (TextView) findViewById(R.id.tv_data_lancamento);

        Intent it = getIntent();

        if (it.resolveActivity(getPackageManager()) !=  null){
            if(it.hasExtra(Intent.EXTRA_TEXT)){
                Filme filmes = it.getParcelableExtra(Intent.EXTRA_TEXT);
                if (filmes != null){
                   mTituloOriginal.setText(filmes.getTituloOriginal());
                   mSinopse.setText(filmes.getSinopse());
                   mAvaliacao.setText(String.format(getResources().getString(R.string.string_avaliacao), filmes.getAvaliacao()));
                   mDataLancamento.setText(filmes.getDataLancamento());

                   mCartaz.setAdjustViewBounds(true);

                   Picasso.with(this)
                          .load(PARAM_URL_IMAGE+filmes.getCartaz())
                          .into(mCartaz);
                }
            }
        }
    }
}
