# BCSD-Assignment
BCSD 실습 과제 - 9주차

- MainActivity에 기기에 저장된 음악 파일을 리스팅한다(RecyclerView).
  - 필수 포함 요소 : 음악의 제목, 가수, 재생 시간(hh:mm:ss)
  - READ_EXTERNAL_STORAGE 권한 필요 : 권한 다이얼로그를 Activity Result API의 ActivityResultContracts.RequestPermission를 이용해서 구현
  - 권한 허용이 되지 않을 경우 음악 파일을 리스팅하는 대신 권한을 허용 메시지와 권한 다이얼로그를 다시 띄우는 버튼 추가
