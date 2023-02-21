# Stopwatch
 카운트다운 설정 후 화면이 물드며 시작되는 스톱워치
 
 ## 핵심 기능
 1. Timer
  * 기본으로 제공하는 기능
  * 워커 쓰레드에서 동작
 2. runOnUiThread / binding.root.post
  * UI를 바꾸는 작업은 메인 쓰레드에서만 동작해야함
