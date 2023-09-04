@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.tip

import android.icu.text.NumberFormat
import android.os.Bundle
import androidx.compose.runtime.remember
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tip.ui.theme.TipTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipTimeLayout()
                }
            }
        }
    }
}
@Composable
fun TipTimeLayout(){
    var amountInput by remember{mutableStateOf("")}
    var percentageValue by remember{ mutableStateOf("") }
    var roundOffStatus by remember{ mutableStateOf(false) }
    //to doubleOrNull converts a string representation to a double or null when string cant be a dobule
    //elvis operator ?: returns the assigned value when an expression returns null
    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val percentage = percentageValue.toDoubleOrNull() ?:15.0

    val tip = calculateTip(
        amount=amount,
        tipPercent = percentage,
        roundUp=roundOffStatus)
    Column(
        modifier = Modifier
            .padding(40.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(text = stringResource(R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.Start)
        )
        EditNumberField(
            value = amountInput,
            onValueChange = {amountInput = it},
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth())
        EditPercentageField(
            value =percentageValue ,
            onValueChange = {percentageValue = it},
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth())
        RoundoffTip(
            value = roundOffStatus,
            onValueChange = {roundOffStatus = it},
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Text(
            text = stringResource(R.string.tip_amount,tip),

            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))

    }
}
@Composable
fun EditNumberField(value:String,onValueChange:(String)->Unit,modifier: Modifier=Modifier){

    TextField(
        value = value,
        label={Text(stringResource(R.string.bill_amount))},
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange =onValueChange,
        modifier=modifier )

}
@Composable
fun EditPercentageField(value:String,onValueChange:(String)->Unit,modifier: Modifier=Modifier){

    //to doubleOrNull converts a string representation to a double or null when string cant be a dobule
    //elvis operator ?: returns the assigned value when an expression returns null

    TextField(
        value = value,
        label={Text(stringResource(R.string.how_was_the_service))},
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        onValueChange =onValueChange,
        modifier=modifier )

}
@Composable
fun RoundoffTip(value:Boolean,onValueChange: (Boolean) -> Unit,modifier: Modifier=Modifier){
    Row (
        modifier= modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = stringResource(R.string.round_up_tip))
        Switch(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),
            checked = value, onCheckedChange = onValueChange)
    }
}
private fun calculateTip(amount: Double, tipPercent: Double,roundUp:Boolean): String {

    var tip = tipPercent / 100 * amount
    if(roundUp){
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)
}

@Preview(showBackground = true)
@Composable
fun TipTimeLayoutPreview() {
    TipTheme {
        TipTimeLayout()
    }
}