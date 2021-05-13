package com.soma_quokka.dreamtree.presentation.main.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_UP
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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
import org.koin.android.ext.android.bind
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
        binding.btnSurroundMeter.text = meter.toString()+"m"
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

        binding.btnSurroundMeter.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog()
            bottomSheetDialog.show(supportFragmentManager, "bottomSheetDialog")
        }



        /**
         * RecyclerView Adapter 적용
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
            this.hint = "검색어를 입력해주세요"

            // EditText 에 포커스가 갔을 때 ClearButton 활성화
            this.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {
                    binding.textClearButton.visibility = View.VISIBLE
                } else {
                    binding.textClearButton.visibility = View.GONE
                }
            }

            /**
             * Rx 방식으로 사용자 입력 쿼리 처리 동작
             * - 사용자 입력이 멈추면 데이터 스트리밍 (입력 완료 상황으로 간주)
             */
            val editTextChangeObservable = binding.searchView.textChanges()
            val searchEditTextSubscription: Disposable =
                // 생성한 Observable 에 Operator 추가
                editTextChangeObservable
                    // 마지막 글자 입력 0.8초 후에 onNext 이벤트로 데이터 스트리밍
                    .debounce(800, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    // 구독을 통해 이벤트 응답 처리
                    .subscribeBy(
                        onNext = {
                            Log.d("Rx", "onNext : $it")
                            // 사용작 쿼리가 비어있지 않다면 API 호출
                            runOnUiThread {
                                if (!it.isNullOrBlank()){
                                    refreshSearchResultList(it.toString())
                                }else{
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
            // CompositeDisposable 에 추가
            compositeDisposable.add(searchEditTextSubscription)
        }

        // ClearButton 눌렀을 때 쿼리 Clear
        binding.textClearButton.setOnClickListener {
            binding.searchView.text.clear()
        }
    }

    /**
     * 키보드 이외의 영역을 터치했을 때, 키보드를 숨기는 동작
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
       // 사용자에 의해 변경된 쿼리(검색어) 기반으로 API 호출
        viewModel.getSearchResultStoreList(userQuery)
        Log.d(TAG, "refresh")
        viewModel.searchResultStoreListLiveData.observe(
            this@MapActivity,
            {
                StoreList(it).storeList.forEach{
                    Log.d(TAG, it.name)
                }
                // API 호출 결과 데이터가 있다면 아이템 갱신 적용
                if (it.size != 0) {
                    binding.storeListRecyclerView.visibility = View.VISIBLE
                    binding.noResultCard.visibility = View.GONE

                    recyclerViewAdapter.setItem(StoreList(it).storeList)
                } else {
                    // 만약 아이템이 없다면, '아이템 없음'을 사용자에게 알리는 뷰를 띄워줌
                    binding.storeListRecyclerView.visibility = View.GONE
                    binding.noResultCard.visibility = View.VISIBLE
                }
            })
    }

    override fun onDestroy() {
        // MemoryLeak 방지를 위해 CompositeDisposable 해제
        this.compositeDisposable.clear()
        super.onDestroy()
    }
}
