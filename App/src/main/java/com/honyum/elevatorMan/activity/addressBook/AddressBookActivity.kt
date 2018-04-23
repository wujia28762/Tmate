package com.honyum.elevatorMan.activity.addressBook

import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.base.BaseActivityWraper
import kotlinx.android.synthetic.main.activity_address_book.*


class AddressBookActivity : BaseActivityWraper() {

    private var pinyinComparator: PinyinComparator? = null
    private var characterParser: CharacterParser? = null

    private var SourceDateList: MutableList<SortModel>? = null

    private var adapter: SortAdapter? = null

    override fun getTitleString(): String {
        return "通讯录"
    }
    override fun getLayoutID(): Int {
        return R.layout.activity_address_book
    }
    override fun initView() {
        //设置右侧触摸监听

        sideBar.setTextView(dialog)

        sideBar.setOnTouchingLetterChangedListener(object : SideBar.OnTouchingLetterChangedListener{
            override fun onTouchingLetterChanged(s: String?) {
                /*val position = adapter!!.getPositionForSection(s!!.codePointAt(0))
                if (position != -1) {
                    lv_address_book.setSelection(position)
                }*/
            }
        })
    }

   /* private fun filledData(date: Array<String>): List<SortModel> {
        val mSortList = ArrayList<SortModel>()

        for (i in date.indices) {
            val sortModel = SortModel()
            sortModel.name = date[i]

            val pinyin = characterParser!!.getSelling(date[i])
            val sortString = pinyin.substring(0, 1).toUpperCase()

            if (sortString.matches("[A-Z]".toRegex())) {
                sortModel.sortLetters = sortString.toUpperCase()
            } else {
                sortModel.sortLetters = "#"
            }

            mSortList.add(sortModel)
        }
        return mSortList

    }*/

  /*  private fun filterData(filterStr: String) {
        var filterDateList: MutableList<SortModel> = ArrayList()

        if (TextUtils.isEmpty(filterStr)) {
            filterDateList = SourceDateList
        } else {
            filterDateList.clear()
            for (sortModel in SourceDateList) {
                val name = sortModel.getName()
                if (name.indexOf(filterStr.toString()) != -1 || characterParser!!.getSelling(name).startsWith(filterStr.toString())) {
                    filterDateList.add(sortModel)
                }
            }
        }

        Collections.sort(filterDateList, pinyinComparator)
        adapter!!.updateListView(filterDateList)
    }*/
}
