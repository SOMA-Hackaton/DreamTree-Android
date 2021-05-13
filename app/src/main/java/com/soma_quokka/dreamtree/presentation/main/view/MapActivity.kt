package com.soma_quokka.dreamtree.presentation.main.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    }

    override fun onDestroy() {
        // MemoryLeak 방지를 위해 CompositeDisposable 해제
        this.compositeDisposable.clear()
        super.onDestroy()
    }
}