package co.edu.unipiloto.restservicesapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
*
* Flujo de conexión de las clases
* ApiClient -> Retrofit -> PostService -> Servidor REST -> JSON -> Gson -> Objeto Post
*
* */
public class ApiCliente {

    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private static Retrofit retrofit;

    public static PostService getPostService() {

        if (retrofit == null) {

            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()
                    ).build();

        }

        return retrofit.create(PostService.class);
    }

}
