/*
 * Copyright (c) 2017 Lizhaotailang
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package io.github.tonnyl.mango.epoxy

import com.airbnb.epoxy.EpoxyAdapter
import com.airbnb.epoxy.EpoxyModel

/**
 * Created by lizhaotailang on 13/12/2017.
 */
class MangoEpoxyAdapter : EpoxyAdapter() {

    private val mLoadMoreModel = LoadMoreModel_()

    public override fun addModel(modelToAdd: EpoxyModel<*>) {
        super.addModel(modelToAdd)
    }

    public override fun addModels(modelsToAdd: Array<EpoxyModel<*>>) {
        super.addModels(*modelsToAdd)
    }

    public override fun addModels(modelsToAdd: Collection<EpoxyModel<*>>) {
        super.addModels(modelsToAdd)
    }

    public override fun removeAllModels() {
        super.removeAllModels()
    }

    public override fun removeModel(model: EpoxyModel<*>) {
        super.removeModel(model)
    }

    fun showLoadingMore(loading: Boolean) {
        if (loading) {
            addModel(mLoadMoreModel)
        } else {
            removeModel(mLoadMoreModel)
        }
    }
}