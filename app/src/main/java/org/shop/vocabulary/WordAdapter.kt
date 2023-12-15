package org.shop.vocabulary

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.shop.vocabulary.databinding.ItemWordBinding

class WordAdapter(
    private val itemclickListener: ItemClickListener? = null,
    val list: MutableList<Word>
) :
    RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    /** TODO
     *  ViewHolder를 생성.
     *  우선 View를 Inflate 해야함.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val inflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ItemWordBinding.inflate(inflater, parent, false)
        return WordViewHolder(binding)
    }

    /** TODO
     *  만든 ViewHolder를 화면과 연결(bind)함.
     */
    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        // 이렇게 하면 우리가 원하는 대로 UI가 연결
        val word = list[position]
        holder.bind(word)
        holder.itemView.setOnClickListener { itemclickListener?.onClick(word) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class WordViewHolder(val binding: ItemWordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(word: Word) {
            binding.apply {
                textTextView.text = word.text
                meanTextView.text = word.mean
                typeChip.text = word.type
            }
        }
    }

    interface ItemClickListener {
        fun onClick(word: Word)
    }
}