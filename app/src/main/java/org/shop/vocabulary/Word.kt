package org.shop.vocabulary

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "word")
data class Word(
    val text: String,
    val mean: String,
    val type: String,
    @PrimaryKey(autoGenerate = true) val id: Int = 0
) : Parcelable

/** TODO
 * data class 는 일반 class와는 다르게 일단 상속이 불가능함.
 * toString, hashCode 등이 내부적으로 구현되어 있음.
 * 추가적으로 알아보면 좋음
 */

/** TODO
 *  실제로 사용하는 Entity 테이블. 이 데이터가 그대로 저장될 예정.
 *  테이블로 사용하기 위해서는 Annotation으로 @Entity 을 추가해야함.
 *  tableName은 굳이 선언하지 않더라고 알아서 생성되긴 함.
 *  DB에서 사용할 때에는 Key 값이 필요함.
 *  유니크한 것을 사용하기 위해 Primary key라고 Annotation을 지정
 *  autoGenerate = true: id값을 입력하는 것이 아니라 자동으로 만들어지게끔
 */

/** TODO
 *  Parcelize는 kotlin-kapt할 때 같이 가져오게 됨
 */