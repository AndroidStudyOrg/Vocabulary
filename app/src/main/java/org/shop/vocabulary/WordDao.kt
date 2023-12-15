package org.shop.vocabulary

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WordDao {
    @Query("SELECT * from word ORDER BY id DESC")   // id 값을 기준으로 내림차순. 즉, 최신 추가된 데이터가 가장 상단에 배치
    fun getAll(): List<Word>

    @Query("SELECT * from word ORDER BY id DESC LIMIT 1")   // 끝에 하나만 받겠다. 즉, 최신꺼 하나만 받는다.
    fun getLatestWord(): Word

    @Insert
    fun insert(word: Word)

    @Delete
    fun delete(word: Word)

    @Update
    fun update(word: Word)
}