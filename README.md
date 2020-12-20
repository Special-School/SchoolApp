# SchoolApp

이 프로젝트는 Kotlin을 사용하여 Clean Architecture에 따라 개발하고, AAC(Android Architecture Components)와 Coroutine, Dagger-hilt 등 Google에서 권장하는 라이브러리들을 최대한 사용해보는 것을 목표로 제작되었습니다.

## Features

이 앱은 전국의 특수학교 데이터를 가져와 보여주고, 검색할 수 있습니다. 또한 원하는 특수학교를 즐겨찾기 해둘 수 있습니다.

## Architecture

이 앱은 MVVM 패턴과 [앱 아키텍처 가이드](https://developer.android.com/jetpack/guide), Clean Architecture에 따라 제작되었습니다.

Activity와 Fragment의 로직은 ViewModel에 있고, View 들은 LiveData를 사용하여 앱의 데이터 변화를 관찰하고, Data Binding 라이브러리를 사용해 View의 구성요소를 앱의 데이터에 연결합니다.

이 앱은 크게 3개의 layer로 구성됩니다.

- Presentation Layer: View(Activity, Fragment)와 ViewModel을 포함합니다.

- Domain Layer: 비즈니스 로직을 구현한 Use case와 앱의 실질적인 데이터인 도메인 모델을 포함합니다.

- Data Layer
  - Repository: Use case에서 필요로 하는 데이터를 다루는 부분입니다. 여러 종류의 data source를 추상화합니다.

  - Data source: 여러 종류의 데이터 저장소를 의미하며, 데이터의 입출력이 실행되는 부분입니다.

  - Entity: data source에서 사용되는 데이터를 정의한 모델로 DB Entity나 JSON 등이 포함됩니다.

이 앱은 의존성 주입을 위해 Dagger를 기반으로 제작된 Hilt를 사용하고 있습니다. Hilt는 Dagger의 보일러 플레이트 코드를 줄여서 설정이 상대적으로 쉽고 이해하기 편합니다.

이 앱은 학교 데이터를 검색하기 위해 Room 라이브러리의 Fts4(Full Text Search) 기능을 사용하고 있습니다.

이 앱이 사용하는 학교 데이터는 Firebase storage에 저장된 데이터를 불러옵니다. 불러오는 데 실패할 경우 미리 로컬에 저장되어 있는 데이터를 사용합니다.

이 앱의 인증 모듈은 Firebase AuthUI를 사용하고, 앱에서 업데이트 되는 사용자 데이터는 Firestore에 저장됩니다.

이 앱의 모든 비동기 작업은 [Kotlin coroutine](https://kotlinlang.org/docs/reference/coroutines-overview.html)으로 작성되었습니다. 또한 Gradle 빌드 스크립트를 Kotlin DSL을 사용합니다.

## TODO

- Test code 추가

- 검색 필터 등 새로운 기능 추가

## Copyright

```
MIT License

Copyright (c) 2020 mobulabs

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

