package diego.rodiguez.CRUD

import Modelos.Conexion
import android.content.Intent
import android.os.Bundle
import android.provider.Settings.Global
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception
import java.util.UUID

class Activity_Registrar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val txtNombre = findViewById<EditText>(R.id.txtNombre)
        val txtApellido = findViewById<EditText>(R.id.txtApellido)
        val txtCorreo = findViewById<EditText>(R.id.txtCorreo)
        val txtContrasena = findViewById<EditText>(R.id.txtPassword)

        val btnIniciar = findViewById<Button>(R.id.btnIniciar)
        val imvRegresar = findViewById<ImageView>(R.id.imvRegresar)

        imvRegresar.setOnClickListener {

            val activityLogin = Intent(this, Activity_login::class.java)

            startActivity(activityLogin)
            finish()
        }


        try {
            GlobalScope.launch(Dispatchers.IO){

                val objConexion = Conexion().cadenaConexion()

                val crearUsuario = objConexion?.prepareStatement("INSERT INTO TB_USUARIO(Nombre,Apellido,Correo,Contraseña) VALUES(?,?,?,?)")!!

                crearUsuario.setString(1,txtNombre.text.toString())
                crearUsuario.setString(2,txtApellido.text.toString())
                crearUsuario.setString(3,txtCorreo.text.toString())
                crearUsuario.setString(4,txtContrasena.text.toString())

                crearUsuario.executeUpdate()

                withContext(Dispatchers.Main){

                    Toast.makeText(this@Activity_Registrar,"Usuario creado chele", Toast.LENGTH_SHORT).show()
                }
            }
        }
        catch (ex:Exception){
            println("REGISTER:Capo este es el error entiende:$ex")
        }

    }
}