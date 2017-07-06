package com.sothawo

import com.vaadin.annotations.PreserveOnRefresh
import com.vaadin.server.VaadinRequest
import com.vaadin.server.VaadinSession
import com.vaadin.spring.annotation.SpringUI
import com.vaadin.ui.*
import java.net.InetAddress


/**
 * @author P.J. Meisch (Peter.Meisch@hlx.com)
 */
@SpringUI
@PreserveOnRefresh
class MainUI : UI() {

    override fun init(vaadinRequest: VaadinRequest?) {
        val sessionId = VaadinSession.getCurrent().session.id

        // build the UI
        val verticalLayout = VerticalLayout()
        val textField = TextField()
        val label = Label("new ui created on ${hostName()}()")
        val button = Button("change text").apply {
            addClickListener {
                label.value = "\"${textField.value}\" processed on ${hostName()}"
                textField.apply { clear(); focus() }
            }
        }
        textField.focus()
        verticalLayout.addComponents(HorizontalLayout().apply {
            addComponents(textField, button)
        }, label, Label("Session-ID: $sessionId"))

        content = verticalLayout
    }

    private fun hostName() = InetAddress.getLocalHost().hostAddress ?: "(?.?.?.?)"

}
