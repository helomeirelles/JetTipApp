package com.jettipapp.utils


fun calculateTotalTip(totalBillState: Double, tipPercentage: Int): Double {
    return if (totalBillState > 1 &&
        totalBillState.toString().isNotEmpty()
    ) {
        (totalBillState * tipPercentage) / 100
    } else {
        0.0
    }
}

fun calculateTotalPerPerson(
    totalBill: Double,
    splitBy: Int,
    tipPercentage: Int
): Double {

    val bill = calculateTotalTip(totalBillState = totalBill, tipPercentage = tipPercentage) + totalBill

    return bill / splitBy
}