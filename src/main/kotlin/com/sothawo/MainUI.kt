package com.sothawo

import com.vaadin.annotations.PreserveOnRefresh
import com.vaadin.server.VaadinRequest
import com.vaadin.server.VaadinSession
import com.vaadin.spring.annotation.SpringUI
import com.vaadin.ui.*


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
        val label = Label("new ui created")
        val button = Button("change text").apply {
            addClickListener {
                label.value = textField.value
                textField.apply { clear(); focus() }
            }
        }
        textField.focus()
        verticalLayout.addComponents(HorizontalLayout().apply {
            addComponents(textField, button)
        }, label, Label(sessionId))

        content = verticalLayout
    }

}
