package com.example.takeofflabstinder.utils

import androidx.annotation.AnimRes
import androidx.annotation.AnimatorRes
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.takeofflabstinder.R

fun FragmentManager.pop() {
    popBackStack()
}

fun FragmentManager.popNow() {
    popBackStackImmediate()
}

fun FragmentManager.popTo(fragment: String) {
    popBackStack(fragment, 0)
}

fun FragmentManager.popTo(fragment: Fragment) {
    popTo(fragment::class.java.name)
}

fun FragmentManager.popAll(): Boolean {
    if (backStackEntryCount == 0) return false
    val name = getBackStackEntryAt(0).name
    return popBackStackImmediate(name, FragmentManager.POP_BACK_STACK_INCLUSIVE)
}

fun FragmentManager.isEmpty(): Boolean = backStackEntryCount == 0

fun FragmentManager.isNotEmpty(): Boolean = !isEmpty()

fun FragmentManager.with(fragment: Fragment) = TransactionBuilder(this, fragment)

class TransactionBuilder(private var fragmentManager: FragmentManager, private val fragment: Fragment) {
    private var shouldAddToBackStack = true
    private var allowStateLoss = false
    private var animated = false
    @AnimatorRes @AnimRes private var entryAnimation = R.anim.left_enter
    @AnimatorRes @AnimRes private var outAnimation = R.anim.left_exit
    @IdRes private var layout = R.id.fullscreen_fragment_holder

    fun animate(animated: Boolean): TransactionBuilder {
        this.animated = animated
        return this
    }

    fun addToBackStack(shouldAddToBackStack: Boolean): TransactionBuilder {
        this.shouldAddToBackStack = shouldAddToBackStack
        return this
    }

    fun into(@IdRes container: Int): TransactionBuilder {
        layout = container
        return this
    }

    fun withStateLoss(allowStateLoss: Boolean): TransactionBuilder {
        this.allowStateLoss = allowStateLoss
        return this
    }

    fun withAnimations(@AnimatorRes @AnimRes entry: Int, @AnimatorRes @AnimRes exit: Int): TransactionBuilder {
        entryAnimation = entry
        outAnimation = exit
        animated = true
        return this
    }

    fun push() {
        val transaction = fragmentManager.beginTransaction()

        if (animated) {
            transaction.setCustomAnimations(entryAnimation, outAnimation, entryAnimation, outAnimation)
        }

        transaction.add(layout, fragment, fragment::class.java.name)

        if (shouldAddToBackStack)
            transaction.addToBackStack(fragment::class.java.name)

        if (allowStateLoss) {
            transaction.commitAllowingStateLoss()
        } else {
            transaction.commit()
        }
    }
}