package co.edu.unipiloto.restservicesapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/*
*
* ¿Qué estamos haciendo?
* - ApiClient.getPostService() obtiene el servicio configurado con Retrofit.
* - obtenerPosts() prepara la petición GET.
* - enqueue() ejecuta la petición de forma asíncrona, sin bloquear la interfaz.
* - onResponse() procesa los datos recibidos.
* - onFailure() informa si falla la conexión.
* - ArrayAdapter muestra los títulos en el ListView.
*
* El flujo será:Botón "Consultar publicaciones" -> MainActivity -> PostService (GET) -> JSONPlaceholder -> Respuesta JSON
*               -> Gson -> Lista de Post -> ListView
*
* */

public class MainActivity extends AppCompatActivity {

    private ListView postsListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> publicaciones;
    private PostService postService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        postsListView = findViewById(R.id.postsListView);
        Button getPostsButton = findViewById(R.id.getPostsButton);

        publicaciones = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, publicaciones);

        postsListView.setAdapter(adapter);
        postService = ApiCliente.getPostService();
        getPostsButton.setOnClickListener(v -> consultarPosts());

        Button createPostButton = findViewById(R.id.createPostButton);

        createPostButton.setOnClickListener(v -> {

            Intent intent = new Intent(MainActivity.this, FormularioActivity.class);
            startActivity(intent);

        });

    }

    private void consultarPosts() {

        postService.obtenerPosts().enqueue(new Callback<List<Post>>() {

            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if (response.isSuccessful() && response.body() != null) {

                    publicaciones.clear();

                    for (Post post : response.body()) {
                        publicaciones.add(post.getId() + ". " + post.getTitle());
                    }

                    adapter.notifyDataSetChanged();

                    Toast.makeText(MainActivity.this, "Publicaciones recibidas: "
                            + publicaciones.size(), Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(MainActivity.this, "Error HTTP: "
                            + response.code(), Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {

                Toast.makeText(MainActivity.this, "Error de conexión: "
                                + t.getMessage(), Toast.LENGTH_LONG).show();

            }

        });

    }

}