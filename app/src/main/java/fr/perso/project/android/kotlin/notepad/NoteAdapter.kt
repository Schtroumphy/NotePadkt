package fr.perso.project.android.kotlin.notepad

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import fr.perso.project.android.kotlin.notepad.model.Note

/**
 * Created on 29/04/2020 - 22:32.
 * TODO: Add a class header comment!
 *
 * @author : JEAN-LOUIS Thessalène
 */
class NoteAdapter(val noteList : List<Note>, val itemClickListener : View.OnClickListener) :
    RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val cardView = itemView.findViewById<CardView>(R.id.cardView)
        val tvTitle = itemView.findViewById<TextView>(R.id.tv_title)
        val excerptView = itemView.findViewById<TextView>(R.id.tx_excerpt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewItem = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_note, parent, false)
        return ViewHolder(viewItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = noteList[position]

        holder.cardView.setOnClickListener(itemClickListener)
        holder.cardView.tag=position
        holder.tvTitle.text=item.title
        holder.excerptView.text=item.content
    }

    override fun getItemCount(): Int {
        return noteList.size
    }


}