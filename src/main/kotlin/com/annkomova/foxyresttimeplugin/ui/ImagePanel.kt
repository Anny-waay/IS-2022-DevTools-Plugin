package com.annkomova.foxyresttimeplugin.ui

import java.awt.Dimension
import java.awt.Graphics
import java.awt.image.BufferedImage
import javax.swing.JPanel

class ImagePanel(private var image: BufferedImage) : JPanel() {

    override fun getPreferredSize(): Dimension {
        return Dimension(this.image.getWidth(this), this.image.getHeight(this))
    }

    override fun paint(g: Graphics) {
        g.drawImage(image, 0, 0, this)
    }
}
