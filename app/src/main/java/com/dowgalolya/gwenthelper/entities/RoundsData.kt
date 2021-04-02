package com.dowgalolya.gwenthelper.entities

class RoundsData {
    var firstRoundFirstPlayerPoints: Int? = null
    var secondRoundFirstPlayerPoints: Int? = null
    var thirdRoundFirstPlayerPoints: Int? = null

    val firstPlayerTotalPoints
        get() = (firstRoundFirstPlayerPoints ?: 0) +
                (secondRoundFirstPlayerPoints ?: 0) +
                (thirdRoundFirstPlayerPoints ?: 0)

    var firstRoundSecondPlayerPoints: Int? = null
    var secondRoundSecondPlayerPoints: Int? = null
    var thirdRoundSecondPlayerPoints: Int? = null

    val secondPlayerTotalPoints
        get() = (firstRoundSecondPlayerPoints ?: 0) +
                (secondRoundSecondPlayerPoints ?: 0) +
                (thirdRoundSecondPlayerPoints ?: 0)
}