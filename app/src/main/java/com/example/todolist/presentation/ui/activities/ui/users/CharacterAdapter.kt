package com.example.todolist.presentation.ui.activities.ui.users

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.entities.Character
import com.example.todolist.databinding.CharacterCellBinding

class CharacterAdapter(
    private val characterList: MutableList<Character>
): RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding = CharacterCellBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_cell, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val character = characterList[position]
        holder.binding.tvName.text = character.name
        holder.binding.tvSpecies.text = character.species
        holder.binding.tvStatus.text = character.status
    }

    fun clearData() {
        characterList.clear()
        notifyDataSetChanged()
    }

    fun add(characters: List<Character>) {
        characterList.addAll(characters)
        notifyDataSetChanged()
    }
}