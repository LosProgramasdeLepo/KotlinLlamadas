package com.example.kotlinllamadas.fragments

import android.Manifest
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinllamadas.R
import com.example.kotlinllamadas.adapter.AdapterContactos
import com.example.kotlinllamadas.objetos.Contacto
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class FragmentMenu : Fragment() {

    lateinit var v: View
    private var flagAdd: Boolean = true
    private var flagCall: Boolean = false
    private var flagActivarAgregar: Boolean = false
    private var contactos: MutableList<Contacto> = ArrayList()
    private lateinit var recyclerContacto: RecyclerView
    private lateinit var btnAgregar: FloatingActionButton
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var contactosListAdapter: AdapterContactos

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        v = inflater.inflate(R.layout.fragment_menu, container, false)
        recyclerContacto = v.findViewById(R.id.recycler)
        btnAgregar = v.findViewById(R.id.fab_add_patient)
        recyclerContacto.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(context)
        recyclerContacto.layoutManager = linearLayoutManager
        contactosListAdapter = AdapterContactos(contactos!!) { position -> onItemClick(position) }
        recyclerContacto.adapter = contactosListAdapter

        return v
    }

    override fun onStart() {
        super.onStart()
        if (flagAdd) {
            //Items de la lista por defecto
            contactos.add(Contacto("EMERGENCIAS","107", R.drawable.ic_baseline_local_taxi_24))
            contactos.add(Contacto("POLICÍA","911", R.drawable.ic_baseline_local_pizza_24))
            contactos.add(Contacto("BOMBEROS","100", R.drawable.ic_baseline_fireplace_24))
            contactos.add(Contacto("PIZZERÍA","7932-1281", R.drawable.ic_baseline_local_pizza_24))
            flagAdd = false
        }

        btnAgregar.setOnClickListener {
            flagActivarAgregar = true
            Log.d("TAGBOTON", contactos.toString())
            val action = FragmentMenuDirections.actionFragmentMenuToFragmentAgregar(contactos.toTypedArray())
            v.findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()

        //Guardar sólo si es distinto a null
        if(flagActivarAgregar) {
            Log.d("TAGBOTON", "mesi")
            contactos = FragmentMenuArgs.fromBundle(requireArguments()).listaContactos1.toMutableList()
            contactosListAdapter.notifyDataSetChanged()
            flagActivarAgregar = false
        }

    }

    ///////////////////////////////////////////Aquí empiezan los permisos y llamadas///////////////////////////////////////////

    private fun onItemClick(position: Int) {
        checkPermission()
        if(flagCall) {
            startCall(position, contactos)
        }
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            Snackbar.make(v, "capo, necesitamos ese permiso", Snackbar.LENGTH_SHORT).show()

            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.CALL_PHONE)) {
                //Pide permiso
                Snackbar.make(v, "aceptá porfa", Snackbar.LENGTH_SHORT).show()
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CALL_PHONE), 42)
            }

            else {
                //Pide permiso 2
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CALL_PHONE), 42)
            }
        }

        else {
            //Hay permiso
            flagCall = true
            Snackbar.make(v, "bien, permiso dado", Snackbar.LENGTH_SHORT).show()
        }

        return

    }

    //Llama si los permisos fueron aceptados
    private fun startCall(position: Int, listaContactos: MutableList<Contacto>) {
        if (flagCall){
            //Cambiar el DIAL por call para que llame
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${listaContactos[position].numero}"))
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return
        }
            startActivity(intent)
        }
    }

}