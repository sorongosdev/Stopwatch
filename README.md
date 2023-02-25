# Stopwatch
 카운트다운 설정 후 화면이 물드며 시작되는 스톱워치
 
 ## 핵심 기능
 1. Timer
  * 기본으로 제공하는 기능
  * 워커 쓰레드에서 동작
 2. runOnUiThread / binding.root.post
  * UI를 바꾸는 작업은 메인 쓰레드에서만 동작해야함
 3. 랩 기능
  * 코틀린에서 텍스트뷰를 동적으로 추가하기
 4. 타이머 3초전 알림음
  * ToneGenerator 사용
 ## 스크린샷
 1.초기 화면
<img src="https://user-images.githubusercontent.com/61692372/221339307-0f305ce3-812e-4329-846b-a1295093086e.png" width="200" height="380"/><br>
 2.타이머 설정    
<img src="https://user-images.githubusercontent.com/61692372/221339758-38c0ddcc-8066-480e-a6bd-61fdf2409fd6.png" width="200" height="380"/><br>
 3.타이머 동작    
<img src="https://user-images.githubusercontent.com/61692372/221339360-dee7cc0b-9f98-4aeb-9564-128e8cc10ae3.png" width="200" height="380"/><br>
 4.스톱워치 동작    
<img src="https://user-images.githubusercontent.com/61692372/221339394-ab187cdf-355d-4e87-880d-16d6f5029be1.png" width="200" height="380"/><br>
 5.랩 기록    
<img src="https://user-images.githubusercontent.com/61692372/221339399-cb772b56-67c5-4e70-b044-0c613d23e50b.png" width="200" height="380"/><br>
