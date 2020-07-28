package tech.devcord.autohelp.ocr.core

import io.ktor.http.HttpStatusCode
import io.ktor.http.content.PartData
import io.ktor.http.content.forEachPart
import io.ktor.http.content.streamProvider
import io.ktor.request.isMultipart
import io.ktor.request.receiveMultipart
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.post
import io.ktor.routing.route

fun Routing.ocr() {
    route("/ocr") {
        authorized {
            ratelimited {
                post {
                    if (!context.request.isMultipart()) return@post context.respond(HttpStatusCode.BadRequest)
                    val multipart = context.receiveMultipart()

                    var image: PartData.FileItem? = null
                    multipart.forEachPart {
                        if (it is PartData.FileItem) {
                            image = it
                            return@forEachPart
                        }
                    }
                    if (image == null) {
                        return@post context.respond(HttpStatusCode.BadRequest, "Missing image")
                    }

                    val text = image!!.streamProvider().use {
                        ImageRecognizer.readImageText(it)
                    }

                    context.respond(text)
                }
            }
        }
    }
}
