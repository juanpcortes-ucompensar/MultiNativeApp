package com.example.multinativeapp.activities

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.multinativeapp.R
import com.example.multinativeapp.models.Producto
import com.google.android.material.navigation.NavigationView
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbar: Toolbar
    private lateinit var drawerToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Limpiar SharedPreferences
        val keys = listOf("productos_pref", "categorias_pref", "carrito_pref")
        for (key in keys) {
            val prefs = getSharedPreferences(key, MODE_PRIVATE)
            prefs.edit().clear().apply()
        }

        inicializarProductos()
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        drawerLayout = findViewById(R.id.drawer_layout)
        val navView = findViewById<NavigationView>(R.id.nav_view)

        drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_drawer,
            R.string.close_drawer
        )

        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.inicioFragment,
                R.id.categoriasFragment,
                R.id.carritoFragment,
                R.id.perfilFragment,
                R.id.productosFragment
            ),
            drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener { menuItem ->
            val handled: Boolean

            // Cierra el menú lateral
            drawerLayout.closeDrawers()

            when (menuItem.itemId) {
                R.id.categoriasFragment -> {
                    // Evita múltiples copias en el back stack
                    navController.popBackStack(R.id.categoriasFragment, true)
                    navController.navigate(R.id.categoriasFragment)
                    handled = true
                }

                R.id.inicioFragment -> {
                    navController.popBackStack(R.id.inicioFragment, false)
                    navController.navigate(R.id.inicioFragment)
                    handled = true
                }

                R.id.carritoFragment -> {
                    navController.popBackStack(R.id.carritoFragment, true)
                    navController.navigate(R.id.carritoFragment)
                    handled = true
                }

                R.id.perfilFragment -> {
                    navController.popBackStack(R.id.perfilFragment, true)
                    navController.navigate(R.id.perfilFragment)
                    handled = true
                }

                R.id.productosFragment -> {
                    navController.popBackStack(R.id.productosFragment, true)
                    navController.navigate(R.id.productosFragment)
                    handled = true
                }

                else -> handled = false
            }

            handled
        }

        // Configurar accion del regreso a la pantalla de perfil al ingresar a editar perfil
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.editarPerfilFragment) {
                // Ocultar el botón de hamburguesa
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                toolbar.setNavigationOnClickListener {
                    onBackPressed()
                }
            } else {
                // Mostrar el botón de hamburguesa
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                toolbar.setNavigationOnClickListener {
                    drawerLayout.openDrawer(GravityCompat.START)
                }
                drawerToggle.syncState()
            }
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    private fun inicializarProductos() {
        val sharedPrefs = getSharedPreferences("productos_pref", MODE_PRIVATE)
        sharedPrefs.edit().clear().apply()
        if (!sharedPrefs.contains("productos")) {
            val productos = listOf(
                // Ropa
                Producto("1", "Camisa Casual", "Camisa de algodón manga corta", 19.99, 10, "rop_camisa_casual", "Ropa"),
                Producto("2", "Camiseta Deportiva", "Camiseta de secado rápido", 15.99, 20, "rop_camiseta_deportiva", "Ropa"),
                Producto("3", "Pantalón Jean", "Jean azul clásico", 29.99, 15, "rop_pantalon_jean", "Ropa"),
                Producto("4", "Chaqueta", "Chaqueta impermeable", 49.99, 8, "rop_chaqueta", "Ropa"),
                Producto("5", "Sudadera", "Sudadera térmica", 39.99, 12, "rop_sudadera", "Ropa"),
                Producto("6", "Camisa Formal", "Camisa azul elegante", 24.99, 10, "rop_camisa_formal", "Ropa"),
                Producto("7", "Falda", "Falda de mezclilla", 22.99, 9, "rop_falda", "Ropa"),
                Producto("8", "Vestido", "Vestido casual de verano", 34.99, 7, "rop_vestido", "Ropa"),

                // Electrónica
                Producto("9", "Televisor 50''", "Smart TV 50 pulgadas", 499.99, 5, "ele_tv", "Electrónica"),
                Producto("10", "Laptop", "Laptop 14'' SSD 256GB", 599.99, 6, "ele_laptop", "Electrónica"),
                Producto("11", "Auriculares Bluetooth", "Auriculares con cancelación de ruido", 89.99, 20, "ele_auriculares", "Electrónica"),
                Producto("12", "Tablet", "Tablet 10'' con Android", 199.99, 8, "ele_tablet", "Electrónica"),
                Producto("13", "Mouse Inalámbrico", "Mouse óptico inalámbrico", 14.99, 25, "ele_mouse", "Electrónica"),
                Producto("14", "Smartwatch", "Reloj inteligente con pulsómetro", 79.99, 10, "ele_smartwatch", "Electrónica"),
                Producto("15", "Parlante Bluetooth", "Altavoz portátil con buen sonido", 34.99, 12, "ele_parlante", "Electrónica"),
                Producto("16", "Cámara Web", "Cámara HD para videollamadas", 49.99, 7, "ele_camara_web", "Electrónica"),

                // Hogar
                Producto("17", "Silla", "Silla de escritorio ergonómica", 79.99, 8, "hog_silla", "Hogar"),
                Producto("18", "Escritorio", "Escritorio compacto para laptop", 89.99, 6, "hog_escritorio", "Hogar"),
                Producto("19", "Lámpara", "Lámpara de mesa LED", 29.99, 14, "hog_lampara", "Hogar"),
                Producto("20", "Almohada", "Almohada ortopédica de espuma", 19.99, 20, "hog_almohada", "Hogar"),
                Producto("21", "Cortina", "Cortina blackout 1.40x2.30m", 24.99, 10, "hog_cortina", "Hogar"),
                Producto("22", "Reloj de Pared", "Reloj moderno decorativo", 34.99, 5, "hog_reloj_pared", "Hogar"),
                Producto("23", "Vela Aromática", "Vela con aroma a vainilla", 12.99, 25, "hog_vela", "Hogar"),
                Producto("24", "Organizador", "Caja organizadora plegable", 9.99, 18, "hog_organizador", "Hogar"),

                // Deportes
                Producto("25", "Balón de fútbol", "Balón tamaño 5 profesional", 24.99, 12, "dep_balon", "Deportes"),
                Producto("26", "Raqueta de tenis", "Raqueta de aluminio", 59.99, 6, "dep_raqueta", "Deportes"),
                Producto("27", "Mancuernas", "Par de mancuernas de 5kg", 44.99, 10, "dep_mancuernas", "Deportes"),
                Producto("28", "Mat de Yoga", "Tapete antideslizante", 19.99, 15, "dep_yoga_mat", "Deportes"),
                Producto("29", "Bicicleta Estática", "Bicicleta para ejercicios indoor", 199.99, 3, "dep_bicicleta_estatica", "Deportes"),
                Producto("30", "Guantes de boxeo", "Guantes talla M", 34.99, 8, "dep_guantes_boxeo", "Deportes"),
                Producto("31", "Casco de ciclismo", "Casco ajustable con ventilación", 39.99, 7, "dep_casco_ciclismo", "Deportes"),
                Producto("32", "Botella deportiva", "Botella térmica 1L", 14.99, 22, "dep_botella", "Deportes"),

                // Accesorios
                Producto("33", "Gorra", "Gorra casual ajustable", 14.99, 20, "acc_gorra", "Accesorios"),
                Producto("34", "Cinturón", "Cinturón de cuero negro", 19.99, 12, "acc_cinturon", "Accesorios"),
                Producto("35", "Lentes de sol", "Lentes con protección UV", 24.99, 10, "acc_lentes_sol", "Accesorios"),
                Producto("36", "Reloj de pulsera", "Reloj clásico analógico", 49.99, 5, "acc_reloj_pulsera", "Accesorios"),
                Producto("37", "Cartera", "Cartera de cuero", 29.99, 8, "acc_cartera", "Accesorios"),
                Producto("38", "Bufanda", "Bufanda de lana", 17.99, 15, "acc_bufanda", "Accesorios"),
                Producto("39", "Anillo", "Anillo de acero inoxidable", 9.99, 25, "acc_anillo", "Accesorios"),
                Producto("40", "Pulsera", "Pulsera con cuentas de madera", 12.99, 18, "acc_pulsera", "Accesorios"),

                )

            val gson = Gson()
            val json = gson.toJson(productos)
            sharedPrefs.edit().putString("productos", json).apply()
        }
    }
}