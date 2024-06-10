package RecyclerViewHelper

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import diego.rodiguez.CRUD.R

class ViewHolder(view: View): RecyclerView.ViewHolder(view){
   val textView: TextView = view.findViewById(R.id.txt_productoCard)
    val imgEditar: ImageView = view.findViewById(R.id.img_Editar)
    val imgBorrar: ImageView = view.findViewById(R.id.img_Borrar)

}