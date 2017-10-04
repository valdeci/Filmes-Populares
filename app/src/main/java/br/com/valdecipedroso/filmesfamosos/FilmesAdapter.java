package br.com.valdecipedroso.filmesfamosos;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;


/**
 * Created by Valdecipti on 27/09/2017.
 */

class FilmesAdapter extends ArrayAdapter<Filme> {

    private static final String PARAM_URL_IMAGE = "http://image.tmdb.org/t/p/w185/";

    public FilmesAdapter(Context context) {
        super(context,0);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Filme filme = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.filme_list_item, parent, false);
        }

        ImageView image;
        image = (ImageView) convertView.findViewById(R.id.list_item_image);
        image.setAdjustViewBounds(true);

        if ((filme != null ? filme.getCartaz() : null) != null) {
            Picasso.with(getContext())
                    .load(PARAM_URL_IMAGE + filme.getCartaz())
                    .into(image);
        }

        return convertView;
    }

    public void setFilmesData(Filme[] filmesData) {
        clear();
        for (Filme filme: filmesData) {
            add(filme);
        }
    }
}
