package com.soma_quokka.dreamtree.presentation.store_detail

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.soma_quokka.dreamtree.R
import com.soma_quokka.dreamtree.data.response.StoreResponseItem
import com.soma_quokka.dreamtree.databinding.ActivityStoreDetailBinding
import java.text.DecimalFormat

class StoreDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityStoreDetailBinding

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
        val storeItem: StoreResponseItem = intent.getParcelableExtra(STORE_ITEM)!!

        binding.storeName.text = storeItem.name
        binding.storeType.text = storeItem.type

        Glide
            .with(this)
            .load(storeItem.imgurl)
            .centerCrop()
            .into(binding.storeImage)
        binding.storeImage.setColorFilter(Color.parseColor("#9F9F9F"), PorterDuff.Mode.MULTIPLY)


        /**
         *  장소 위치 정보를 기반으로 GoogleMap Intent
         */
        binding.storeAddress.text = storeItem.address
        binding.storeLocationCard.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:0,0?q=${storeItem.address}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }

        /**
         * 전화번호 정보를 기반으로 Call Intent
         */
        binding.storePhoneNumber.text = storeItem.phoneNumber
        binding.phoneNumberCard.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_VIEW)
            callIntent.data = Uri.parse("tel:" + storeItem.phoneNumber)
            startActivity(callIntent)
        }

        val decimalFormat = DecimalFormat("#,###")

        // 배열 형태이기 때문에 TextView 의 append() 사용
        if (storeItem.menus.isNotEmpty()) {
            for (menu in storeItem.menus) {
                val menuPriceFormatted = decimalFormat.format(menu.price.toInt())
                if (menu == storeItem.menus.last()) {
                    binding.storeMenuList.append(menu.name)
                    binding.storeMenuPriceList.append(menuPriceFormatted + "원")
                }else{
                    binding.storeMenuList.append(menu.name + "\n")
                    binding.storeMenuPriceList.append(menuPriceFormatted + "원\n")
                }
            }
        } else{
            binding.storeMenuList.append("메뉴 정보가 없습니다.")
        }
    }

    companion object {
        val STORE_ITEM = "STORE_ITEM"
    }

}