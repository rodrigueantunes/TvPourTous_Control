package com.antunes.tvpourtous_control

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Inet4Address
import java.net.InetSocketAddress
import java.net.NetworkInterface
import java.net.Socket

class MainActivity : AppCompatActivity() {

    private lateinit var hostsAdapter: ApiHostsAdapter
    private lateinit var channelsAdapter: ChannelsAdapter
    private lateinit var api: TvPourTousApi
    private lateinit var textViewStatus: TextView
    private lateinit var editTextSearch: EditText

    // Conserver la liste complète des chaînes pour le filtre
    private var fullChannelsList: List<Channel> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_TvPourTous_Control)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonScan = findViewById<Button>(R.id.buttonScan)
        textViewStatus = findViewById(R.id.textViewStatus)
        editTextSearch = findViewById(R.id.editTextSearch)

        // Cacher le champ de recherche au démarrage
        editTextSearch.visibility = View.GONE

        // RecyclerView pour les hôtes
        val recyclerViewHosts = findViewById<RecyclerView>(R.id.recyclerViewHosts)
        recyclerViewHosts.layoutManager = LinearLayoutManager(this)
        hostsAdapter = ApiHostsAdapter { apiHost ->
            val baseUrl = "http://${apiHost.ip}:5000/"
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            api = retrofit.create(TvPourTousApi::class.java)
            loadChannels(null) // Charger toutes les chaînes au départ

            // Rendre visible le champ de recherche après la sélection d'un hôte
            editTextSearch.visibility = View.VISIBLE
        }
        recyclerViewHosts.adapter = hostsAdapter

        // RecyclerView pour les chaînes
        val recyclerViewChannels = findViewById<RecyclerView>(R.id.recyclerViewChannels)
        recyclerViewChannels.layoutManager = LinearLayoutManager(this)
        channelsAdapter = ChannelsAdapter { channel ->
            changeChannel(channel)
        }
        recyclerViewChannels.adapter = channelsAdapter

        // Lancer le scan réseau lorsque le bouton est cliqué
        buttonScan.setOnClickListener {
            lifecycleScope.launch {
                textViewStatus.text = "Recherche Hôte en cours ..."
                textViewStatus.visibility = TextView.VISIBLE

                val ips = scanForApiHosts()
                val apiHosts = ips.map { ApiHost(it) }
                hostsAdapter.updateHosts(apiHosts)

                textViewStatus.visibility = TextView.GONE
                Toast.makeText(
                    this@MainActivity,
                    "Scan terminé: ${apiHosts.size} hôtes trouvés",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Filtrage lors de la validation (appui sur "Entrée")
        editTextSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = editTextSearch.text.toString()
                Log.i("MainActivity", "Filtrage sur validation: $query")
                loadChannels(query) // Recharge les chaînes avec le filtre
                true
            } else {
                false
            }
        }
    }

    // Charge les chaînes depuis l'API et applique le filtre si nécessaire
    private fun loadChannels(searchQuery: String?) {
        lifecycleScope.launch {
            // Vider la liste et afficher un message de chargement
            withContext(Dispatchers.Main) {
                textViewStatus.text = "Chargement des chaînes..."
                textViewStatus.visibility = View.VISIBLE
                channelsAdapter.submitList(emptyList()) // Vider la liste temporairement
            }

            try {
                val channels = api.getChannels()
                fullChannelsList = channels // Stocker toutes les chaînes

                // Appliquer le filtre
                val filteredList = if (!searchQuery.isNullOrBlank()) {
                    channels.filter { it.name.contains(searchQuery, ignoreCase = true) }
                } else {
                    channels
                }

                Log.i("MainActivity", "Chaînes affichées: ${filteredList.size} - ${filteredList.joinToString { it.name }}")

                // Mise à jour de la liste avec les nouvelles chaînes filtrées
                withContext(Dispatchers.Main) {
                    channelsAdapter.submitList(ArrayList(filteredList)) // Forcer une nouvelle liste
                    textViewStatus.visibility = TextView.GONE // Cacher le message de chargement
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Erreur de chargement : ${e.message}", Toast.LENGTH_LONG).show()
                    textViewStatus.visibility = TextView.GONE // Cacher le message d'erreur
                }
            }
        }
    }

    // Envoie la requête pour changer de chaîne
    private fun changeChannel(channel: Channel) {
        lifecycleScope.launch {
            try {
                val response = api.changeChannel(ChangeChannelRequest(channel.name))
                Toast.makeText(this@MainActivity, response.Message, Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Erreur : ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    // Récupère l'adresse IP locale de l'appareil
    private fun getLocalIpAddress(): String? {
        try {
            val interfaces = NetworkInterface.getNetworkInterfaces()
            for (networkInterface in interfaces) {
                val addresses = networkInterface.inetAddresses
                for (address in addresses) {
                    if (!address.isLoopbackAddress && address is Inet4Address) {
                        return address.hostAddress
                    }
                }
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    // Scanne le sous-réseau pour trouver toutes les IP avec le port 5000 ouvert
    private suspend fun scanForApiHosts(): List<String> = withContext(Dispatchers.IO) {
        val result = mutableListOf<String>()
        val localIp = getLocalIpAddress() ?: return@withContext result
        val subnet = localIp.substringBeforeLast(".")
        for (i in 1..254) {
            val host = "$subnet.$i"
            try {
                Socket().use { socket ->
                    val socketAddress = InetSocketAddress(host, 5000)
                    socket.connect(socketAddress, 200)
                    result.add(host)
                }
            } catch (ex: Exception) {
                // Ignorer si la connexion échoue
            }
        }
        result
    }
}
