package com.calendar.core.indicator.pager

import android.database.DataSetObserver
import android.graphics.Color
import android.text.format.DateUtils
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewpager.widget.PagerAdapter
import com.calendar.core.CalendarDay
import com.calendar.core.CalendarPagerAdapter
import com.calendar.core.utils.DpUtils
import java.util.*

/**
 * Created by NguyenBangGiang on 8/3/2023
 */
class PagerIndicatorAdapter(private val pagerAdapter: CalendarPagerAdapter<*>) : PagerAdapter() {
    private val currentViews: ArrayDeque<FrameLayout> = ArrayDeque()
    var defaultButtonBackgroundColor = Color.parseColor("#f8f9f9")
    var defaultButtonTextColor = Color.parseColor("#1a1a1a")
    var selectedButtonBackgroundColor = Color.parseColor("#1398f5")
    var selectedButtonTextColor = Color.WHITE

    init {
        pagerAdapter.registerDataSetObserver(object : DataSetObserver() {
            override fun onChanged() {
                notifyDataSetChanged()
                invalidateSelectedMonths()
            }
        })
    }

    fun invalidateSelectedMonths() {
        for (view in currentViews) {
            (view.getChildAt(0) as SmartButton).setBigButtonColor(
                color = defaultButtonBackgroundColor,
                textColor = defaultButtonTextColor
            )
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = SmartButton(container.context)
        val month = pagerAdapter.getItem(position)
        view.text = DateUtils.formatDateTime(
            container.context, month.date.time,
            DateUtils.FORMAT_NO_MONTH_DAY or DateUtils.FORMAT_SHOW_YEAR
        )
        view.tag = month
        initView(view, month)
        val parent = FrameLayout(container.context)
        val lp = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        lp.gravity = Gravity.CENTER
        parent.addView(view, lp)
        currentViews.add(parent)
        container.addView(parent)
        view.setOnClickListener { btn ->
            val day = btn.tag as CalendarDay
            pagerAdapter.mcv.selectRange(
                day, CalendarDay.from(
                    day.year,
                    day.month,
                    day.calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                )
            )
        }
        return parent
    }

    private fun initView(view: SmartButton, month: CalendarDay) {
        val selectedDates = pagerAdapter.getSelectedDates()
        val isMonthSelected = selectedDates.any { it.month == month.month && it.year == month.year }
        val mainColor = if (isMonthSelected) {
            selectedButtonBackgroundColor
        } else {
            defaultButtonBackgroundColor
        }
        val textColor = if (mainColor == selectedButtonBackgroundColor) {
            selectedButtonTextColor
        } else {
            defaultButtonTextColor
        }
        view.init(
            mainColor = mainColor, mainTextColor = textColor,
            cornerRadius = 50
        )
        val dp184 = DpUtils.dpToPx(view.context, 184)
        view.minWidth = dp184
        view.height = DpUtils.dpToPx(view.context, 40)
    }

    @Suppress("UNCHECKED_CAST")
    override fun getItemPosition(`object`: Any): Int {
        if (`object` !is FrameLayout) {
            return POSITION_NONE
        }
        val index = indexOf(`object`.getChildAt(0).tag as CalendarDay)
        return if (index < 0) {
            POSITION_NONE
        } else index
    }

    fun indexOf(month: CalendarDay): Int = pagerAdapter.getIndexForDay(month)

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount(): Int = pagerAdapter.count

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as FrameLayout
        currentViews.remove(view)
        container.removeView(view)
    }

    fun selectMonths(start: CalendarDay?, end: CalendarDay? = null) {
        invalidateSelectedMonths()
        if (start == null) {
            return
        }
        if (end == null) {
            return selectMonthInternal {
                val month = it.tag as CalendarDay
                month.month == start.month &&
                        month.year == start.year
            }
        }
        selectMonthInternal {
            val month = it.tag as CalendarDay
            month.month == start.month && month.year == start.year ||
                    month.isInRange(start, end)

        }
    }

    private fun selectMonthInternal(predicate: (SmartButton) -> Boolean) {
        currentViews
            .map { it.getChildAt(0) as SmartButton }
            .filter(predicate)
            .forEach {
                it.setBigButtonColor(
                    color = selectedButtonBackgroundColor,
                    textColor = selectedButtonTextColor
                )
            }
    }
}