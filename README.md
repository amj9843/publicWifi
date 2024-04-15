# 내 위치 기반 공공 와이파이 정보를 제공하는 웹 서비스

## 개발 환경
- IDE: eclipse-jee-2024-03
- JDK: 17

## 사용 툴
- Java1.7
- Maven
- SQLite-jdbc
- JSP
- Tomcat 9.0
- [Open API](https://data.seoul.go.kr/dataList/OA-20883/S/1/datasetView.do) [[guide](docs/public_wifi_open_api_doc.md)]

## 기능
- [x] 내 위치 정보를 입력하면 가까운 위치에 있는 와이파이 정보 조회
- [x] 가까운 와이피이 검색 기록을 저장/조회/삭제
- [x] 와이파이 상세 정보 조회
- [] 즐겨찾기 그룹 CRUD (UPDATE 제외 완료)
- [] 와이파이를 즐겨찾기 그룹에 추가/삭제 (추가만 완료)
- [] 즐겨찾기 조회

## ERD
![erd](https://github.com/amj9843/publicWifi/assets/71617201/86434b94-7a04-4867-9b6d-26feae81e074)


## 실행
- common_modules 내의 db.java 에서 폴더 경로를 생성해줘야 한다.
- common_modules 내의 wifi_api.java에서 키를 설정해 주어야 한다.

## 아쉬운 점 / 어려웠던 점
주어진 기간은 넉넉했으나 개인 사정으로 그 기간동안 집중할 수 없었다. 결국 기본 조건인 와이파이 상세 정보 조회까지만 만들었다.
기능도 모두 구현할 수 있을 것 같은데, 급하게 하다 보니 기능 별로 코드를 나누지도 못하고 되려 꼬여 더 오래걸린 것 같다.

스프링 언어에 대해 전혀 몰라서 혼자 해보려고 했는데 정작 기능하게 한 것도 제대로 이해한 건 없는 것 같다.
properties로 속성값 빼내서 넣는 것도 왜 안되는건지... 여유시간이 나면 차근차근 다시 해 보아야할 것 같다.
