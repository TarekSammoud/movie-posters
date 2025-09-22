package fr.eseo.ld.ts.movieposters.ui.theme


import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.Dispatchers
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import fr.eseo.ld.ts.movieposters.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import coil3.BitmapImage
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.request.ErrorResult
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.error
import coil3.request.placeholder
import fr.eseo.ld.ts.movieposters.models.Movie
import fr.eseo.ld.ts.movieposters.network.OmdbApiServiceImpl
import fr.eseo.ld.ts.movieposters.repositories.OmdbRepository
import fr.eseo.ld.ts.movieposters.ui.theme.MoviePostersTheme
import fr.eseo.ld.ts.movieposters.ui.viewmodels.MovieViewModel
import fr.eseo.ld.ts.movieposters.ui.viewmodels.MovieViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream

@Composable
fun MovieSearchScreen() {

    val viewModel : MovieViewModel = viewModel(
        factory = MovieViewModelFactory(
            OmdbRepository(OmdbApiServiceImpl.api)
        )
    )

    val movie by viewModel.movie

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                val layoutDirection = LocalLayoutDirection.current
                val intentContext: Context = LocalContext.current

                MovieComposeAppBar(
                    movie = movie,
                    onShareClicked = { movie ->
                        if (movie != null) {
                            Log.d("SHARE_CLICK", "Share clicked with movie: ${movie.title}")
                            shareInformation(movie = movie, intentContext = intentContext)
                        } else {
                            Log.d("SHARE_CLICK", "Share clicked but movie is null")
                            Toast.makeText(intentContext, "No movie to share", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = WindowInsets.safeDrawing
                                .asPaddingValues()
                                .calculateStartPadding(layoutDirection),
                            end = WindowInsets.safeDrawing
                                .asPaddingValues()
                                .calculateEndPadding(layoutDirection)
                        )
                        .background(MaterialTheme.colorScheme.primary)
                )
            },
            content = { paddingValues ->
                MoviePosterScreenContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .imePadding(),
                    movieViewModel = viewModel
                )
            }
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)

@Composable
fun MovieComposePreview(){
    MoviePostersTheme {
        MovieSearchScreen()
    }
}



@Composable
private fun MoviePosterScreenContent(
    modifier: Modifier,
    movieViewModel: MovieViewModel
) {
    var filmTitle by remember { mutableStateOf("") }
    val movie by movieViewModel.movie

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Poster area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // make image big
        ) {
            if (movie != null) {
                Log.d("FILM", "not_null " + movie?.poster)
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(movie?.poster)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .listener(
                            onSuccess = { _, _ -> Log.d("COIL", "Successfully loaded ${movie?.poster}") },
                            onError = { _, error ->
                                Log.e("COIL", "Failed to load ${movie?.poster}", error.throwable)
                            }
                        )
                        .build(),
                    contentDescription = "Movie Poster",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )

            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
            }
        }

        movie?.let {
            Row {
                Text(it.director)
                Spacer(modifier = Modifier.width(8.dp))
                Text(it.released)

            }
            Text(it.genre)

        }
        Spacer(modifier = Modifier.height(16.dp))

        // Bottom row with search bar + button
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = filmTitle,
                onValueChange = { filmTitle = it },
                label = { Text("Search for movie") },
                placeholder = { Text("Search for movie") },
                singleLine = true,
                modifier = Modifier
                    .width(250.dp)
                    .height(56.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                onClick = { movieViewModel.fetchMovie(filmTitle) }
            ) {
                Text(text = stringResource(id = R.string.search))
            }
        }
    }


}


