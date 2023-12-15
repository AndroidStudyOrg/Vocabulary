package org.shop.vocabulary

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.shop.vocabulary.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), WordAdapter.ItemClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var wordAdapter: WordAdapter
    private var selectedWord: Word? = null
    private val updateAddWordResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val isUpdated = result.data?.getBooleanExtra("isUpdated", false)
            if (result.resultCode == RESULT_OK && isUpdated == true) {
                updateAddWord()
            }

        }

    private val updateEditWordResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val editWord = result.data?.getParcelableExtra<Word>("editWord")
            if (result.resultCode == RESULT_OK && editWord != null) {
                updateEditWord(editWord)
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        initRecyclerView()
        binding.addButton.setOnClickListener {
//            updateAddWordResult.launch(Intent(this, AddActivity::class.java))
            Intent(this, AddActivity::class.java).let {
                updateAddWordResult.launch(it)
            }
        }

        binding.deleteImageView.setOnClickListener {
            delete()
        }
        binding.editImageView.setOnClickListener {
            edit()
        }
    }

    private fun initRecyclerView() {
        wordAdapter = WordAdapter(this, mutableListOf())
        binding.wordRecyclerView.apply {
            adapter = wordAdapter
            layoutManager =
                LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
            val dividerItemDecoration =
                DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }

        Thread {
            val list = AppDatabase.getInstance(this)?.wordDao()?.getAll() ?: emptyList()
            Thread.sleep(1000)
            wordAdapter.list.addAll(list)
            runOnUiThread {
                wordAdapter.notifyDataSetChanged()
            }
        }.start()
        /** TODO
         *  데이터가 양이 많아 가져오는데 시간이 걸린다면 Adapter가 초기화 된 후에는 적용이 안될 수도 있음
         *  어댑터에게 아이템이 추가되었다고 알려주어야함. 이는 UI Thread에서 진행해야함.
         *  notifyDataSetChanged는 부하가 많이 걸린다. List가 길어질 수록 그 전체를 봐야하기 때문에
         */
    }

    private fun updateAddWord() {
        Thread {
            AppDatabase.getInstance(this)?.wordDao()?.getLatestWord()?.let { word ->
                wordAdapter.list.add(0, word)
                runOnUiThread {
                    wordAdapter.notifyDataSetChanged()
                }
            }
        }.start()
    }

    private fun updateEditWord(word: Word) {
        val index = wordAdapter.list.indexOfFirst { it.id == word.id }
        wordAdapter.list[index] = word
        runOnUiThread {
            selectedWord = word
            wordAdapter.notifyItemChanged(index)
            binding.tvText.text = word.text
            binding.tvMean.text = word.mean
        }
    }

    private fun delete() {
        if (selectedWord == null) return
        Thread {
            selectedWord?.let { word ->
                AppDatabase.getInstance(this)?.wordDao()?.delete(word)
                runOnUiThread {
                    wordAdapter.list.remove(word)
                    wordAdapter.notifyDataSetChanged()
                    binding.tvText.text = ""
                    binding.tvMean.text = ""
                    Toast.makeText(this, "삭제가 완료되었습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    private fun edit() {
        if (selectedWord == null) return
        val intent = Intent(this, AddActivity::class.java).putExtra("originWord", selectedWord)
        updateEditWordResult.launch(intent)
    }

    override fun onClick(word: Word) {
        selectedWord = word
        binding.tvText.text = word.text
        binding.tvMean.text = word.mean
    }
}