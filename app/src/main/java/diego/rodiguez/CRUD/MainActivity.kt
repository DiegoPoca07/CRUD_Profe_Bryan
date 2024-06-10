package diego.rodiguez.CRUD

import Modelos.Conexion
import Modelos.DataClassTicket
import RecyclerViewHelper.Adaptador
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val txtTitulo = findViewById<EditText>(R.id.txtTitulo)
        val txtDescripcion = findViewById<EditText>(R.id.txtDescripcion)
        val txtAutor = findViewById<EditText>(R.id.txtDescripcion)
        val txtCorreo = findViewById<EditText>(R.id.txtCorreoticket)
        val txtEstaddo = findViewById<EditText>(R.id.txtEstado)
        val btnAgregar = findViewById<Button>(R.id.btnTicket)


        val rcvproductos = findViewById<RecyclerView>(R.id.rcvElementos)


        //asignar un layout al reciledview

        rcvproductos.layoutManager= LinearLayoutManager(this)

        //funcion para obtener datos
        fun obtenerDatos():List<DataClassTicket>{
            val objConexion=Conexion().cadenaConexion()

            val statement = objConexion?.createStatement()
            val resultSet=statement?.executeQuery("select * from TB_TICKET")!!

            val tickets = mutableListOf<DataClassTicket>()
            while (resultSet.next()){

                val numTicket = resultSet.getInt("NUM_TICKET")
                val titulo = resultSet.getString("TÍTULO")
                val descripcion = resultSet.getString("DESCRIPCIÓN")
                val autor = resultSet.getString("AUTOR")
                val correo = resultSet.getString("CORREO")
                val fechaCreación = resultSet.getDate("FECHA_CREACIÓN")
                val estado = resultSet.getString("ESTADO")
                val fechaFinalizacion = resultSet.getDate("FECHA_FINALIZACIÓN")

                val ticket = DataClassTicket(numTicket, titulo, descripcion, autor, correo, fechaCreación, estado, fechaFinalizacion)
                tickets.add(ticket)
            }
            return tickets
        }

        //asignar un adaptador CoroutineScope ( Dispatchers.IO) .launch {
        //            val Ticketsbd=obtenerDatos()
        //            withContext(Dispatchers.Main){
        //                val miAdapter = Adaptador(Ticketsbd)
        //                rcvproductos.adapter=miAdapter
        //            }

        CoroutineScope(Dispatchers.IO).launch {
            val Tickets = obtenerDatos()
            withContext(Dispatchers.Main){
                val miAdaptador =Adaptador(Tickets)

            }
        }

        btnAgregar.setOnClickListener{
            try {

                GlobalScope.launch (Dispatchers.IO){
                    //Abrir corrutina
                    val objConnection= Conexion().cadenaConexion()

                    val crearTicket = objConnection?.prepareStatement("INSERT INTO TB_TICKET (título, descripción, autor, correo,estado) VALUES (?,?,?,?,?)")!!

                    crearTicket.setString(1, txtTitulo.text.toString())
                    crearTicket.setString( 2, txtDescripcion.text.toString())
                    crearTicket.setString(3, txtAutor.text.toString())
                    crearTicket.setString(4, txtCorreo.text.toString())
                    crearTicket.setString(5, txtEstaddo.text.toString())
                    crearTicket.executeUpdate()

                   withContext(context = Dispatchers.Main){
                       android.widget.Toast.makeText(this@MainActivity,"ticket guardado pibe", Toast.LENGTH_SHORT).show()
                   }



                }


            }
            catch (ex:Exception){
                println("REGISTER: Loco este es el error:$ex")

            }
        }


    }
}