@Composable
private fun MovieComposeAppBar(
    movie: Movie?,
    onShareClicked: (Movie?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.app_name),
            modifier = Modifier.padding(start = 16.dp),
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.titleLarge
        )
        IconButton(
            onClick = { onShareClicked(movie) },
            modifier = Modifier.padding(end = 16.dp),
            enabled = movie != null // Disable button if no movie is loaded
        ) {
            Icon(
                imageVector = Icons.Filled.Share,
                contentDescription = stringResource(id = R.string.share),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}






private fun shareInformation(movie: Movie, intentContext: Context) {
    val shareBody = "Check out this movie: ${movie.title}\nShared from Movie Posters"
    val shareSubject = movie.title
    Log.d("SHARE", "Starting share for movie: ${movie.title}, Poster: ${movie.poster}")

    if (movie.poster.isNullOrBlank()) {
        Log.w("SHARE", "Poster URL is null or blank, falling back to text-only sharing")
        CoroutineScope(Dispatchers.Main).launch {
            Toast.makeText(intentContext, "No poster image available to share", Toast.LENGTH_LONG).show()
            val sendIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, shareSubject)
                putExtra(Intent.EXTRA_TEXT, shareBody)
            }
            val shareIntent = Intent.createChooser(sendIntent, "Share movie details")
            try {
                if (intentContext !is Activity) {
                    shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                intentContext.startActivity(shareIntent)
                Log.d("SHARE", "Text-only share intent launched")
            } catch (e: Exception) {
                Log.e("SHARE", "Failed to launch text-only share intent: ${e.message}", e)
                Toast.makeText(intentContext, "Failed to share: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
        return
    }

    CoroutineScope(Dispatchers.IO).launch {
        try {
            Log.d("SHARE", "Downloading image from: ${movie.poster}")
            val imageLoader = ImageLoader.Builder(intentContext)
                .build()
            val request = ImageRequest.Builder(intentContext)
                .data(movie.poster)
                .build()

            val result = imageLoader.execute(request)
            when (result) {
                is SuccessResult -> {
                    val image = result.image
                    if (image is BitmapImage) {
                        val bitmap = image.bitmap // Extract Bitmap from BitmapImage
                        Log.d("SHARE", "Image downloaded, size: ${bitmap.byteCount} bytes, width: ${bitmap.width}, height: ${bitmap.height}")

                        // Save the bitmap to a temporary file
                        val cacheDir = intentContext.cacheDir
                        val imageFile = File(cacheDir, "${movie.title.replace(" ", "_")}_poster.jpg")
                        FileOutputStream(imageFile).use { out ->
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
                        }
                        Log.d("SHARE", "Image saved to: ${imageFile.absolutePath}, exists: ${imageFile.exists()}, size: ${imageFile.length()} bytes")

                        // Get a content URI using FileProvider
                        val imageUri = FileProvider.getUriForFile(
                            intentContext,
                            "${intentContext.packageName}.fileprovider",
                            imageFile
                        )
                        Log.d("SHARE", "FileProvider URI: $imageUri")

                        // Create the share intent
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            type = "image/jpeg"
                            putExtra(Intent.EXTRA_SUBJECT, shareSubject)
                            putExtra(Intent.EXTRA_TEXT, shareBody)
                            putExtra(Intent.EXTRA_STREAM, imageUri)
                            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        }

                        val shareIntent = Intent.createChooser(sendIntent, "Share movie poster")
                        CoroutineScope(Dispatchers.Main).launch {
                            try {
                                if (intentContext !is Activity) {
                                    shareIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                }
                                intentContext.startActivity(shareIntent)
                                Log.d("SHARE", "Share intent launched with image")
                            } catch (e: Exception) {
                                Log.e("SHARE", "Failed to launch share intent: ${e.message}", e)
                                Toast.makeText(intentContext, "Failed to share: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        Log.e("SHARE", "Image is not a BitmapImage, type: ${image.javaClass.name}")
                        CoroutineScope(Dispatchers.Main).launch {
                            Toast.makeText(intentContext, "Failed to process poster image", Toast.LENGTH_LONG).show()
                        }
                    }
                }
                is ErrorResult -> {
                    Log.e("SHARE", "Image download failed: ${result.throwable.message}", result.throwable)
                    CoroutineScope(Dispatchers.Main).launch {
                        Toast.makeText(intentContext, "Failed to load poster image: ${result.throwable.message}", Toast.LENGTH_LONG).show()
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("SHARE", "Error in shareInformation: ${e.message}", e)
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(intentContext, "Failed to share: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}