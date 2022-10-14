package com.endeavour.task.feature.view.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.endeavour.task.model.Product
import com.endeavour.task.network.Result
import com.endeavour.task.ui.theme.LightNeutral

@Composable
fun Alert(text:String) {
    Text(
        text = text,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(20.dp,10.dp),
        fontSize = 21.sp,
        fontWeight = FontWeight.SemiBold,
        maxLines = 2
    )
}

@Composable
fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .padding(top = 32.dp, bottom = 32.dp)
    ) {
        CircularProgressIndicator(Modifier.align(Alignment.Center))
    }
}

@Composable
fun ShowProductList(products: List<Product>?, onViewLoad: @Composable (Product) -> Unit) {
    if (!products.isNullOrEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .padding(8.dp)
                .background(LightNeutral),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)

        ) {
            items(products) {
                onViewLoad(it)
            }
        }

    } else {
        Alert("No items are available in list.")
    }
}

@Composable
fun HandleResult(result : Result, onSuccess: @Composable (Result.Success<*>) -> Unit) {

    when(result) {
        is Result.Loading -> {
            Loading()
        }

        is Result.Success<*> -> {
            onSuccess(result)
        }

        is Result.Failure -> {
            Alert("No items are available in list.")
        }
    }
}