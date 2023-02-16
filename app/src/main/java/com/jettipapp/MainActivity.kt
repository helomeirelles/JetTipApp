package com.jettipapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jettipapp.components.InputField
import com.jettipapp.ui.theme.JetTipAppTheme
import com.jettipapp.ui.theme.fonts
import com.jettipapp.utils.calculateTotalPerPerson
import com.jettipapp.utils.calculateTotalTip
import com.jettipapp.widgets.RoundIconButton

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                Column {
                    Spacer(modifier = Modifier.height(10.dp))
                    MainContent()
                }
            }
        }
    }

    @Composable
    fun MyApp(content: @Composable () -> Unit) {
        JetTipAppTheme {
            Surface(
                modifier = Modifier
                    .background(Color.White)
                    .padding(10.dp),
            ) {
                content()
            }
        }
    }

    @Composable
    fun MainContent() {

        val totalPeopleToSplit = remember {
            mutableStateOf(1)
        }

        val tipAmountState = remember {
            mutableStateOf(0.0)
        }

        val totalPerPerson = remember {
            mutableStateOf(0.0)
        }

        BillForm(
            totalPeopleToSplit = totalPeopleToSplit,
            tipAmountState = tipAmountState,
            totalPerPerson = totalPerPerson,
        )

    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MyApp {
            Column {
                Spacer(modifier = Modifier.height(10.dp))
                MainContent()
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BillForm(
    modifier: Modifier = Modifier,
    totalPeopleToSplit: MutableState<Int>,
    tipAmountState: MutableState<Double>,
    totalPerPerson: MutableState<Double>,
    onValChange: (String) -> Unit = {}
) {

    val totalBillState = remember {
        mutableStateOf("")
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    val validState = remember(totalBillState.value) {
        totalBillState.value.trim().isNotEmpty()
    }

    val sliderPositionState = remember {
        mutableStateOf(0f)
    }

    val tipPercentage = (sliderPositionState.value * 100).toInt()

    TopHeader(totalPerPerson.value)

    Spacer(modifier = Modifier.height(15.dp))

    Surface(
        modifier = modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(corner = CornerSize(8.dp)),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    )
    {
        Column(
            modifier = Modifier.padding(6.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            SetInputField(onValChange, keyboardController, validState, totalBillState)
            SetupSplitByRow(totalPeopleToSplit, totalPerPerson, totalBillState, tipPercentage)
            Spacer(modifier = Modifier.height(15.dp))
            SetupTipRow(tipAmountState.value)
            Spacer(modifier = Modifier.height(15.dp))
            SetupSlider(tipPercentage, tipAmountState, totalPerPerson, totalBillState, sliderPositionState, totalPeopleToSplit)
        }

    }
}

@Composable
fun SetupSlider(
    tipPercentage: Int,
    tipAmountState: MutableState<Double>,
    totalPerPerson: MutableState<Double>,
    totalBillState: MutableState<String>,
    sliderPositionState: MutableState<Float>,
    totalPeopleToSplit: MutableState<Int>
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "$tipPercentage%")
        Spacer(modifier = Modifier.height(15.dp))
        Slider(
            value = sliderPositionState.value,
            onValueChange = { newVal ->
                sliderPositionState.value = newVal
                tipAmountState.value =
                    calculateTotalTip(totalBillState.value.toDouble(), tipPercentage)
                totalPerPerson.value = calculateTotalPerPerson(
                    totalBillState.value.toDouble(),
                    totalPeopleToSplit.value,
                    tipPercentage
                )
            },
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),

            )
    }
}

@Composable
fun SetupSplitByRow(
    totalPeopleToSplit: MutableState<Int>,
    totalPerPerson: MutableState<Double>,
    totalBillState: MutableState<String>,
    tipPercentage: Int
) {
    Row(
        modifier = Modifier.padding(10.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = "Dividir por:",
            modifier = Modifier.align(alignment = Alignment.CenterVertically),
            fontFamily = fonts,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.width(130.dp))
        Row(
            modifier = Modifier.padding(horizontal = 3.dp),
            horizontalArrangement = Arrangement.End
        ) {
            RoundIconButton(
                imageVector = Icons.Default.Remove,
                onClick = {
                    if (totalPeopleToSplit.value > 1) totalPeopleToSplit.value--
                    totalPerPerson.value = calculateTotalPerPerson(
                        totalBillState.value.toDouble(),
                        totalPeopleToSplit.value,
                        tipPercentage
                    )
                })
            Text(
                text = "${totalPeopleToSplit.value}",
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 9.dp, end = 9.dp)
            )
            RoundIconButton(
                imageVector = Icons.Default.Add,
                onClick = {
                    totalPeopleToSplit.value++
                    totalPerPerson.value = calculateTotalPerPerson(
                        totalBillState.value.toDouble(),
                        totalPeopleToSplit.value,
                        tipPercentage
                    )
                })
        }
    }
}

@Composable
fun TopHeader(totalPerPerson: Double) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .background(Color.White)
            .clip(shape = CircleShape.copy(all = CornerSize(12.dp))), color = Color(0xFFACB6FA)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val total = "%.2f".format(totalPerPerson)
            Text(
                text = "Total por pessoa",
                fontFamily = fonts,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.h6

            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "R$ $total",
                fontFamily = fonts,
                fontWeight = FontWeight.Black,
                style = MaterialTheme.typography.h4

            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SetInputField(
    onValChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?,
    validState: Boolean,
    totalBillState: MutableState<String>
) {
    InputField(
        valueState = totalBillState,
        labelId = "Valor da conta",
        enabled = true,
        isSingleLine = true,
        onAction = KeyboardActions {
            if (!validState) return@KeyboardActions
            onValChange(totalBillState.value.trim())
            keyboardController?.hide()
        }
    )
}

@Composable
fun SetupTipRow(tipAmount: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp)
            .background(Color.White),
    ) {
        Text(
            text = "Gorjeta:",
            fontFamily = fonts,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(width = 220.dp))
        Text(
            text = "R$ $tipAmount",
            fontFamily = fonts,
            fontStyle = FontStyle.Normal,
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
        )

    }
}