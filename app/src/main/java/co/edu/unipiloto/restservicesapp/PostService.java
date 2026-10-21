package co.edu.unipiloto.restservicesapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/*
*
* ¿Qué hace cada metodo?
*
* - @GET("posts"): Solicita la lista de publicaciones al servidor.
* - Call<List<Post>> Representa una petición que devolverá una lista de objetos Post.
* - @POST("posts"): Envía una nueva publicación al servidor.
* - @Body Post post: Convierte el objeto Java en un cuerpo JSON para enviarlo.
* - Call<Post> Representa la respuesta de la operación POST.
*
* */
public interface PostService {

    @GET("posts")
    Call<List<Post>> obtenerPosts();

    @POST("posts")
    Call<Post> crearPost(@Body Post post);

}
