package co.edu.unipiloto.restservicesapp;

import android.os.Bundle;
import android.util.Log;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

    public class FormularioActivity extends AppCompatActivity {

        private EditText userIdEditText;
        private EditText titleEditText;
        private EditText bodyEditText;
        private PostService postService;

        @Override
        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_formulario);

            userIdEditText = findViewById(R.id.userIdEditText);
            titleEditText = findViewById(R.id.titleEditText);
            bodyEditText = findViewById(R.id.bodyEditText);
            Button submitPostButton = findViewById(R.id.submitPostButton);

            postService = ApiCliente.getPostService();
            submitPostButton.setOnClickListener(v -> crearPost());

        }

        private void crearPost() {

            String userIdTexto = userIdEditText.getText().toString().trim();
            String titulo = titleEditText.getText().toString().trim();
            String contenido = bodyEditText.getText().toString().trim();

            if (userIdTexto.isEmpty() || titulo.isEmpty() || contenido.isEmpty()) {

                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
                return;

            }

            int userId;

            try {

                userId = Integer.parseInt(userIdTexto);

            } catch (NumberFormatException e) {

                Toast.makeText(this, "El ID de usuario debe ser un número", Toast.LENGTH_SHORT).show();
                return;

            }

            if (userId <= 0) {

                Toast.makeText(this, "El ID de usuario debe ser mayor que cero", Toast.LENGTH_SHORT).show();
                return;

            }

            Post nuevaPublicacion = new Post(userId, titulo, contenido);

            submitPostButtonDeshabilitar();

            postService.crearPost(nuevaPublicacion).enqueue(new Callback<Post>() {

                        @Override
                        public void onResponse(Call<Post> call, Response<Post> response) {

                            habilitarBoton();

                            if (response.isSuccessful() && response.body() != null) {

                                Post creada = response.body();

                                Toast.makeText(FormularioActivity.this, "Publicación enviada. ID: "
                                                + creada.getId(), Toast.LENGTH_LONG).show();
                                finish();



                            }

                            Log.d("REST_POST", "Código HTTP: " + response.code());
                            Log.d("REST_POST", "Respuesta exitosa: " + response.isSuccessful());

                            if (response.body() != null) {

                                Log.d("REST_POST", "ID recibido: " + response.body().getId());
                                Log.d("REST_POST", "Título recibido: " + response.body().getTitle());

                            }else {
                                Toast.makeText(FormularioActivity.this, "Error HTTP: " + response.code(), Toast.LENGTH_LONG).show();
                            }

                        }

                        @Override
                        public void onFailure(Call<Post> call, Throwable t) {

                            habilitarBoton();

                            Log.e("REST_POST", "Falló la petición", t);
                            Toast.makeText(FormularioActivity.this, "Error de conexión: "
                                            + t.getMessage(), Toast.LENGTH_LONG).show();

                        }

                    });

        }

        private void submitPostButtonDeshabilitar() {
            findViewById(R.id.submitPostButton).setEnabled(false);
        }

        private void habilitarBoton() {
            findViewById(R.id.submitPostButton).setEnabled(true);
        }
    }
