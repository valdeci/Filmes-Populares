package br.com.valdecipedroso.filmesfamosos.data;

import android.content.Context;
import android.content.SharedPreferences;

import br.com.valdecipedroso.filmesfamosos.R;

/**
 * Created by Valdecipti on 01/10/2017.
 */

public class FilmesPreferences {
    private static final  String KEY_PREF_MENU = "id_menu_pref";
    private static final  String KEY_ORDER = "most_popular";

    public static Boolean getPreferredFilmesOrderPopular(Context context) {
        SharedPreferences settings = context.getSharedPreferences(KEY_PREF_MENU, 0);
        return settings.getBoolean(KEY_ORDER, true);
    }

    public static void setPreferredFilmesOrderPopular(Context context, int id) {
        SharedPreferences settings = context.getSharedPreferences(KEY_PREF_MENU, 0);
        SharedPreferences.Editor editor = settings.edit();
        Boolean popularChecked =  (id == R.id.action_check_popular);
        editor.putBoolean(KEY_ORDER, popularChecked);
        editor.apply();
    }
}
