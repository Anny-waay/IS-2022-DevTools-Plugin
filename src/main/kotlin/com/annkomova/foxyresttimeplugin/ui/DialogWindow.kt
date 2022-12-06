package com.annkomova.foxyresttimeplugin.ui

import com.intellij.ui.layout.CellBuilder
import com.intellij.ui.layout.panel
import com.annkomova.foxyresttimeplugin.services.AppService
import com.annkomova.foxyresttimeplugin.services.Status
import java.awt.event.*
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
import javax.swing.JComponent
import javax.swing.JDialog
import javax.swing.JLabel
import javax.swing.KeyStroke

class DialogWindow (private val service: AppService) : JDialog() {
    private lateinit var timerLabel: CellBuilder<JLabel>
    private lateinit var imagePanel: ImagePanel

    init {
        title = "Time to rest"
        isModal = true
        defaultCloseOperation = DO_NOTHING_ON_CLOSE
        setLocation(210, 70)
        contentPane = panel {
            row {
                placeholder().withLargeLeftGap()
                label("\nFox says: it's time to take a break!")
            }
            row {
                val image = getImage()
                resize(image)
                imagePanel = ImagePanel(image)
                component(imagePanel)
            }
            row {
                placeholder().withLargeLeftGap()
                label("Rest time: ")
                timerLabel = label("05:00")
            }
        }

        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent) {
                onCancel()
            }
        })

        rootPane.registerKeyboardAction(
            { onCancel() },
            KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
            JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
        )
    }

    fun setTime(text: String) {
        timerLabel.component.text = text
    }

    private fun resize(image: BufferedImage) {
        setSize(image.width, image.height + 120)
    }

    fun showRestWindow() {
        isVisible = true
    }

    private fun getImage(): BufferedImage {
        val input = DialogWindow::class.java.classLoader.getResourceAsStream("images/rest-fox.jpeg")
        return ImageIO.read(input)
    }

    private fun onCancel() {
        if (service.status != Status.REST) {
            dispose()
        }
    }
}