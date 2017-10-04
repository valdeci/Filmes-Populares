package br.com.valdecipedroso.filmesfamosos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by Valdecipti on 01/10/2017.
 */

class OpenFilmesJsonUtils {
    public static Filme[] getFilmesStringsFromJson(String forecastJsonStr)
            throws JSONException {

        final String OWM_LIST = "results";

        final String OWM_TITULO = "title";
        final String OWM_CARTAZ = "poster_path";
        final String OWM_SINOPSE = "overview";
        final String OWM_AVALIACAO = "vote_average";
        final String OWM_DATA_LANCAMENTO = "release_date";

        final String OWM_MESSAGE_CODE = "cod";

        JSONObject forecastJson = new JSONObject(forecastJsonStr);

        if (forecastJson.has(OWM_MESSAGE_CODE)) {
            int errorCode = forecastJson.getInt(OWM_MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    return null;
            }
        }

        JSONArray moviesArray = forecastJson.getJSONArray(OWM_LIST);

        Filme[] parsedFilmesData = new Filme[moviesArray.length()];

        for (int i = 0; i < moviesArray.length(); i++) {
            String tituloOriginal;
            String cartaz;
            String sinopse;
            Double avaliacao;
            String dataLancamento;

            JSONObject movie = moviesArray.getJSONObject(i);

            tituloOriginal = movie.getString(OWM_TITULO);
            cartaz = movie.getString(OWM_CARTAZ);
            sinopse = movie.getString(OWM_SINOPSE);
            avaliacao = movie.getDouble(OWM_AVALIACAO);
            dataLancamento = movie.getString(OWM_DATA_LANCAMENTO);

            Filme newFilme = new Filme(tituloOriginal, cartaz, sinopse, avaliacao, dataLancamento);
            parsedFilmesData[i] = newFilme;
        }

        return parsedFilmesData;
    }
}
