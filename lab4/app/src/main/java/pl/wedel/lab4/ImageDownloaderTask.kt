package pl.wedel.lab4

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.widget.ImageView
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL

class ImageDownloaderTask(
    imageView: ImageView,
    private val url: String
) : AsyncTask<String, Void, Bitmap>() {
    private val imageViewReference: WeakReference<ImageView> = WeakReference(imageView)

    override fun doInBackground(vararg params: String?): Bitmap? {
        val urlConntection: HttpURLConnection
        try {
            val uri = URL(params[0])
            urlConntection = uri.openConnection() as HttpURLConnection
            if (urlConntection.responseCode != 200)
                return null
            val inputStream = urlConntection.inputStream
            if (inputStream != null) {
                val bitmap = BitmapFactory.decodeStream(inputStream)
                Thread.sleep(5000)
                return bitmap
            }
        } catch (e: Exception) {

        }
        return null
    }

    override fun onPostExecute(result: Bitmap?) {
        val imageView = imageViewReference.get()
        if (result == null) {
            imageView!!.setImageDrawable(imageView.context.resources.getDrawable(R.drawable.logo))
            return
        }
        imageView!!.setImageBitmap(result)

    }
}