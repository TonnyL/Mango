package io.github.tonnyl.mango.glide

import android.support.v7.graphics.Palette

/**
 * A callback when [Palette] finished processing.
 */
interface OnPaletteProcessingCallback {

    /**
     * The [Palette] finishes its work successfully.
     */
    fun OnPaletteGenerated(palette: Palette?)

    /**
     * The [Palette] finished its work with a failure.
     */
    fun OnPaletteNotAvailable()

}