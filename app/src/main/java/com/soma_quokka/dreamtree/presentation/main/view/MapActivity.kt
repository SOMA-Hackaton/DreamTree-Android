package com.soma_quokka.dreamtree.presentation.main.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_UP
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.jakewharton.rxbinding4.widget.textChanges
import com.soma_quokka.dreamtree.R
import com.soma_quokka.dreamtree.adapter.StoreListAdapter
import com.soma_quokka.dreamtree.data.model.StoreList
import com.soma_quokka.dreamtree.databinding.ActivityMapBinding
import com.soma_quokka.dreamtree.presentation.base.BaseActivity
import com.soma_quokka.dreamtree.presentation.main.view.MapViewFragment.Companion.STORE_ITEM
import com.soma_quokka.dreamtree.presentation.main.viewmodel.MapViewModel
import com.soma_quokka.dreamtree.presentation.store_detail.StoreDetailActivity
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class MapActivity : BaseActivity<ActivityMapBinding, MapViewModel>(R.layout.activity_map), OnMeterSetListener {
    companion object {
        const val TAG = "MapActivity"
        const val ARG_PARAM = "STORE_LIST"
    }

    override val viewModel: MapViewModel by viewModel()

    private val mapViewFragment = MapViewFragment()
    private var compositeDisposable = CompositeDisposable()
    private lateinit var recyclerViewAdapter: StoreListAdapter

    @SuppressLint("SetTextI18n")
    override fun onMeterSetListener(meter: Double) {
        binding.btnSurroundMeter.text = meter.toString()+"M"
        mapViewFragment.setSurroundMeter(meter)
        mapViewFragment.setZoom(meter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = Bundle()


        viewModel.getSurroundStoreList()
        viewModel.surroundStoreListLiveData.observe(this,
            {
                bundle.putParcelable(ARG_PARAM, StoreList(it))
                mapViewFragment.arguments = bundle
                supportFragmentManager.beginTransaction().add(R.id.fragment_map, mapViewFragment)
                    .commit()
            }
        )

        binding.btnCurrentPosition.setOnClickListener {
            mapViewFragment.setCurrentPosition()
        }

        binding.cardSurroundMeter.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog()
            bottomSheetDialog.show(supportFragmentManager, "bottomSheetDialog")
        }

        binding.balanceCheckCard.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse("https://m.shinhancard.com/mob/MOBFM064N/MOBFM064R01.shc"))
            startActivity(browserIntent)
        }


        /**
         * RecyclerView Adapter ??????
         */
        recyclerViewAdapter = StoreListAdapter {
            Log.d("ItemClick", "ItemClicked")
            val intent = Intent(this, StoreDetailActivity::class.java)
            intent.putExtra(STORE_ITEM, it)
            startActivity(intent)
        }

        binding.storeListRecyclerView.apply {
            this.adapter = recyclerViewAdapter
            this.layoutManager = LinearLayoutManager(context)
            this.setHasFixedSize(true)
        }

        binding.searchView.apply {
            this.hint = "???????????? ??????????????????"

            // EditText ??? ???????????? ?????? ??? ClearButton ?????????
            this.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    binding.textClearButton.visibility = View.VISIBLE
                } else {
                    binding.textClearButton.visibility = View.GONE
                }
            }

            /**
             * Rx ???????????? ????????? ?????? ?????? ?????? ??????
             * - ????????? ????????? ????????? ????????? ???????????? (?????? ?????? ???????????? ??????)
             */
            val editTextChangeObservable = binding.searchView.textChanges()
            val searchEditTextSubscription: Disposable =
                // ????????? Observable ??? Operator ??????
                editTextChangeObservable
                    // ????????? ?????? ?????? 0.8??? ?????? onNext ???????????? ????????? ????????????
                    .debounce(800, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    // ????????? ?????? ????????? ?????? ??????
                    .subscribeBy(
                        onNext = {
                            Log.d("Rx", "onNext : $it")
                            // ????????? ????????? ???????????? ????????? API ??????
                            runOnUiThread {
                                if (!it.isNullOrBlank()) {
                                    refreshSearchResultList(it.toString())
                                } else {
                                    binding.storeListRecyclerView.visibility = View.GONE
                                    binding.noResultCard.visibility = View.GONE
                                }
                            }
                        },
                        onComplete = {
                            Log.d("Rx", "onComplete")
                        },
                        onError = {
                            Log.d("Rx", "onError : $it")
                        }
                    )
            // CompositeDisposable ??? ??????
            compositeDisposable.add(searchEditTextSubscription)
        }

        // ClearButton ????????? ??? ?????? Clear
        binding.textClearButton.setOnClickListener {
            binding.searchView.text.clear()
        }
    }

    /**
     * ????????? ????????? ????????? ???????????? ???, ???????????? ????????? ??????
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val view = currentFocus
        if (view != null && (ev!!.action === ACTION_UP || MotionEvent.ACTION_MOVE === ev!!.action) &&
            view is EditText && !view.javaClass.name.startsWith("android.webkit.")
        ) {
            binding.storeListRecyclerView.visibility = View.GONE
            binding.noResultCard.visibility = View.GONE

            val scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x = ev!!.rawX + view.getLeft() - scrcoords[0]
            val y = ev!!.rawY + view.getTop() - scrcoords[1]
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) (this.getSystemService(
                INPUT_METHOD_SERVICE
            ) as InputMethodManager).hideSoftInputFromWindow(
                this.window.decorView.applicationWindowToken, 0
            )
        }

        return super.dispatchTouchEvent(ev)
    }

    private fun refreshSearchResultList(userQuery: String) {
       // ???????????? ?????? ????????? ??????(?????????) ???????????? API ??????
        viewModel.getSearchResultStoreList(userQuery)
        Log.d(TAG, "refresh")
        viewModel.searchResultStoreListLiveData.observe(
            this@MapActivity,
            {
                StoreList(it).storeList.forEach {
                    Log.d(TAG, it.name)
                }
                // API ?????? ?????? ???????????? ????????? ????????? ?????? ??????
                if (it.size != 0) {

                    YoYo.with(Techniques.SlideInUp)
                        .duration(700)
                        .playOn(binding.storeListRecyclerView)

                    binding.storeListRecyclerView.visibility = View.VISIBLE
                    binding.noResultCard.visibility = View.GONE
                    recyclerViewAdapter.setItem(StoreList(it).storeList)
                } else {
                    // ?????? ???????????? ?????????, '????????? ??????'??? ??????????????? ????????? ?????? ?????????
                    binding.storeListRecyclerView.visibility = View.GONE
                    binding.noResultCard.visibility = View.VISIBLE
                }
            })
    }

    override fun onDestroy() {
        // MemoryLeak ????????? ?????? CompositeDisposable ??????
        this.compositeDisposable.clear()
        super.onDestroy()
    }
}
