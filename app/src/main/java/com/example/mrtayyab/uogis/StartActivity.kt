package com.example.mrtayyab.uogis

import android.Manifest
import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.URLUtil
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.example.mrtayyab.uogis.R.id.info
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {


    lateinit var  mWebView : WebView
    lateinit var mProgressBar : ProgressBar
    val MY_PERMISSION_REQUEST_CODE = 123
     lateinit var listItems : Array<String>
    lateinit var categoryList : Array<String>


    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        listItems = resources.getStringArray(R.array.shopping_item)
        mWebView =findViewById(R.id.myWebView)
        mProgressBar = findViewById(R.id.progressBar)
        mProgressBar.max = 100

        checkPermission()
        val myUrl = "https://www.puretoons.net"

        checkConnectivity()


        optionLinkOpen(myUrl)

        mWebView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->

            if(mWebView.url.contains(".3gp") || mWebView.url.contains(".mp4")){
//                val i = Intent(Intent.ACTION_VIEW)
//                i.data = Uri.parse(url)
//                startActivity(i)
//                var  videoPath : String = mWebView.url
//                var videoPath = URLUtil.guessUrl()

                val videoPath = URLUtil.guessFileName(url, contentDisposition, mimetype)


                val mBuilder = AlertDialog.Builder(this)
                mBuilder.setTitle("Choose an item")
                mBuilder.setSingleChoiceItems(listItems, -1, DialogInterface.OnClickListener { dialogInterface, i ->
                    // mResult.setText(listItems[i])

                    if (listItems[i] == "online") {
                        Toast.makeText(this, "Your Choice is Online", Toast.LENGTH_SHORT).show()



                        val i = Intent(Intent.ACTION_VIEW)
                        i.data = Uri.parse(url)
                        startActivity(i)


//                        val intent = Intent(applicationContext, VideoActivity::class.java)
//                        intent.putExtra("videoPath", videoPath)
//                        startActivity(intent)



                    } else if (listItems[i] == "download") {
                        Toast.makeText(this, "Your Choice is download", Toast.LENGTH_SHORT).show()


                        val fileName = URLUtil.guessFileName(url, contentDisposition, mimetype) //returns a string of the name of the file THE IMPORTANT PART

                        val myRequest = DownloadManager.Request(Uri.parse(url))
                        myRequest.allowScanningByMediaScanner()

                        myRequest.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        myRequest.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileName)
                        val myManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

                        myManager.enqueue(myRequest)
                        myDownloadingAlert(fileName)
                        Toast.makeText(this, "Your file is Downloading... ${fileName}", Toast.LENGTH_SHORT).show()

                    }
                    dialogInterface.dismiss()
                })
                val mDialog = mBuilder.create()
                mDialog.show()

            }
        }

    }


