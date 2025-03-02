package com.antunes.tvpourtous_control

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

data class ApiHost(val ip: String)

class ApiHostsAdapter(private val onClick: (ApiHost) -> Unit) : RecyclerView.Adapter<ApiHostsAdapter.HostViewHolder>() {

    private val hosts = mutableListOf<ApiHost>()

    fun updateHosts(newHosts: List<ApiHost>) {
        hosts.clear()
        hosts.addAll(newHosts)
        notifyDataSetChanged()
    }

    inner class HostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textViewIp)

        init {
            itemView.setOnClickListener {
                onClick(hosts[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_api_host, parent, false)
        return HostViewHolder(view)
    }

    override fun onBindViewHolder(holder: HostViewHolder, position: Int) {
        holder.textView.text = hosts[position].ip
    }

    override fun getItemCount(): Int = hosts.size
}
