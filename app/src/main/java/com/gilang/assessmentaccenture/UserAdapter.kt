package com.gilang.assessmentaccenture

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gilang.assessmentaccenture.data.model.Obj_User
import java.util.*

class UserAdapter(
  private val mContext: Context,
  itemList: Obj_User,
  listener: OnDownloadClicked
) :
  RecyclerView.Adapter<UserAdapter.MyViewHolder>(), Filterable {
  var itemList: Obj_User
  var itemListFilter: Obj_User

  interface OnDownloadClicked {

  }

  private val listener: OnDownloadClicked

  inner class MyViewHolder(v: View) : RecyclerView.ViewHolder(v),
    View.OnClickListener, OnLongClickListener {
    lateinit var txtUser: TextView
    lateinit var imgUser: ImageView

    override fun onClick(v: View) {}
    override fun onLongClick(v: View): Boolean {
      return false
    }

    init {
      txtUser = v.findViewById<View>(R.id.txtUser) as TextView
      imgUser = v.findViewById<View>(R.id.imgUser) as ImageView
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val itemView: View = LayoutInflater.from(parent.context)
      .inflate(R.layout.item_user, parent, false)
    return MyViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    val dat: Obj_User.Data = itemListFilter.data!![position]
    val sName: String? = dat.login
    val sImg: String? = dat.avatar_url
    holder.txtUser.text = sName

  }

  override fun getItemCount(): Int {
    try {
      return if (itemListFilter.data != null) {
        itemListFilter.data!!.size
      } else {
        0
      }
    } catch (e: Exception) {
      Log.e("ErrorItemCount", e.toString())
    }
    return 0
  }

  override fun getFilter(): Filter {
    return object : Filter() {
      override fun performFiltering(constraint: CharSequence): FilterResults {
        val filterString = constraint.toString().lowercase(Locale.getDefault())
        val results = FilterResults()

        //int count = itemList.data.size();
        val nlist: ArrayList<Obj_User.Data> = ArrayList<Obj_User.Data>()
        for (dataFilter in itemList.data!!) {
          if (dataFilter.login?.toLowerCase()?.contains(filterString) == true) {
            nlist.add(dataFilter)
          }
        }
        results.values = nlist
        results.count = nlist.size
        return results
      }

      override fun publishResults(constraint: CharSequence, results: FilterResults) {
        try {
          itemListFilter.data = results.values as List<Obj_User.Data>
          notifyDataSetChanged()
        } catch (e: Exception) {
          e.printStackTrace()
        }
      }
    }
  }

  init {
    this.itemList = itemList
    itemListFilter = itemList
    this.listener = listener
  }
}
