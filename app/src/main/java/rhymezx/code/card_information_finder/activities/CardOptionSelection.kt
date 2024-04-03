package rhymezx.code.card_information_finder.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import io.card.payment.CardIOActivity
import io.card.payment.CreditCard
import rhymezx.code.card_information_finder.R
import rhymezx.code.card_information_finder.databinding.ActivityCardOptionSelectionBinding


@Suppress("DEPRECATION")
class CardOptionSelection : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityCardOptionSelectionBinding

    private var backPass: Long? = 0

    //keep track of camera intent
    private val CAMERA = 2

    private var currentPhotoPath = ""

    //bundle data
    private var bundle: Bundle = Bundle()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123) {
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                val scanResult: CreditCard? =
                    data.getParcelableExtra(CardIOActivity.EXTRA_SCAN_RESULT)

                setCard(scanResult?.cardNumber?: "")
            } else {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    "Scan was canceled.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        binding = ActivityCardOptionSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.cardNumber.setOnClickListener(this)
        binding.cardOcr.setOnClickListener(this)
    }

    private fun setCard(card: String) {
        bundle.putString("cardNumber", card)
        startActivity(
            Intent(
                this,
                OcrConfirm::class.java
            ).putExtras(bundle)
        )
    }

    private fun scanCard() {
        val scanIntent = Intent(this, CardIOActivity::class.java)

        // customize these values to suit your needs.
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, true) // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false) // default: false
        scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false) // default: false

        // MY_SCAN_REQUEST_CODE is arbitrary and is only used within this activity.
        startActivityForResult(scanIntent, 123)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.card_number -> startActivity(
                    Intent(
                        this,
                        CardProcessor::class.java
                    )
                )

                R.id.card_ocr -> {
                    scanCard()
//                    Dexter.withActivity(this)
//                        .withPermissions(
//                            Manifest.permission.CAMERA
//                            , Manifest.permission.READ_EXTERNAL_STORAGE
//                            , Manifest.permission.WRITE_EXTERNAL_STORAGE
//                        )
//                        .withListener(object : MultiplePermissionsListener {
//                            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
//                                report?.let {
//                                    if (report.areAllPermissionsGranted()) {
//                                        takePhotoFromCamera()
//                                    } else if (report.isAnyPermissionPermanentlyDenied) {
//                                        showSettingsDialog()
//                                    }
//                                }
//                            }
//
//                            override fun onPermissionRationaleShouldBeShown(
//                                permissions: MutableList<com.karumi.dexter.listener.PermissionRequest>?,
//                                token: PermissionToken?
//                            ) {
//                                token?.continuePermissionRequest()
//                            }
//                        })
//                        .withErrorListener {
//                            Snackbar.make(
//                                findViewById(android.R.id.content),
//                                it.name,
//                                Snackbar.LENGTH_LONG
//                            ).show()
//                        }
//                        .check()
                }
            }
        }
    }

//    private fun showSettingsDialog() {
//        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
//        builder.setTitle("Need Permissions")
//        builder.setMessage("This app needs permission to use this feature. You can grant them in app settings.")
//        builder.setPositiveButton(
//            "GOTO SETTINGS"
//        ) { dialog, _ ->
//            dialog.cancel()
//            openSettings()
//        }
//        builder.setNegativeButton(
//            "CANCEL"
//        ) { dialog, _ ->
//            dialog.cancel()
//        }
//        builder.show()
//    }

//    private fun openSettings() {
//        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//        val uri = Uri.fromParts("package", packageName, null)
//        intent.data = uri
//        startActivityForResult(intent, 101)
//    }
//
//    private fun takePhotoFromCamera() = try {
//        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        val file: File = getImageFile()
//        val uri: Uri
//        uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID +
//                    ".provider", file)
//        } else {
//            Uri.fromFile(file)
//        }
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
//        startActivityForResult(intent, CAMERA)
//    } catch (err: ActivityNotFoundException) {
//        Log.v("error:", err.toString())
//        Snackbar.make(
//            findViewById(android.R.id.content),
//            "error: $err",
//            Snackbar.LENGTH_LONG
//        ).show()
//    }

