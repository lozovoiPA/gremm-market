package com.implus.gremmarket.ui.account

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import com.implus.gremmarket.LocalNavigation
import com.implus.gremmarket.R


@Composable
fun AccountScreenScaffold(
    modifier: Modifier = Modifier,
    content: @Composable ((PaddingValues, Modifier, LazyListState) -> Unit) = { paddingValues, modifier, listState -> AccountScreenContent(paddingValues, modifier, listState) }
) {
    val account = Account(
        "Vivego",
        "vividegoistical@gmail.com",
        Color(0xFF4285F4),
        profilePicture = painterResource(R.drawable.profile_pic_poppy)
    )

    // Вычисляем когда появляется скролл - меняется топ бар, сразу же при первом скролле.
    val listState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val isScrolled by remember(listState) {
        derivedStateOf {
            listState.firstVisibleItemScrollOffset > 10 || listState.firstVisibleItemIndex > 0
        }
    }

    val navController = LocalNavigation.current

    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = colorResource(R.color.AccountBackground),
        topBar = {
            AccountTopBar(
                account = account,
                scrollBehavior = scrollBehavior,
                scrollState = isScrolled,
                navController = navController
            )
        }
    ) {
        contentPadding ->
        var innerPadding = WindowInsets.safeDrawing.asPaddingValues().calculateEndPadding(LocalLayoutDirection.current)
        innerPadding *= 2

        val modifierInner = modifier
            .padding(
                start = innerPadding,
                end = innerPadding
            )

        content(contentPadding, modifierInner, listState)
    }
}