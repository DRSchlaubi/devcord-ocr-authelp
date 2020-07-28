package tech.devcord.autohelp.ocr.core

import io.ktor.routing.Route
import io.ktor.routing.RouteSelector
import io.ktor.routing.RouteSelectorEvaluation
import io.ktor.routing.RoutingResolveContext

internal inline fun Route.with(callback: Route.() -> Unit, builder: Route.() -> Unit): Route {
    val route = this.createChild(object : RouteSelector(1.0) {
        override fun evaluate(context: RoutingResolveContext, segmentIndex: Int): RouteSelectorEvaluation =
            RouteSelectorEvaluation.Constant
    })

    builder(route)

    return route.apply(callback)
}
