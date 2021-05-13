package com.soma_quokka.dreamtree.presentation.store_detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.soma_quokka.dreamtree.R
import com.soma_quokka.dreamtree.data.model.Store
import com.soma_quokka.dreamtree.databinding.ActivityStoreDetailBinding

class StoreDetailActivity : AppCompatActivity() {

    lateinit var binding : ActivityStoreDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_detail)

        // DataBinding
        binding = DataBindingUtil.setContentView(
            this,
            R.layout.activity_store_detail
        )

        /**
         * 전달받은 Store Item 정보를 얻음
         */
        val storeItem: Store = intent.getSerializableExtra(STORE_ITEM) as Store

        binding.storeName.text = storeItem.name
        binding.storeType.text = storeItem.type

        // TODO : 변경된 Store Data Class 에 맞게 데이터 바인딩 해주면 됨

    }

    companion object{
        val STORE_ITEM = "STORE_ITEM"
    }

}