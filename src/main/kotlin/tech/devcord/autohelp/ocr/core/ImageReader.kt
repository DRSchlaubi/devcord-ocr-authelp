package tech.devcord.autohelp.ocr.core

import com.google.cloud.vision.v1.AnnotateImageRequest
import com.google.cloud.vision.v1.Feature
import com.google.cloud.vision.v1.Image
import com.google.cloud.vision.v1.ImageAnnotatorClient
import com.google.protobuf.ByteString
import tech.devcord.autohelp.ocr.ratelimit.Ratelimiter
import java.io.InputStream

/**
 * Utility to use OCR (Optical Character Recognition).
 */
object ImageRecognizer {


    private val client = ImageAnnotatorClient.create()

    /**
     * Tries to read the text seen in [inputStream].
     */
    fun readImageText(inputStream: InputStream): String {
        Ratelimiter.register()
        val imageBytes = ByteString.readFrom(inputStream)
        val image = Image.newBuilder().setContent(imageBytes).build()
        val findTextFeature = Feature.newBuilder().setType(Feature.Type.TEXT_DETECTION).build()
        val request = AnnotateImageRequest.newBuilder().addFeatures(findTextFeature).setImage(image).build()
        val response = client.batchAnnotateImages(listOf(request)).responsesList.first()
        if (response.hasError()) {
            throw IllegalStateException(response.error.message)
        }
        return response.textAnnotationsList.first().description
    }
}
