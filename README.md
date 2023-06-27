# BCSD-Assignment
BCSD 실습 과제 - 11주차

### 스톱워치 구현
- 화면 상단에 (Start/Pause Toggle, Stop, Lap) 버튼 3개와 Timer 시간을 출력하는 TextView를 만든다.
- Start 시 Timer를 작동 시켜서 TextView에 “mm : ss : ms” 출력한다. - Start버튼의 Text를 Pause로 바꾼다.
- Stop 시 Timer를 작동 중지하고 TextView를 “00 : 00 : 00”으로 돌린다.
- 동작 시 Pause 버튼을 누르면 일시정지를 한다. TextView 또한 멈춘다.
- Pause버튼의 Text를 Start로 바꾼다.
  - 만약 다시 Start 버튼을 누를 경우 Timer를 재작동시킨다.
- 버튼 아래에는 RecyclerView를 만들고 Lap 버튼을 누를 때마다 타이머의 시간을 “mm : ss : ms”의 형태로 추가한다.
