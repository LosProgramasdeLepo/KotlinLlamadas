package com.example.kotlinllamadas.fragments

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinllamadas.ListaContactos.contactos
import com.example.kotlinllamadas.R
import com.example.kotlinllamadas.TinyDB
import com.example.kotlinllamadas.adapter.AdapterContactos
import com.example.kotlinllamadas.objetos.Contacto
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.util.*

class FragmentMenu : Fragment() {

    private lateinit var v: View
    private var flagCall: Boolean = false
    private lateinit var mContext: Context
    private lateinit var recyclerContacto: RecyclerView
    private lateinit var btnAgregar: FloatingActionButton
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var contactosListAdapter: AdapterContactos

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        v = inflater.inflate(R.layout.fragment_menu, container, false)
        recyclerContacto = v.findViewById(R.id.recycler)
        btnAgregar = v.findViewById(R.id.fab_add_patient)
        recyclerContacto.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerContacto.layoutManager = linearLayoutManager
        contactosListAdapter =  AdapterContactos(contactos) { position -> onItemClick(position) }
        recyclerContacto.adapter = contactosListAdapter

        //Contactos por defecto
        if (contactos.size < 4) {
            //Items de la lista por defecto
            contactos.add(
                Contacto(
                    "EMERGENCIAS",
                    "107",
                    R.drawable.ic_baseline_local_taxi_24,
                    "#FFFFFF"
                )
            )
            contactos.add(
                Contacto(
                    "POLICÍA",
                    "911",
                    R.drawable.ic_baseline_local_pizza_24,
                    "#FFFFFF"
                )
            )
            contactos.add(
                Contacto(
                    "BOMBEROS",
                    "100",
                    R.drawable.ic_baseline_fireplace_24,
                    "#FFFFFF"
                )
            )
            contactos.add(
                Contacto(
                    "PIZZERÍA",
                    "7932-1281",
                    R.drawable.ic_baseline_local_pizza_24,
                    "#FFFFFF"
                )
            )
            Log.d("dd", contactos.toString())
        }

        //Guarda los contactos por defecto en TinyDB
        mContext = requireActivity()
        val tinydb = TinyDB(mContext)
        tinydb.putListaContactos("Contactos", contactos)

        ///////////////////////////////////////////Acá empieza lo de mover items///////////////////////////////////////////

        val touchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val sourcePosition = viewHolder.adapterPosition
                val targetPosition = target.adapterPosition
                //Notifica los cambios
                Collections.swap(contactos, sourcePosition, targetPosition)
                contactosListAdapter.notifyItemMoved(sourcePosition, targetPosition)
                Log.d("dd", contactos.toString())
                val item: Contacto = contactos.get(sourcePosition)
                contactos.removeAt(sourcePosition)
                contactos.add(targetPosition, item)
                //Guarda cambios en TinyDB
                tinydb.putListaContactos("Contactos", contactos)
                return true
                //Hacer un índice para el objeto
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                TODO("Not yet implemented")
            }


        }
        val itemTouchHelper = ItemTouchHelper(touchHelper)
        itemTouchHelper.attachToRecyclerView(recyclerContacto)

        ///////////////////////////////////////////Acá termina lo de mover items///////////////////////////////////////////

        return v

    }

    //Acá hay botones
    override fun onStart() {
        super.onStart()
        //El botón que te lleva al fragment para agregar contactos
        btnAgregar.setOnClickListener {
            Log.d("lcenbotonagregar", contactos.toString())
            val action = FragmentMenuDirections.actionFragmentMenuToFragmentAgregar()
            v.findNavController().navigate(action)
        }
    }

    //Acá está lo relacionado a agregar contactos
    override fun onResume() {
        super.onResume()
        Log.d("lccuandovuelve", contactos.toString())
        //Notifica los cambios y los guarda en la base de datos
        mContext = requireActivity()
        val tinydb = TinyDB(mContext)
        contactos = tinydb.getListaContactos("Contactos")
    }

    ///////////////////////////////////////////Acá empiezan los permisos y llamadas///////////////////////////////////////////

    //Botón para llamar
    private fun onItemClick(position: Int) {
        checkPermission()
        if(flagCall) {
            startCall(position, contactos)
        }
    }

    //Chequea los permisos
    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(v, "capo, necesitamos ese permiso", Snackbar.LENGTH_SHORT).show()

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    requireActivity(),
                    Manifest.permission.CALL_PHONE
                )) {
                //Pide permiso
                Snackbar.make(v, "aceptá porfa", Snackbar.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42
                )
            }

            else {
                //Pide permiso 2
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CALL_PHONE),
                    42
                )
            }
        }

        else {
            //Hay permiso
            flagCall = true
            Snackbar.make(v, "bien, permiso dado", Snackbar.LENGTH_SHORT).show()
        }

        return

    }

    //Llama
    private fun startCall(position: Int, listaContactos: MutableList<Contacto>) {
        if (flagCall){
            //Cambiar el DIAL por call para que llame
            val intent = Intent(
                Intent.ACTION_DIAL,
                Uri.parse("tel:${listaContactos[position].numero}")
            )
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return
        }
            startActivity(intent)
        }
    }

    ///////////////////////////////////////////Acá terminan los permisos y llamadas///////////////////////////////////////////

}