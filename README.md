# BCSD-Assignment
BCSD 실습 과제 - 10주차

### 뮤직 플레이어 구현
- 첫 화면에 MediaStore와 RecyclerView를 이용하여 기기에 저장된 음악 파일을 리스팅한다.
- Mediaplayer 사용
<https://developer.android.com/guide/topics/media/mediaplayer?hl=ko>
- 첫 화면 하단에 현재 재생중인 음악을 표시한다.
  - RecyclerView의 음악 아이템을 클릭하면 노래를 재생한다.
  - 노래 재생은 Foreground Service에 구현한다. 알림에 노래 제목 표시
- 첫 화면 하단의 재생중인 음악과 알림에 표시하고 있는 음악은 항상 동일해야 한다.
- 노래를 재생하면 지워지지 않는 알림을 띄운다.
- 알림을 클릭하면 음악 앱을 다시 표시한다.
- 배터리 사용량을 확인하고 배터리 용량에 따라 이벤트를 체크한다.