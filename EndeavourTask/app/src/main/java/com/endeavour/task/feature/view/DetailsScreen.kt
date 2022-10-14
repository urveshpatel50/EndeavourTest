package com.endeavour.task.feature.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.endeavour.task.R
import com.endeavour.task.feature.viewmodel.FavouritesViewModel
import com.endeavour.task.model.Product
import com.endeavour.task.ui.theme.AddToCartColor
import com.endeavour.task.ui.theme.LightNeutral
import com.endeavour.task.ui.theme.MediumPrimary
import kotlinx.coroutines.launch

@Composable
fun DetailsScreen(product: Product?) {

    val viewModel = hiltViewModel<FavouritesViewModel>()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .align(Alignment.End)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .background(LightNeutral),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = rememberAsyncImagePainter(
                                product?.imageURL,
                                error = painterResource(R.drawable.no_image)
                            ),
                            modifier = Modifier
                                .width(250.dp)
                                .height(200.dp),
                            contentDescription = null,
                            contentScale = ContentScale.FillHeight,
                        )
                    }

                    var isFavourite by remember { mutableStateOf(product?.isFavourite ?: false) }

                    FavouriteView(
                        isFavourite = isFavourite,
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.BottomEnd)
                    ) {
                        product?.let {
                            if (isFavourite) {
                                viewModel.deleteFavourites(it) { e ->
                                    scope.launch {
                                        snackbarHostState.showSnackbar(e.message.toString())
                                    }
                                }
                            } else {
                                viewModel.insertFavourites(it) { e ->
                                    scope.launch {
                                        snackbarHostState.showSnackbar(e.message.toString())
                                    }
                                }
                            }

                            isFavourite = !isFavourite
                            product.isFavourite = isFavourite
                        }
                    }
                }
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(10.dp, 5.dp),
                text = product?.title.toString(),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 2
            )

            val price = product?.price?.get(0)
            if (price != null) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(0.dp, 5.dp),
                    text = String.format("$%.2f %s",price.value, price.message),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2
                )
            }

            val ratingCount = product?.ratingCount
            if (ratingCount != null) {
                RatingBar(
                    rating = ratingCount,
                    modifier = Modifier.height(25.dp)
                )
            }

            if (product?.isAddToCartEnable == true) {
                Button(
                    onClick = {
                        //Add to cart
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = AddToCartColor),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = stringResource(R.string.add_to_cart),
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun FavouriteView(isFavourite: Boolean, modifier: Modifier, onClick: () -> Unit) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            if (isFavourite)
                Icons.Filled.Favorite
            else
                Icons.Filled.FavoriteBorder,
            tint = MediumPrimary,
            modifier = Modifier.size(50.dp),
            contentDescription = null
        )
    }
}