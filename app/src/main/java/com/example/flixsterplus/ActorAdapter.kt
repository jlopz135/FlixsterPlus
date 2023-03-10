package com.example.flixsterplus

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions

const val PEOPLE_EXTRA = "PEOPLE_EXTRA"
class PeopleAdapter(private val context: Context, private val actor: List<Actor>) : RecyclerView.Adapter<PeopleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_article,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val people = actor[position]
        holder.bind(people)
    }

    override fun getItemCount() = actor.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener{
        private val ivActor = itemView.findViewById<ImageView>(R.id.ivPeople)
        private val tvName = itemView.findViewById<TextView>(R.id.tvName)

        init{
            itemView.setOnClickListener(this)
        }

        fun bind(actor: Actor){
            tvName.text = actor.name

            Glide.with(context).load(actor.profileImageURL).apply(RequestOptions().centerCrop()).transform(
                RoundedCorners(50)
            ).into(ivActor)
        }

        override fun onClick(p0: View?) {
            // Get selected article
            val actor = actor[absoluteAdapterPosition]
            // Navigate to Details screen and pass selected article
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(PEOPLE_EXTRA, actor)
            context.startActivity(intent)
        }
    }

}