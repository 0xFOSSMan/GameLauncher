package app.fossman.gamelauncher

/*
sealed class BottomNavItem(var title: String, var icon: Int, var screen_route: String) {
    object Home : BottomNavItem("Home", Icons.Outlined.Home.hashCode(), "home")
    object Favorites : BottomNavItem("Favorites", Icons.Outlined.Favorite.hashCode(), "favorites")
    object Settings : BottomNavItem("Settings", Icons.Outlined.Settings.hashCode(), "settings")
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            GameLauncherView()
        }

        composable(BottomNavItem.Favorites.screen_route) {
            FavoriteView()
        }

        composable(BottomNavItem.Settings.screen_route) {
            SettingsView()
        }
    }
}
@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Favorites,
        BottomNavItem.Settings
    )
    BottomNavigation(navController = NavController(LocalContext.current))
    {
        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },
                label = { Text(text = item.title,
                    fontSize = 9.sp) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}*/
