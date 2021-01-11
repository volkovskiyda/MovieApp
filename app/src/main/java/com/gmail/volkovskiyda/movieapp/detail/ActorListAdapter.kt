package com.gmail.volkovskiyda.movieapp.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gmail.volkovskiyda.movieapp.R
import com.gmail.volkovskiyda.movieapp.model.Actor

class ActorListAdapter : ListAdapter<Actor, ActorListAdapter.ActorViewHolder>(ActorDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ActorViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_actor, parent, false)
    )

    override fun onBindViewHolder(holder: ActorViewHolder, position: Int) {
        val actor = getItem(position)
        with(holder) {
            image.load(actor.image)
            name.text = actor.name
        }
    }

    class ActorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.actor_image)
        val name: TextView = itemView.findViewById(R.id.actor_name)
    }

    object ActorDiffUtil : DiffUtil.ItemCallback<Actor>() {
        override fun areItemsTheSame(oldItem: Actor, newItem: Actor): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Actor, newItem: Actor): Boolean =
            oldItem == newItem
    }
}