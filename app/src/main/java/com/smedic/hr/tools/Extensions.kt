package com.smedic.hr.tools

import java.text.DecimalFormat

/**
 * @author Stevan Medic
 *
 * Created on Nov 2020
 */

fun Float.formattedRating(): String {
    val df = DecimalFormat("0.0")
    return "${df.format(this)}/10"
}