package com.c23ps160.nutriscan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.icu.lang.UCharacter.VerticalOrientation
import android.media.ThumbnailUtils
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.c23ps160.nutriscan.Adapter.ArticleAdapter
import com.c23ps160.nutriscan.Adapter.FoodAdapter
import com.c23ps160.nutriscan.Model.Article
import com.c23ps160.nutriscan.Model.FoodData
import com.c23ps160.nutriscan.Model.QuickFood
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.c23ps160.nutriscan.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder

class MainActivity : AppCompatActivity() {

    private val openScanButton: FloatingActionButton by lazy {
        findViewById(R.id.open_scan)
    }

    private val openCameraButton: FloatingActionButton by lazy {
        findViewById(R.id.open_camera)
    }

    private val openGalleryButton: FloatingActionButton by lazy {
        findViewById(R.id.open_gallery)
    }

    private val rotateOpen: Animation by lazy {
        AnimationUtils.loadAnimation(
            this, R.anim.rotate_open_anim
        )
    }
    private val rotateClose: Animation by lazy {
        AnimationUtils.loadAnimation(
            this, R.anim.rotate_close_anim
        )
    }
    private val fromBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this, R.anim.from_bottom_anim
        )
    }
    private val toBottom: Animation by lazy {
        AnimationUtils.loadAnimation(
            this, R.anim.to_bottom_anim
        )
    }
    companion object {
        var foodData = FoodData()
    }

    private var clicked = false
    private val imageSize = 224
    private lateinit var recyclerView: RecyclerView
    private lateinit var foodList:ArrayList<QuickFood>
    private lateinit var foodAdapter: FoodAdapter
    private lateinit var articleRecyclerView: RecyclerView
    private lateinit var articleList:ArrayList<Article>
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.status_bar_color)
        }
        val profileIcon: ImageView = findViewById(R.id.profileIcon)
        profileIcon.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }
        openScanButton.setOnClickListener {
            onOpenScanButtonClicked()
        }
        openCameraButton.setOnClickListener {
            Toast.makeText(this, "Open Camera", Toast.LENGTH_SHORT).show()
        }
        openGalleryButton.setOnClickListener {
            Toast.makeText(this, "Open Gallery", Toast.LENGTH_SHORT).show()
        }

        // Scanner
        openCameraButton.setOnClickListener {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 3)
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }

        openGalleryButton.setOnClickListener {
            val cameraIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(cameraIntent, 1)
        }
        init()
        initiate()
    }

    private fun init(){
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(20)
        val layoutManager = GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false)
        recyclerView.layoutManager = layoutManager
        foodList = ArrayList()

        addFoodDataToList()

        foodAdapter = FoodAdapter(foodList)
        recyclerView.adapter = foodAdapter
    }

    private fun initiate(){
        articleRecyclerView = findViewById(R.id.articleRecyclerView)
        articleRecyclerView.setHasFixedSize(true)
        articleRecyclerView.setItemViewCacheSize(20)
        val layoutManager = LinearLayoutManager(this)
        articleRecyclerView.layoutManager = layoutManager
        articleList = ArrayList()

        addArticleDataToList()

        articleAdapter = ArticleAdapter(articleList)
        articleRecyclerView.adapter = articleAdapter
    }

    private fun addFoodDataToList(){
        foodList.add(QuickFood(R.drawable.ayam_goreng,"Ayam Goreng"))
        foodList.add(QuickFood(R.drawable.bubur,"Bubur Ayam"))
        foodList.add(QuickFood(R.drawable.es_krim,"Es Krim"))
        foodList.add(QuickFood(R.drawable.gado_gado,"Gado-Gado"))
        foodList.add(QuickFood(R.drawable.nasi_goreng,"Nasi Goreng"))
        foodList.add(QuickFood(R.drawable.kue_coklat,"Kue Coklat"))
        foodList.add(QuickFood(R.drawable.omellette,"Omellette"))
        foodList.add(QuickFood(R.drawable.kentang_goreng,"Kentang Goreng"))
        foodList.add(QuickFood(R.drawable.rendang,"Rendang"))
        foodList.add(QuickFood(R.drawable.sandwich,"Sandwich"))
    }

    private fun addArticleDataToList(){
        articleList.add(Article(R.drawable.bannerarticle_satu, "5 Jenis Nutrisi yang Harus Dipenuhi untuk Tubuh Sehat"))
        articleList.add(Article(R.drawable.bannerarticle_dua, "Pentingnya Gizi Seimbang bagi Kesehatan"))
        articleList.add(Article(R.drawable.bannerarticle_tiga, "Ini Alasan Pentingnya Memenuhi Kebutuhan Asupan Nutrisi di Pagi Hari"))
    }

    private fun onOpenScanButtonClicked() {
        setVisibility(clicked)
        setAnimation(clicked)
        clicked = !clicked
    }

    private fun setAnimation(clicked: Boolean) {
        if (!clicked) {
            openCameraButton.visibility = View.VISIBLE
            openGalleryButton.visibility = View.VISIBLE
        } else {
            openCameraButton.visibility = View.INVISIBLE
            openGalleryButton.visibility = View.INVISIBLE
        }
    }

    private fun setVisibility(clicked: Boolean) {
        if (!clicked) {
            openCameraButton.startAnimation(fromBottom)
            openGalleryButton.startAnimation(fromBottom)
            openScanButton.startAnimation(rotateOpen)
        } else {
            openCameraButton.startAnimation(toBottom)
            openGalleryButton.startAnimation(toBottom)
            openScanButton.startAnimation(rotateClose)
        }
    }

    private fun classifyImage(image: Bitmap) {
        try {
            val model = Model.newInstance(applicationContext)

            // Creates inputs for reference.
            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
            val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())

            val intValues = IntArray(imageSize * imageSize)
            image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)
            var pixel = 0
            // Iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for (i in 0 until imageSize) {
                for (j in 0 until imageSize) {
                    val value = intValues[pixel++] // RGB
                    byteBuffer.putFloat(((value shr 16) and 0xFF) * (1f / 255))
                    byteBuffer.putFloat(((value shr 8) and 0xFF) * (1f / 255))
                    byteBuffer.putFloat((value and 0xFF) * (1f / 255))
                }
            }

            inputFeature0.loadBuffer(byteBuffer)

            // Runs model inference and gets the result.
            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.getOutputFeature0AsTensorBuffer()

            val confidences = outputFeature0.floatArray
            // Find the index of the class with the highest confidence.
            var maxPos = 0
            var maxConfidence = 0f
            for (i in confidences.indices) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i]
                    maxPos = i
                }
            }

            val foodClasses: Array<String> = resources.getStringArray(R.array.food_classes)
            val foodNames: Array<String> = resources.getStringArray(R.array.food_names)


            if (maxConfidence >= 0.85f) {
                foodData.foodClass = foodClasses[maxPos]
                foodData.foodName = foodNames[maxPos]
            } else {
                foodData.foodClass = null
                foodData.foodName = "Mohon maaf data makanan tersebut belum terdaftar"
            }
            foodData.confidence = maxConfidence

            model.close()

            val intent = Intent(this@MainActivity, ResultActivity::class.java)
            startActivity(intent)
        } catch (e: IOException) {
            // TODO Handle the exception
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            foodData = FoodData()

            if (requestCode == 3) {
                val image = data?.extras?.get("data") as Bitmap
                val dimension = Math.min(image.width, image.height)
                val thumbnail = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
                foodData.image = thumbnail

                val scaledImage = Bitmap.createScaledBitmap(thumbnail, imageSize, imageSize, false)
                classifyImage(scaledImage)
            } else if (requestCode == 1) {
                val imageUri = data?.data
                val image = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                foodData.image = image

                val scaledImage = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
                classifyImage(scaledImage)
            }
        }
    }
}
