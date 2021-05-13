package com.soma_quokka.dreamtree.presentation.main.view

import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_UP
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.jakewharton.rxbinding4.widget.textChanges
import com.soma_quokka.dreamtree.R
import com.soma_quokka.dreamtree.databinding.ActivityMapBinding
import com.soma_quokka.dreamtree.presentation.base.BaseActivity
import com.soma_quokka.dreamtree.presentation.main.viewmodel.MapViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit


class MapActivity : BaseActivity<ActivityMapBinding, MapViewModel>(R.layout.activity_map) {
    override val viewModel: MapViewModel by viewModel()

    private val mapViewFragment = MapViewFragment()
    private var compositeDisposable = CompositeDisposable()

    companion object {
        const val TAG = "MapActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val bundle = Bundle()

        viewModel.getStoreList()
        viewModel.storeListLiveData.observe(this, Observer {
            //bundle.putParcelable("storeList", it)
        })

        supportFragmentManager.beginTransaction().add(R.id.fragment_map, mapViewFragment).commit()

        // DataBinding
        binding = DataBindingUtil.setContentView<ActivityMapBinding>(
            this,
            R.layout.activity_map
        )
        binding.viewModel = viewModel

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
                            runOnUiThread {
//                                viewModel.onChangeQuery(searchQuery = it.toString())
                            }
                            // TODO : 바뀐 쿼리 기반으로 API 추가 호출 필요
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
            view is EditText && !view.javaClass.name.startsWith( "android.webkit.")) {
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

    override fun onDestroy() {
        // MemoryLeak 방지를 위해 CompositeDisposable 해제
        this.compositeDisposable.clear()
        super.onDestroy()
    }
}