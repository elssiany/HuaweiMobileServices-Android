package com.dkbrothers.apps.mapkithuawei

import kotlin.math.floor


fun numRandom(desde: Int, hasta: Int): Int {
    return floor(Math.random() * (hasta - desde + 1) + desde).toInt()
}


