package com.antunes.tvpourtous_control

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ChannelsAdapter(private val onClick: (Channel) -> Unit) :
    ListAdapter<Channel, ChannelsAdapter.ChannelViewHolder>(ChannelDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_channel, parent, false)
        return ChannelViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ChannelViewHolder(itemView: View, val onClick: (Channel) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.textViewChannelName)
        private var currentChannel: Channel? = null

        init {
            itemView.setOnClickListener {
                currentChannel?.let { onClick(it) }
            }
        }

        fun bind(channel: Channel) {
            currentChannel = channel
            // Affiche seulement le nom
            textView.text = channel.name
        }
    }
}

class ChannelDiffCallback : DiffUtil.ItemCallback<Channel>() {
    override fun areItemsTheSame(oldItem: Channel, newItem: Channel): Boolean = oldItem.name == newItem.name
    override fun areContentsTheSame(oldItem: Channel, newItem: Channel): Boolean = oldItem == newItem
}
