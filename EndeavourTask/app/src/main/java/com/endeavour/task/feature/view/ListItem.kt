package com.endeavour.task.feature.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
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
import coil.compose.rememberAsyncImagePainter
import com.endeavour.task.R
import com.endeavour.task.model.Product
import com.endeavour.task.ui.theme.AddToCartColor
import com.endeavour.task.ui.theme.MediumPrimary

@Composable
fun ListItem(
    product: Product,
    onClick: () -> Unit,
    onFavouriteClick: () -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .height(350.dp)
            .background(MaterialTheme.colors.background)
            .clickable {
                onClick()
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.End
        ) {

            val ratingCount = product.ratingCount
            if (ratingCount != null) {
                RatingBar(
                    rating = ratingCount,
                    modifier = Modifier.height(15.dp)
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)
                        .padding(0.dp, 0.dp),
                    text = product.title.toString(),
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2
                )

                val price = product.price?.get(0)
                if (price != null) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .padding(0.dp, 5.dp),
                        text = String.format("$%.2f %s",price.value, price.message),
                        textAlign = TextAlign.Center,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 2
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.End),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = rememberAsyncImagePainter(
                            product.imageURL,
                            error = painterResource(R.drawable.no_image)
                        ),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                    )

                    IconButton(
                        modifier = Modifier.size(24.dp),
                        onClick = {
                            onFavouriteClick()
                        }
                    ) {
                        Icon(
                            if (product.isFavourite)
                                Icons.Filled.Favorite
                            else
                                Icons.Filled.FavoriteBorder,
                            tint = MediumPrimary,
                            contentDescription = null
                        )
                    }
                }

                if (product.isAddToCartEnable == true) {
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
}