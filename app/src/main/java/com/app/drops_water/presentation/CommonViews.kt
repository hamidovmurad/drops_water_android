package com.app.drops_water.presentation

import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.drops_water.R
import com.app.drops_water.ui.theme.*


@Composable
fun SubTitle(
    subtitle:String,
    modifier: Modifier = Modifier
    ){
    Text(
        text = subtitle,
        fontFamily = FontFamily(Font(R.font.inter_regular)),
        fontSize = 16.sp,
        color = GrayDark,
        modifier = modifier.fillMaxWidth()
    )
}


@Composable
fun TopAppBarApp(
    subtitle:String? = null,
    title:String? = null,
    onBackClick:(()-> Unit)? = null,
    onActionAnalysis:(()->Unit)? = null,
    onActionSettings:(()->Unit)? = null,
){
    Column {

        TopAppBar(title = {
            Column {

                subtitle?.let{
                    SubTitle(it)
                }

                title?.let {
                    Text(
                        text = it,
                        fontFamily = FontFamily(Font(R.font.inter_extra_bold)),
                        fontSize = 24.sp,
                        color = Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

            }

        },
            backgroundColor = Color.Transparent,
            elevation = 0.dp,
            navigationIcon = onBackClick?.let{
                {
                    IconButton(onClick = {
                        it.invoke()

                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back),
                            contentDescription = "Back",
                            modifier = Modifier
                                .size(42.dp)
                                .padding(10.dp)
                        )
                    }
            }},
            actions = {
                onActionAnalysis?.let{
                    IconButton(onClick = {
                        it.invoke()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_chart),
                            contentDescription = "chart",
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(White)
                                .padding(10.dp)
                        )
                    }
                }

                onActionSettings?.let{
                    IconButton(onClick = {
                        it.invoke()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_setting),
                            contentDescription = "settings",
                            modifier = Modifier
                                .size(42.dp)
                                .clip(CircleShape)
                                .background(White)
                                .padding(10.dp)
                        )
                    }
                }

            }
        )
    }

}



@Composable
fun ActionButton(
    title:String,
    color:Color = Blue,
    modifier: Modifier = Modifier,
    onClick:()->Unit
    ){

    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(12),
        colors = ButtonDefaults.textButtonColors(color),
        onClick = {
            onClick()
        }) {
        Text(
            text = title,
            fontFamily = FontFamily(Font(R.font.inter_regular)),
            fontSize = 16.sp,
            color = colorResource(id = R.color.white)
        )
    }
}



@Composable
fun TextFieldApp(
    hint:String,
    modifier: Modifier = Modifier,
    onValueChange:(value:String)->Unit
) {

    var text by rememberSaveable { mutableStateOf("") }


    Column {
        Text(
            text = hint,
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(R.font.inter_regular)),
            color = Black,
            modifier = Modifier.padding(start = 8.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
                onValueChange(it)
            },

            placeholder = {
                Text(
                    text = "Enter $hint",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.inter_regular)),
                    color = Gray
                )
            },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Black,
                disabledTextColor = Gray,
                backgroundColor = BlueLight2,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
        )

    }
}





@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun PreviewItem(){
    Column {
        TextFieldApp("Phone number"){}
        TopAppBarApp(onBackClick = {}, title = "Hello title")
        ActionButton(title = "Hello") {}
    }
}