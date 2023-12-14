package org.shop.vocabulary

data class Word(
    val text: String,
    val mean: String,
    val type: String,
)

/** TODO
 * data class 는 일반 class와는 다르게 일단 상속이 불가능함.
 * toString, hashCode 등이 내부적으로 구현되어 있음.
 * 추가적으로 알아보면 좋음
 */