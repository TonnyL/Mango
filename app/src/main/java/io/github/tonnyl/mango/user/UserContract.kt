package io.github.tonnyl.mango.user

import io.github.tonnyl.mango.BasePresenter
import io.github.tonnyl.mango.BaseView

/**
 * Created by lizhaotailang on 2017/6/28.
 */
interface UserContract {

    interface View: BaseView<Presenter> {



    }

    interface Presenter: BasePresenter {

    }

}