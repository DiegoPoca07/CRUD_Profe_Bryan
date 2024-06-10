package Modelos

import java.sql.Connection
import java.sql.DriverManager

class Conexion {

    fun cadenaConexion():Connection?{
         try {
            val url="jdbc:oracle:thin:@192.168.1.28:1521:xe"
            val user = "DIEGO_DEVELOPER"
            val password = "KUFEDU"

            val connection = DriverManager.getConnection(url, user, password)

            return connection
        }catch (ex:Exception){
            println("este es el error:$ex")
            return null
        }
    }
}
