package com.example.artspace

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class Album(val title: String, val artist: String, @DrawableRes val image: Int)

class MainActivity : ComponentActivity() {
    companion object {
        const val FirstAlbumIndex = 0
        internal val Albums = arrayOf(
            Album(title = "노동법", artist = "카툰", image = R.drawable.a),
            Album(title = "직장갑질", artist = "사진", image = R.drawable.b),
            Album(title = "박성웅", artist = "프로필", image = R.drawable.c)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Layout()
                }
            }
        }
    }
}

@Composable
fun Layout(context: Context = LocalContext.current, modifier: Modifier = Modifier) {
    var albumIndex by remember { mutableStateOf(0) }

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        MainActivity.Albums.elementAt(albumIndex).let {
            Image(
                painter = painterResource(id = it.image),
                contentDescription = it.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(300.dp)
                    .clip(RoundedCornerShape(10.dp))
            )
            DescriptionBox(
                title = it.title,
                artist = it.artist,
                modifier = modifier
            )
            ControlButtonContainer(
                labelLeft = if (albumIndex == MainActivity.FirstAlbumIndex) R.string.first else R
                    .string.previous,
                onClickForLeftButton = {
                    when (albumIndex) {
                        0 -> Toast.makeText(context, R.string.first_of_albums, Toast.LENGTH_SHORT).show()
                        else -> albumIndex--
                    }
                },
                labelRight = if (albumIndex == MainActivity.Albums.size - 1) R.string.last else R
                    .string.next,
                onClickForRightButton = {
                    when (albumIndex) {
                        MainActivity.Albums.size - 1 -> Toast.makeText(context, R.string.last_of_albums,
                            Toast
                                .LENGTH_SHORT).show()
                        else -> albumIndex++
                    }
                },
                modifier = Modifier.padding(2.dp)
            )
        }
    }
}

@Composable
fun DescriptionBox(
    title: String = "artwork title",
    artist: String = "artwork artist",
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            modifier = Modifier
                .padding(2.dp)
                .align(Alignment.CenterHorizontally),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = artist,
            modifier = Modifier
                .padding(2.dp)
                .align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun ControlButtonContainer(
    @StringRes labelLeft: Int,
    onClickForLeftButton: () -> Unit,
    @StringRes labelRight: Int,
    onClickForRightButton: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = onClickForLeftButton) {
            Text(text = stringResource(id = labelLeft))
        }
        Button(onClick = onClickForRightButton) {
            Text(text = stringResource(id = labelRight))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ArtSpaceTheme {
        Layout()
    }
}