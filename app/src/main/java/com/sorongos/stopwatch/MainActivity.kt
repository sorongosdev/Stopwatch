package com.sorongos.stopwatch


import android.media.AudioManager
import android.media.ToneGenerator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.sorongos.stopwatch.databinding.ActivityMainBinding
import com.sorongos.stopwatch.databinding.DialogCountdownSettingBinding
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var countdownSecond = 5 // 시작전 카운트다운
    private var currentDeciSecond = 0 // 시작 후 증가하는 시간
    private var timer: Timer? = null
    private var currentCountDownDeciSecond = countdownSecond * 10
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.countdownTextView.setOnClickListener {
            showCountDownSettingDialog()
        }

        binding.startButton.setOnClickListener {
            start()
            binding.startButton.isVisible = false
            binding.stopButton.isVisible = false
            binding.pauseButton.isVisible = true
            binding.lapButton.isVisible = true
        }
        binding.stopButton.setOnClickListener {
            showAlertDialog()
        }
        binding.pauseButton.setOnClickListener {
            pause()
            binding.startButton.isVisible = true
            binding.stopButton.isVisible = true
            binding.pauseButton.isVisible = false
            binding.lapButton.isVisible = false
        }
        binding.lapButton.setOnClickListener {
            lap()
        }
        initCountDownViews()
    }

    private fun initCountDownViews() {
        binding.countdownTextView.text = String.format("%02d", countdownSecond)
        binding.countdownProgressBar.progress = 100 // progressBar을 초기화
    }

    private fun start() {
        timer = timer(initialDelay = 0, period = 100) {
            if (currentCountDownDeciSecond == 0) { // 타이머가 다 되면 스톱워치 실행
                currentDeciSecond += 1

                val min = currentDeciSecond.div(10) / 60
                val sec = currentDeciSecond.div(10) % 60
                val deciSec = currentDeciSecond % 10
                runOnUiThread {
                    binding.countdownGroup.isVisible = false
                    binding.timeTextView.text =
                        String.format("%02d:%02d", min, sec)
                    binding.tickTextView.text = deciSec.toString()
                }
            } else { // 타이머 실행
                currentCountDownDeciSecond -= 1
                val sec = currentCountDownDeciSecond / 10
                val progress =
                    (currentCountDownDeciSecond / (countdownSecond * 10f)) * 100 // 백분율로 표시

                binding.root.post { // 카운트 다운 텍스트와 프로그레스바 UI 업데이트
                    binding.countdownTextView.text = String.format("%02d", sec)
                    // progress를 int로 바꾸어서 바에 나타냄
                    binding.countdownProgressBar.progress = progress.toInt()
                }
            }
            if(currentDeciSecond == 0 && currentCountDownDeciSecond < 31
                && currentCountDownDeciSecond % 10 == 0) { //비프음
                val toneType = if(currentCountDownDeciSecond == 0) ToneGenerator.TONE_CDMA_HIGH_L
                else ToneGenerator.TONE_CDMA_ANSWER

                //stream alarm 알람 소리 크기로
                ToneGenerator(AudioManager.STREAM_ALARM, ToneGenerator.MAX_VOLUME)
                    .startTone(toneType, 100)
            }
        }
    }

    /**완전 정지*/
    private fun stop() {
        //완전 정지와 시작 버튼만 보이게
        binding.startButton.isVisible = true
        binding.stopButton.isVisible = true
        binding.pauseButton.isVisible = false
        binding.lapButton.isVisible = false

        //완전 정지, 모두 0으로 초기화
        currentDeciSecond = 0
        currentCountDownDeciSecond = countdownSecond * 10

        binding.timeTextView.text = "00:00"
        binding.tickTextView.text = "0"

        //카운트다운 그룹 다시 보이게
        binding.countdownGroup.isVisible = true
        initCountDownViews()

        //완전 정지되면 기록 텍스트뷰들을 삭제
        binding.lapContainerLinearLayout.removeAllViews()
    }

    private fun pause() {
        timer?.cancel()
        timer = null
    }

    private fun lap() {
        if(currentDeciSecond == 0) return

        val container = binding.lapContainerLinearLayout
        //동적으로 텍스트뷰 생성
        TextView(this).apply {
            textSize = 20f
            gravity = Gravity.CENTER
            val minutes = currentDeciSecond.div(10) / 60
            val seconds = currentDeciSecond.div(10) % 60
            val deciSeconds = currentDeciSecond % 10
            text = container.childCount.inc().toString() + String.format( //몇번째 랩인지 넘버링
                "   %02d:%02d %01d",
                minutes,
                seconds,
                deciSeconds
            )
            setPadding(30, 30, 30, 30)
        }.let{labTextView ->
            container.addView(labTextView, 0)
        }
        
    }

    /**카운트 다운 초를 설정하는 다이얼로그를 띄움*/
    private fun showCountDownSettingDialog() {
        AlertDialog.Builder(this).apply {
            val dialogBinding = DialogCountdownSettingBinding.inflate(layoutInflater)
            with(dialogBinding.countdownSecondPicker) {
                maxValue = 20
                minValue = 0
                value = countdownSecond // 창을 열었을 때 초기값
            }
            setView(dialogBinding.root)
            setPositiveButton("확인") { _, _ ->
                countdownSecond = dialogBinding.countdownSecondPicker.value
                binding.countdownTextView.text = String.format("%02d", countdownSecond)
                currentCountDownDeciSecond = countdownSecond * 10
            }
            setNegativeButton("취소", null)
        }.show()
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("종료하시겠습니까?")
            setPositiveButton("네") { _, _ ->
                stop()
            }
            setNegativeButton("아니오", null)
        }.show()
    }
}