//    @Suppress("DEPRECATION")
//    private fun getImageFile(): File {
//        val imageFileName = "JPEG_" + System.currentTimeMillis() + "_"
//        val storageDir = File(
//            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
//            "Camera"
//        )
//        val file: File = File.createTempFile(imageFileName, ".jpg", storageDir)
//        currentPhotoPath = "file:" + file.absolutePath
//        return file
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        when (requestCode) {
//
//            CAMERA -> {
//                if (resultCode == Activity.RESULT_OK) {
//                    val uri = Uri.parse(currentPhotoPath)
//                    launchImageCrop(uri)
//                } else {
//                    Log.v("Image error:", "Couldn't select that image from camera.")
//                    Snackbar.make(
//                        findViewById(android.R.id.content),
//                        "Image selection error: Couldn't select that image from camera.",
//                        Snackbar.LENGTH_LONG
//                    ).show()
//                }
//            }
//
//            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
//                val result = CropImage.getActivityResult(data)
//                if (resultCode == Activity.RESULT_OK) {
//                    if (result != null) {
//                        try {
//                            val bitmap =
//                                MediaStore.Images.Media.getBitmap(this.contentResolver, result.uri)
//                            textExtract(bitmap)
//                            Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show()
//                        } catch (e: IOException) {
//                            e.printStackTrace()
//                            Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
//                        }
//
//                    }
//
//                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                    Log.v("Crop error: ", result.error.toString())
//                    Snackbar.make(
//                        findViewById(android.R.id.content),
//                        "Crop error: ${result.error}",
//                        Snackbar.LENGTH_LONG
//                    ).show()
//                }
//            }
//
//        }
//    }
//
//    private fun launchImageCrop(uri: Uri) {
//        CropImage.activity(uri)
//            .setGuidelines(CropImageView.Guidelines.ON)
//            .setAspectRatio(1920, 400)
//            .setCropShape(CropImageView.CropShape.RECTANGLE) // default is rectangle
//            .start(this)
//        Toast.makeText(
//            this,
//            "Crop only your card number!!!",
//            Toast.LENGTH_LONG
//        ).show()
//    }
//
//    private fun textExtract(image: Bitmap) {
//        val textRecognizer: TextRecognizer = TextRecognizer.Builder(applicationContext).build()
//        val imageFrame: Frame = Frame.Builder()
//            .setBitmap(image) // your image bitmap
//            .build()
//        var imageText = ""
//        val textBlocks: SparseArray<TextBlock> = textRecognizer.detect(imageFrame)
//        if (textBlocks.size() != 0) {
//            var i = 0
//            while (i < textBlocks.size()) {
//                val textBlock: TextBlock = textBlocks.get(textBlocks.keyAt(i))
//                imageText = textBlock.value // return string
//                i++
//            }
//            bundle.putString("cardNumber", imageText)
//            startActivity(
//                Intent(
//                    this,
//                    OCRconfirm::class.java
//                ).putExtras(bundle)
//            )
//
//        } else {
//            Toast.makeText(this, "Invalid number!!!", Toast.LENGTH_SHORT).show()
//            takePhotoFromCamera()
//        }
//    }

    override fun onBackPressed() {
        if (backPass!! + 2000 > System.currentTimeMillis()) {
            val a = Intent(Intent.ACTION_MAIN)
            a.addCategory(Intent.CATEGORY_HOME)
            a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(a)
            finishAffinity()
        } else {
            Snackbar.make(
                findViewById(android.R.id.content),
                "Touch again to exit",
                Snackbar.LENGTH_SHORT
            ).show()
            backPass = System.currentTimeMillis()
        }
    }

}

