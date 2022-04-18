package com.holo.architecture.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Canvas
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.graphics.drawable.toDrawable
import com.holo.architecture.R
import com.holo.architecture.databinding.LayoutTitleBarBinding
import com.holo.architecture.ext.clickNoRepeat
import com.holo.architecture.ext.dp2px
import com.holo.architecture.ext.sp2px
import com.holo.architecture.ext.toVisible

/**
 *
 *
 * @Author holo
 * @Date 2022/2/16
 */
class TitleBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    private lateinit var binding: LayoutTitleBarBinding

    init {
        initLayout(context, attrs)
    }

    private fun initLayout(context: Context, attrs: AttributeSet?) {
        val root = View.inflate(context, R.layout.layout_title_bar, this)
        binding = LayoutTitleBarBinding.bind(root)

        val typed = context.obtainStyledAttributes(attrs, R.styleable.TitleBar)
        val barBackground = typed.getDrawable(R.styleable.TitleBar_barBackground) ?: Color.WHITE.toDrawable()

        val navigationIcon = typed.getDrawable(R.styleable.TitleBar_navigationIcon)
        val navigationIconTint = typed.getColor(R.styleable.TitleBar_navigationIconTint, Color.parseColor("#272727"))

        val title = typed.getString(R.styleable.TitleBar_title)
        val titleTextColor = typed.getColor(R.styleable.TitleBar_titleTextColor, Color.parseColor("#272727"))
        val titleTextSize = typed.getDimension(R.styleable.TitleBar_titleTextSize, 18f.sp2px())
        val titleCentered = typed.getBoolean(R.styleable.TitleBar_titleCentered, true)

        val menuIcon = typed.getDrawable(R.styleable.TitleBar_menuIcon)

        val menuTitle = typed.getString(R.styleable.TitleBar_menuTitle)
        val menuTitleColor = typed.getColor(R.styleable.TitleBar_menuTitleColor, Color.parseColor("#272727"))
        val menuTitleSize = typed.getDimension(R.styleable.TitleBar_menuTitleSize, 15f.sp2px())

        val menuBtnTitle = typed.getString(R.styleable.TitleBar_menuBtnTitle)
        val menuBtnTextColor = typed.getColor(R.styleable.TitleBar_menuBtnTextColor, Color.WHITE)
        val menuBtnTextSize = typed.getDimension(R.styleable.TitleBar_menuBtnTextSize, 15f.sp2px())
        val menuBtnBackgroundColor = typed.getColor(R.styleable.TitleBar_menuBtnBackgroundColor, -1)
        val menuBtnBackgroundDrawable = typed.getDrawable(R.styleable.TitleBar_menuBtnBackgroundDrawable)
        val menuBtnCornerRadius = typed.getDimensionPixelSize(R.styleable.TitleBar_menuBtnCornerRadius, 5.dp2px())

        val showDivider = typed.getBoolean(R.styleable.TitleBar_barShowDivider, false)
        typed.recycle()

        binding.titleBar.background = barBackground

        navigationIcon?.let { icon ->
            if (navigationIcon != null) {
                binding.ivNavigation.setImageDrawable(icon)
            } else {
                binding.ivNavigation.setImageResource(R.drawable.ic_back)
            }
        }
        binding.ivNavigation.imageTintList = ColorStateList.valueOf(navigationIconTint)

        binding.barTitle.text = title
        binding.barTitle.setTextColor(titleTextColor)
        binding.barTitle.paint.textSize = titleTextSize
        binding.barTitle.gravity = if (titleCentered) Gravity.CENTER else Gravity.START

        binding.ivMenu.toVisible(menuIcon != null)
        menuIcon?.let { icon ->
            binding.ivMenu.setImageDrawable(icon)
        }

        binding.tvMenu.toVisible(!menuTitle.isNullOrBlank())
        if (!menuTitle.isNullOrBlank()) {
            binding.tvMenu.text = menuTitle
            binding.tvMenu.setTextColor(menuTitleColor)
            binding.tvMenu.paint.textSize = menuTitleSize
        }

        binding.btnMenu.toVisible(!menuBtnTitle.isNullOrBlank())
        if (!menuBtnTitle.isNullOrBlank()) {
            binding.btnMenu.text = menuBtnTitle
            binding.btnMenu.setTextColor(menuBtnTextColor)
            binding.btnMenu.paint.textSize = menuBtnTextSize

            if (menuBtnBackgroundColor != -1) {
                binding.btnMenu.backgroundTintList = ColorStateList.valueOf(menuBtnBackgroundColor)
            }
            menuBtnBackgroundDrawable?.let { drawable ->
                binding.btnMenu.background = drawable
                binding.btnMenu.backgroundTintList = null
            }
            binding.btnMenu.cornerRadius = menuBtnCornerRadius
        }
        binding.barDivider.toVisible(showDivider)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val params = binding.barTitle.layoutParams as LayoutParams
        params.marginStart = binding.clMenu.width
        params.marginEnd = binding.clMenu.width
        binding.barTitle.layoutParams = params
    }

    fun setTitle(title: String) {
        binding.barTitle.text = title
    }

    fun init(title: String = "", backAction: () -> Unit) {
        setTitle(title)
        binding.ivNavigation.clickNoRepeat { backAction.invoke() }
    }
}