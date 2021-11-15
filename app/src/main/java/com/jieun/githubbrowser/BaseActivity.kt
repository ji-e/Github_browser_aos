package com.jieun.githubbrowser

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

/**
 * date 2021-11-13
 * create by jieun
 */
abstract class BaseActivity<B : ViewDataBinding> : AppCompatActivity() {
    @LayoutRes
    abstract fun getLayoutId(): Int

    protected lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.lifecycleOwner = this

        overridePendingTransition(0, 0)
    }

    override fun onResume() {
        super.onResume()
    }


    /** 프래그먼트 교체*/
    fun replaceFragment(layoutRes: Int, fragment: Fragment, isBackStack: Boolean = true) {
        if (isBackStack) {
            supportFragmentManager.commit(true) { (replace(layoutRes, fragment)).addToBackStack(fragment.javaClass.simpleName) }
        } else {
            supportFragmentManager.commit(true) { replace(layoutRes, fragment) }
        }
    }

    fun removeTopFragment() {
        supportFragmentManager.popBackStack()
    }

    /** 키보드 숨기기*/
    fun hideKeyboard() {
        if (currentFocus != null) {
            val manager: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(
                currentFocus!!.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }

}