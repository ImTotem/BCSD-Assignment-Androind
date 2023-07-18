# BCSD-Assignment
BCSD 실습 과제 - 14주차

- MVVM (최대한 패턴 적용해보기)
- Material
- Databinding
- DiffUtil
- Retrofit
- Gson
- 디바운싱, 페이징 처리
- Github Open Api
  - <https://docs.github.com/en>
  - 사용 시, Github Token 적용하기 (postman 으로 통신해보기)
  - 연관 검색어로 레포 찾기 : <https://docs.github.com/en/rest/search/search?apiVersion=2022-11-28#search-users>
  - 검색어 레포와 연관된 레포들 찾기 : <https://docs.github.com/en/rest/repos/repos?apiVersion=2022-11-28#list-repositories-for-a-user>
- Progress Bar

- 깃허브 레포지토리 검색하기
  - 검색어 입력창 만들기
  - 검색어와 연관된 repository 불러오기 (RecyclerView)
  - 검색어와 연관된 repository 를 불러올 때, 디바운싱을 이용하여 서버와의 통신 부하 줄이기
  - 나열된 repository  클릭 시 해당 연관된 레포지토리 나열 (RecyclerView)
  - repository  페이징 처리하기
  - repository  클릭 시 Github 로 이동
