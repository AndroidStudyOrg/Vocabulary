package org.shop.vocabulary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.shop.vocabulary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), WordAdapter.ItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var wordAdapter: WordAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        initRecyclerView()
        binding.addButton.setOnClickListener {
            Intent(this, AddActivity::class.java).let {
                startActivity(it)
            }
        }
    }

    private fun initRecyclerView() {
        val dummyList = mutableListOf<Word>(
            Word(
                text = "weather",
                mean = "날씨",
                type = "명사"
            ),
            Word(
                text = "honey",
                mean = "꿀",
                type = "명사"
            ),
            Word(
                text = "run",
                mean = "실행하다",
                type = "동사"
            ),
            Word(
                text = "tomato",
                mean = "토마토",
                type = "명사"
            )
        )
        wordAdapter = WordAdapter(this, dummyList)
        binding.wordRecyclerView.apply {
            adapter = wordAdapter
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            val dividerItemDecoration =
                DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }
    }

    override fun onClick(word: Word) {
        Toast.makeText(this, "${word.text}가 클릭되었습니다", Toast.LENGTH_SHORT).show()
    }
}