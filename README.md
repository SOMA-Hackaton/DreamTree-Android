<h1 align="center">🌳 DreamTree 🌳</h1>

<p align="center">
  <img width="50%" src="https://user-images.githubusercontent.com/54823396/118192618-60a2e080-b481-11eb-881d-eeba9102ea6c.png"> 
</p>

<p align="center">
<a href="https://dreamtree-front-vlgsh.run.goorm.io">DreamTree 웹 바로가기</a> & <a href="https://dreamtree-front-vlgsh.run.goorm.io/apk/apkQR.png">DreamTree 앱 다운받기</a>
</p>

# Android Application

## :speech_balloon: Language
`Kotlin`

## 🔎 Architecture
`MVVM`

## 🛠️ Technical Stack
`AAC`, `Koin`, `Databinding`, `Retrofit`, `RxJava`, `Okhttp`, `Glide`, `NaverMap`

## :camera: Screenshot
- **항공뷰 & 시티뷰**

<img width="30%" src="https://user-images.githubusercontent.com/54823396/118193250-5a613400-b482-11eb-962a-c87f21cce769.jpg"> <img width="30%" src="https://user-images.githubusercontent.com/54823396/118193246-59300700-b482-11eb-92fe-f3d915c4b450.jpg">

- **반경거리조절**

<img width="30%" src="https://user-images.githubusercontent.com/54823396/118193237-559c8000-b482-11eb-8296-07039bd2a1a9.jpg"> 

- **마커 클러스터링**

<img width="30%" src="https://user-images.githubusercontent.com/54823396/118193248-59c89d80-b482-11eb-8d61-46334da7970a.jpg"> 

- **검색 기능**

<img width="30%" src="https://user-images.githubusercontent.com/54823396/118193222-503f3580-b482-11eb-98c0-7e717fc7b7b1.jpg">

- **가맹점 상세정보 조회** 

<img width="30%" src="https://user-images.githubusercontent.com/54823396/118193242-57664380-b482-11eb-84eb-6891cd4f4e12.jpg"> <img width="30%" src="https://user-images.githubusercontent.com/54823396/118193243-57feda00-b482-11eb-8465-c78179249266.jpg"> <img width="30%" src="https://user-images.githubusercontent.com/54823396/118193244-58977080-b482-11eb-9204-e639b803a6e3.jpg">

- **잔액조회**

<img width="30%" src="https://user-images.githubusercontent.com/54823396/118193247-59c89d80-b482-11eb-9258-7c8635d92e6f.jpg">

# Web Application

## :speech_balloon: Language
`Javascript`

## 🔎 Architecture
`MVVM`

## 🛠️ Technical Stack
`VueJS` `Vuetify` `vue-naver-maps` `Axios`

## :camera: Screenshot
- **메인뷰**

<img width="1680" height="500" alt="기본화면" src="https://user-images.githubusercontent.com/12512382/118198729-6a7e1100-b48c-11eb-918e-8c3b5c2e1b06.png">

- **검색 기능**

<img width="1680" height="500" alt="상세검색화면" src="https://user-images.githubusercontent.com/43667316/118195589-72d34d80-b486-11eb-8cf2-e347b0e03cbc.png">

<img width="1680" height="500" alt="없는가게" src="https://user-images.githubusercontent.com/43667316/118196283-bbd7d180-b487-11eb-9f58-96fe2f4e1ca6.png">

- **가맹점 상세정보 조회** 

<img width="1680" height="500" alt="가게상세정보" src="https://user-images.githubusercontent.com/43667316/118196165-80d59e00-b487-11eb-971c-d10f2a809cf2.png">

<img width="1680" height="500" alt="메뉴없음" src="https://user-images.githubusercontent.com/43667316/118196457-00636d00-b488-11eb-8595-e16181d1d3da.png">

- **위치기반 가맹점탐색** 
<img width="1680" height="500" alt="메뉴없음" src="https://user-images.githubusercontent.com/43667316/118197278-921faa00-b489-11eb-9b47-43968c3f69be.gif">

# Server

## **💬 Language**

`Javascript`

## **🔎 Architecture**

`REST`

## **🛠️ Technical Stack**

`Node.js` `Express` `mongoDB` `naverMaps-api-Geocoding`

## 💻 **APIs**

### 1. 꿈나무 카드 가맹점 전체 조회 쿼리

    index router를 통해 마포구에 있는 가게들의 전체 데이터를 받아옵니다.
    공공데이터 '꿈나무카드가맹점 현황'과 naver-Maps-Geocoding으로 데이터 생성

- **HTTP Method:** `GET` 
- **Endpoint:** `https://dreamtree-dywzy.run.goorm.io/`

### 2. 위경도 기반 주변 가맹점 정보 쿼리

    위치 정보 (위도, 경도, 거리)를 전달하여 현재 위치로부터 
    거리 안에 있는 모든 가게 들 중 가까운 가게부터 데이터를 받아옵니다.  

- **HTTP Method:** `GET` 
- **Endpoint:** `https://dreamtree-dywzy.run.goorm.io/location?latitude={latitude}&logitude={logitude}&distance={distance}`

### 3. 키워드(업체명) 검색 기반 가맹점 정보 쿼리

    업체명 중 일부를 쿼리 파라미터로 전달하여
    가맹점 정보를 받아옵니다(검색 기능).

- **HTTP Method:** `GET` 
- **Endpoint:** `https://dreamtree-dywzy.run.goorm.io/keyword?storename={storename}`

# ✋ Part
|Part|Name|
|------|---|
|**Front-end**|이지훈, 황수민|
|**Android**|김현준, 박해민|
|**Back-end**|이인서, 이현민|