//    fun isInternetConnection(mContext: Context): Boolean {
//        val connectivityManager = mContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        return if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).state == NetworkInfo.State.CONNECTED) {
//            //we are connected to a network
//            true
//        } else {
//            false
//        }
//    }

    private fun checkConnectivity(): Int {
        var enabled = true

        var internet =2
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivityManager.activeNetworkInfo

        if (info == null || !info.isConnected || !info.isAvailable) {
             internet = 0//sin conexion
            Toast.makeText(applicationContext, "Internet connection is not available...", Toast.LENGTH_SHORT).show()

            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.app_name)
            builder.setIcon(R.mipmap.ic_launcher)
            builder.setMessage("Internet connection is not available " )
                    .setCancelable(false)
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id -> finish() })
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
            val alert = builder.create()
            alert.show()

            enabled = false
        } else {
             internet = 1//conexión
            Toast.makeText(applicationContext, "Internet is available...", Toast.LENGTH_SHORT).show()

        }

        return internet
    }

    fun linkPassing(){

        val CartoonSeriesHindiDubbed = " https://www.puretoons.net/p/hindi-cartoon-series.html"

        val HindiCartoonMovies = "https://www.puretoons.net/p/hindi-cartoon-movies.html"
        val AnimeSeries = "https://www.pureanimes.com/"
        val FanHindiDubbed = "https://www.puretoons.net/p/fan-hindi-dubbed.html"
       val AnimeCartoonSongs ="https://www.puretoons.net/p/animecartoon-songs.html"
      val AnimeCartoonWallpapers = "https://www.puretoons.net/p/animecartoon-wallpapers.html"




//        val  CartoonSeriesBanglaDubbed = "http://puretoons.com/site_4.xhtml"
//        val CartoonSeriesEnglishDubbed = "http://puretoons.com/site_347.xhtml"
//        val AnimeSeriesHindiSubbed  = "http://puretoons.com/site_anime_series_hindi_subbed.xhtml"
//        val  CartoonAnimatedMovies = "http://puretoons.com/site_cartoon_animated_movies.xhtml"
//        val  AnimeCartoonSpecialEpisode = "http://puretoons.com/site_cartoon_and_anime_special_episodes.xhtml"
//
//        val  FanHindiDubbedVideos = "http://puretoons.com/site_fan_hindi_dubbed.xhtml"
//        val  AnimeCartoonSongs  = "http://puretoons.com/site_anime_cartoon_songs.xhtml"

        categoryList = resources.getStringArray(R.array.cartoonsCatergory)

       val mBuilder = AlertDialog.Builder(this)
        mBuilder.setTitle("Choose a Category")
        mBuilder.setSingleChoiceItems(categoryList, -1, DialogInterface.OnClickListener { dialogInterface, i ->
            // mResult.setText(listItems[i])

            if (categoryList[i] == "Cartoon Series Hindi Dubbed") {
//                Toast.makeText(applicationContext , "you select ${myHindi}" , Toast.LENGTH_SHORT).show()
                optionLinkOpen(CartoonSeriesHindiDubbed)

            } else if (categoryList[i] == "Hindi Cartoon Movies") {

//                Toast.makeText(applicationContext , "you select ${myAnime}" , Toast.LENGTH_SHORT).show()
                optionLinkOpen(HindiCartoonMovies)
            }
            else if (categoryList[i] == "Anime Series") {

//                Toast.makeText(applicationContext , "you select ${myAnimeMovie}" , Toast.LENGTH_SHORT).show()
                optionLinkOpen(AnimeSeries)
            }
            else if (categoryList[i] == "Fan Hindi Dubbed") {

//                Toast.makeText(applicationContext , "you select ${mySpecialAnime}" , Toast.LENGTH_SHORT).show()
                optionLinkOpen(FanHindiDubbed)
            }

            else if (categoryList[i] == "Anime Cartoon Wallpapers") {

//                Toast.makeText(applicationContext , "you select ${mySuperDragon}" , Toast.LENGTH_SHORT).show()
                optionLinkOpen(AnimeCartoonWallpapers)
            }

//            else if(categoryList[i] == "Anime Cartoon Special Episode"){
//                optionLinkOpen(AnimeCartoonSpecialEpisode)
//            }
//            else if(categoryList[i] == "Fan Hindi Dubbed Videos"){
//                optionLinkOpen(FanHindiDubbedVideos)
//            }
//            else if(categoryList[i] == "Anime Cartoon Songs"){
//                optionLinkOpen(AnimeCartoonSongs)
//            }

            dialogInterface.dismiss()
        })
        val mDialog = mBuilder.create()
        mDialog.show()
    }





    @SuppressLint("SetJavaScriptEnabled")

    fun optionLinkOpen(my : String){
        mWebView.loadUrl(my)

        mWebView.settings.javaScriptEnabled = true
        mWebView.webViewClient = WebViewClient()
        this.mProgressBar.progress = 0

        mWebView.webChromeClient = object : WebChromeClient() {

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                mProgressBar.progress = newProgress
            }

            override fun onReceivedTitle(view: WebView, title: String) {
                super.onReceivedTitle(view, title)
                supportActionBar!!.title = title
            }

            override fun onReceivedIcon(view: WebView, icon: Bitmap) {
                super.onReceivedIcon(view, icon)
            }
        }
    }

    override fun onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    fun myDownloadingAlert(filename : String ){
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.app_name)
        builder.setIcon(R.mipmap.ic_launcher)
        builder.setMessage("Your Downloading is start ${filename}" )
                .setCancelable(false)
                //.setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, id -> finish() })
                .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id -> dialog.cancel() })
        val alert = builder.create()
        alert.show()

    }

    fun alertAbout(){

        val stMyWeb = SpannableString("http://www.youtube.com/parholikhocs")
        Linkify.addLinks(stMyWeb, Linkify.ALL)

        val aboutDialog = AlertDialog.Builder(this)
                .setIcon(R.mipmap.ic_launcher)
                .setTitle("Puretoons.com Power by Parho Likho CS ")
                .setMessage(stMyWeb)
                .setPositiveButton("OK") { dialog, which ->
                    // TODO Auto-generated method stub
                }
                .create()

        aboutDialog.show()

        (aboutDialog.findViewById<View>(android.R.id.message) as TextView).movementMethod = LinkMovementMethod.getInstance()

    }

    fun onForwardPressed() {

        if (mWebView.canGoForward()) {

            mWebView.goForward()
        } else {
            Toast.makeText(this, "Can't go further !", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.about -> {
                alertAbout()
                Toast.makeText(applicationContext, "Puretoons.net" , Toast.LENGTH_LONG).show()
            }
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.menu_home -> {
                Toast.makeText(applicationContext , "Home " , Toast.LENGTH_SHORT).show()
                val myHome = "https://www.puretoons.net"
                optionLinkOpen(myHome)
                return@OnNavigationItemSelectedListener true
            }

            R.id.menu_back -> {
                Toast.makeText(applicationContext , "Press Back " , Toast.LENGTH_SHORT).show()
                onBackPressed()
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_forward -> {

                onForwardPressed()
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_refresh -> {
                Toast.makeText(applicationContext , "Press Refresh " , Toast.LENGTH_SHORT).show()
                mWebView.reload()
                return@OnNavigationItemSelectedListener true
            }
            R.id.menu_option -> {
                Toast.makeText(applicationContext , "categories " , Toast.LENGTH_SHORT).show()
//                mWebView.reload()
                linkPassing()

                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }



    protected fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    // show an alert dialog
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("Write external storage permission is required.")
                    builder.setTitle("Please grant permission")
                    builder.setPositiveButton("OK") { dialogInterface, i ->
                        ActivityCompat.requestPermissions(
                                this,
                                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                                MY_PERMISSION_REQUEST_CODE
                        )
                    }
                    builder.setNeutralButton("Cancel", null)
                    val dialog = builder.create()
                    dialog.show()
                } else {
                    // Request permission
                    ActivityCompat.requestPermissions(
                            this,
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            MY_PERMISSION_REQUEST_CODE
                    )
                }
            } else {
                // Permission already granted
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSION_REQUEST_CODE -> {
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted
                } else {
                    // Permission denied
                }
            }
        }
    }

}
