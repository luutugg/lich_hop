package com.example.lichhop.presentation

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lichhop.R
import com.example.lichhop.databinding.EventItemBinding
import getAppColor
import java.text.DateFormat
import java.text.SimpleDateFormat


class EventAdapter : ListAdapter<CacularEventDisplay, EventAdapter.EventVH>(EventDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventVH {
        return EventVH(EventItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: EventVH, position: Int) {
        return holder.onBind(getItem(position))
    }

    class EventDiffUtil : DiffUtil.ItemCallback<CacularEventDisplay>() {
        override fun areItemsTheSame(
            oldItem: CacularEventDisplay,
            newItem: CacularEventDisplay
        ): Boolean {
            return oldItem.heightTotal == newItem.heightTotal
        }

        override fun areContentsTheSame(
            oldItem: CacularEventDisplay,
            newItem: CacularEventDisplay
        ): Boolean {
            return oldItem.data?.dayName == newItem.data?.dayName
        }
    }

    inner class EventVH(private val binding: EventItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var mapOffSet: HashMap<Int, Float> = hashMapOf()

        init {

        }

        @SuppressLint("ResourceType")
        fun onBind(data: CacularEventDisplay) {
            with(binding) {
                tvEventTime.apply {
                    text = data.data?.dayName
                    val params = tvEventTime.layoutParams
                    params.width = data.widthTotal!!
                    params.height = (data.heightTotal?.div(20)!!)
                    tvEventTime.layoutParams = params
                }
                mapOffSet.clear()
                for (i in 0 until data.data?.event?.size!!) {
                    var marginTop = 0f
                    marginTop = if (i > 0) {
                        val distanceEvent = minusTime(
                            data.data?.event?.get(i - 1)?.endTime ?: "",
                            data.data?.event?.get(i)?.startTime ?: ""
                        )
                        data.heightTotal?.toFloat()?.div(20)!! * distanceEvent
                    } else {
                        val distanceEvent =
                            minusTime("08:00:00", data.data?.event?.get(i)?.startTime ?: "")
                        data.heightTotal?.toFloat()?.div(20)!! * distanceEvent
                    }
                    mapOffSet[i] = marginTop.offsetMargin()
                    val tv = TextView(this.root.context)
                    tv.apply {
                        text =
                            "${data.data?.event?.get(i)?.userName}\n${data.data?.event?.get(i)?.content}"
                        setTextColor(getAppColor(R.color.white))
                        gravity = Gravity.CENTER
                        setBackgroundColor(getAppColor(R.color._test))
                    }
                    binding.llEventItem.addView(tv)
                    val part = minusTime(
                        data.data?.event?.get(i)?.startTime ?: "",
                        data.data?.event?.get(i)?.endTime ?: ""
                    )
                    val params = tv.layoutParams as ViewGroup.MarginLayoutParams
                    params.width = data.widthTotal ?: 0
                    params.height = data.heightTotal?.div(20)!! * part
                    val offset = marginTop.offsetMargin()
                    if (offset >= 0.0f) {
                        params.topMargin = (marginTop.toInt() + 1)
                    } else {
                        //params.topMargin = (marginTop.toInt())
                    }
                    tv.layoutParams = params
                }
            }
        }

        private fun sumOffsetMargin(i: Int): Int {
            var sum = 0f
            for (j in 0..i) {
                if (mapOffSet[j] != 0f) {
                    sum += mapOffSet[j]!!
                }
            }
            return if (sum != 0f && sum < 0.5f) {
                (sum + 1).toInt()
            } else {
                1
            }
        }

        private fun Float.offsetMargin(): Float {
            val number = this
            return number % 1
        }

        private fun minusTime(startTime: String, endTime: String): Int {
            val timeFormat: DateFormat = SimpleDateFormat("HH:mm:ss")

            return try {
                // Thời điểm ban đầu
                val gioBanDau = timeFormat.parse(endTime)

                // Thời điểm muốn trừ
                val gioMuonTru = timeFormat.parse(startTime)

                // Chuyển đổi thời gian sang milliseconds
                val gioBanDauMillis = gioBanDau.time
                val gioMuonTruMillis = gioMuonTru.time

                // Tính khoảng thời gian (đơn vị milliseconds)
                val khoangThoiGianMillis = gioBanDauMillis - gioMuonTruMillis

                // Chuyển đổi khoảng thời gian thành giờ và phút
                val giay = khoangThoiGianMillis / 1000
                val gio = giay / 3600
                val phut = giay % 3600 / 60

                // In ra kết quả
                if (gio > 0) {
                    if (phut > 0) {
                        (gio * 2 + 1).toInt()
                    } else {
                        (gio * 2).toInt()
                    }
                } else {
                    if (phut > 0) {
                        1
                    } else {
                        0
                    }
                }

            } catch (e: Exception) {
                0
            }
        }
    }
}
