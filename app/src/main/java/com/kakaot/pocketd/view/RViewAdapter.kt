package com.kakaot.pocketd.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.kakaot.pocketd.R
import com.kakaot.pocketd.data.pkname.PkName
import com.kakaot.pocketd.view.dialog.PkDetailDialog

class RViewAdapter(activityContext: Context, onItemClick: MainViewPresenter.IOnItemClick) :
    RecyclerView.Adapter<RViewAdapter.PkViewHolder>() {
    private var mList: ArrayList<PkName> = ArrayList()
    private var mIOnItemClick: MainViewPresenter.IOnItemClick = onItemClick

    class PkViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layout: CardView = view.findViewById(R.id.card_view)
        val nameViewKr: TextView = view.findViewById(R.id.name)
    }

    fun setList(list: ArrayList<PkName>) {
        mList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PkViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rview_item, parent, false)
        return PkViewHolder(view)
    }

    override fun onBindViewHolder(holder: PkViewHolder, position: Int) {
        val data: PkName = mList[position]
        holder.layout.setOnClickListener {
            mIOnItemClick.onClick(data)
        }
        holder.nameViewKr.text = data.name_kr
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}