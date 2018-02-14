package net.slipp.hkp.handler

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerResponse.ok

@Component
class ViewHandler {
//    fun getView(viewName: String) =
//            ok().html().render(viewName)

    fun getView(viewName: String) = ok().render(viewName)

}