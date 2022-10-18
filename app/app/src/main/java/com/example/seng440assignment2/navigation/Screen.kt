package com.example.seng440assignment2.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.seng440assignment2.R

sealed class Screen(val route: String, val icon: ImageVector, @StringRes val screenName: Int) {
    object Review : Screen("reviews", Icons.Outlined.RateReview, R.string.navbar_review_label)
    object Search : Screen("search", Icons.Outlined.SportsBar, R.string.navbar_category_label)
    object Scan : Screen("scan", Icons.Outlined.QrCodeScanner, R.string.navbar_scan_label)
    object Profile : Screen("profile", Icons.Outlined.AccountCircle, R.string.navbar_profile_label)
}