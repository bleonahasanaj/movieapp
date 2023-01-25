package com.frakton.moviesapp.ui.activities

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.frakton.moviesapp.databinding.ActivityMovieDetailsBinding
import com.frakton.moviesapp.domain.models.MovieDetailsModel
import com.frakton.moviesapp.ui.adapters.MovieGenresRecyclerAdapter
import com.frakton.moviesapp.ui.viewmodels.MovieDetailsViewModel
import com.frakton.moviesapp.util.Constants
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.getMovieDetails(intent.getLongExtra(Constants.MOVIE_ID_EXTRA, -1))
        setViewModelObservers()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.closeButton.setOnClickListener { finish() }
    }

    private fun setViewModelObservers() {
        viewModel.movieDetails.observe(this) { movieDetailsModel ->
            showMovieDetails(movieDetailsModel)
            viewModel.getMovieTrailerVideos(movieDetailsModel.id)
        }
        viewModel.movieTrailerVideos.observe(this) { movieTrailerVideosModel ->
            //TODO: show videos in UI
        }
    }

    private fun showMovieDetails(movieDetailsModel: MovieDetailsModel) {
        binding.movieTitle.text = "${movieDetailsModel.title}\n(${movieDetailsModel.releaseYear})"
        binding.movieDescription.text = movieDetailsModel.description
        setMoviePosterImage(movieDetailsModel.posterPath, binding.moviePosterImage)
        setMovieGenres(movieDetailsModel.genres)
        binding.ratingNumber.text = movieDetailsModel.rating.toString()
        binding.movieRating.rating = movieDetailsModel.rating / 2F
        binding.productionText.text = movieDetailsModel.productionCompany
        binding.budgetText.text = movieDetailsModel.budget
        binding.revenueText.text = movieDetailsModel.revenue
        binding.releaseDateText.text = movieDetailsModel.releaseDate
    }

    private fun setMovieGenres(genres: List<String>) {
        val genresAdapter = MovieGenresRecyclerAdapter()
        binding.genresRecycleView.adapter = genresAdapter
        binding.genresRecycleView.layoutManager = GridLayoutManager(this, 4)
        genresAdapter.setData(genres)
    }

    private fun setMoviePosterImage(moviePosterPath: String, moviePosterImage: ImageView) {
        Picasso.get().load(moviePosterPath).into(moviePosterImage)
    }
}