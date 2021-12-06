package com.iobytex

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.iobytex.domain.Result
import com.iobytex.extensions.hasLocationPermission
import com.iobytex.extensions.locationFlow
import com.iobytex.task.R
import com.iobytex.task.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>
    @Inject
    internal lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val mainViewModel: MainViewModel by viewModels()
    @Inject
    internal lateinit var navController: NavController
    @Inject
    internal lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.topAppBar.setupWithNavController(navController, appBarConfiguration)


        binding.topAppBar.setNavigationOnClickListener {
            it.findNavController().navigate(R.id.categoryFragment)
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                locationPermissionRequest = registerForActivityResult(
                    ActivityResultContracts.RequestMultiplePermissions()
                ) { permissions ->
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        when {
                            permissions.getOrDefault(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                false
                            ) -> {
                                // Precise location access granted.
                                Timber.d("Location Permission has been granted")
                            }
                            permissions.getOrDefault(
                                Manifest.permission.ACCESS_COARSE_LOCATION,
                                false
                            ) -> {
                                // Only approximate location access granted.
                                Timber.d("Location Permission has been granted")
                            }
                            else -> {
                                Timber.d("Location Permission has been denied")
                                // No location access granted.

                                // Explain to the user that the feature is unavailable because the
                                // features requires a permission that the user has denied. At the
                                // same time, respect the user's decision. Don't link to system
                                // settings in an effort to convince the user to change their
                                // decision.
                            }
                        }
                    } else {
                        when {
                            permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true -> {
                                Timber.d("Location Permission has been granted")
                            }
                            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true -> {
                                Timber.d("Location Permission has been granted")
                            }
                            else -> {
                                Timber.d("Location Permission has been denied")
                            }
                        }
                    }
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    when {
                        hasLocationPermission() -> {
                            Timber.d("Location Permission has been granted")
                            launch {
                                //TODO: collect weather flow
                                fusedLocationProviderClient.locationFlow().collect { value ->
                                    mainViewModel.getGeoLocation(
                                        value.latitude,
                                        value.longitude
                                    )

                                    mainViewModel.weatherType.collect {
                                        when (it) {
                                            is Result.Success -> {
                                                binding.topAppBar.subtitle = getString(
                                                    R.string.weather,
                                                    it.value.icon,
                                                    it.value.main
                                                )
                                            }
                                            else -> {
                                                binding.topAppBar.subtitle = ""
                                            }
                                        }
                                    }

                                }
                            }
                        }
                        shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                                || shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                            Timber.d("show request permission rationale")
                        }
                        else -> {
                            locationPermissionRequest.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        }
                    }
                } else {
                    when {
                        hasLocationPermission() -> {
                            Timber.d("Location Permission has been granted")
                            launch {
                                //TODO: collect weather flow
                                fusedLocationProviderClient.locationFlow().collect { value ->
                                    mainViewModel.getGeoLocation(
                                        value.latitude,
                                        value.longitude
                                    )

                                }

                                mainViewModel.weatherType.collect {
                                    when (it) {
                                        is Result.Success -> {
                                            binding.topAppBar.subtitle = getString(
                                                R.string.weather,
                                                it.value.icon,
                                                it.value.main
                                            )
                                        }
                                        else -> {
                                            binding.topAppBar.subtitle = ""
                                        }
                                    }
                                }

                            }
                        }
                        else -> {
                            locationPermissionRequest.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        }
                    }
                }
            }

        }


    }

}