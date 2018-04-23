package com.honyum.elevatorMan.fragment.contart

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.honyum.elevatorMan.R
import com.honyum.elevatorMan.adapter.ContractEnclosureListAdapter
import com.honyum.elevatorMan.net.ContractInfoDetailResponse
import org.jetbrains.anko.find

class ContartEnclosureFragment : Fragment() {

    var response: ContractInfoDetailResponse = ContractInfoDetailResponse()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        var bundle:Bundle = arguments

        response = bundle.getSerializable("response") as ContractInfoDetailResponse

        var view:View = inflater!!.inflate(R.layout.contart_enclosure_fragment,null)

        initView(view)

        return view
    }

    fun initView(view:View){
        view.find<ListView>(R.id.lv_list_enclosure).adapter = ContractEnclosureListAdapter(response.body.listContractFile,activity)
    }
}
