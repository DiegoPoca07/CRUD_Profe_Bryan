package diego.rodiguez.CRUD

import Modelos.Conexion
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.sql.Connection

class Activity_login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        val txtPassworld = findViewById<EditText>(R.id.txtPassworld)
        val  txtEmail = findViewById<EditText>(R.id.txtEmail)
        val lbAbrirRester = findViewById<TextView>(R.id.lbAbrirRegister)

        lbAbrirRester.setOnClickListener {
            val pantallaResgiter = Intent(this, Activity_Registrar::class.java)

            startActivity(pantallaResgiter)

            finish()
        }

        btnIngresar.setOnClickListener {

            val pantallaPrincipal = Intent( this, MainActivity::class.java)

            GlobalScope.launch(Dispatchers.IO){

                val objConexion = Conexion().cadenaConexion()

                val comprobarUsuario = objConexion?.prepareStatement("SELECT * FROM tb_usuario WHERE CORREO =? AND CONTRASEÑA=?")!!

                comprobarUsuario.setString(1,txtEmail.text.toString())
                comprobarUsuario.setString(2,txtPassworld.text.toString())

                val resultado =comprobarUsuario.executeQuery()

                if (resultado.next()){
                    startActivity(pantallaPrincipal)
                }
                else{
                    withContext(Dispatchers.Main){
                        Toast.makeText(this@Activity_login,"Credendeciales invalidas chele", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

    }
}