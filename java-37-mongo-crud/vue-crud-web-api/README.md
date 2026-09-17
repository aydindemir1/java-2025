# Vue 3 CRUD Frontend

Vue 3 kullanılarak geliştirilmiş bir **Tutorial CRUD frontend** uygulamasıdır.

Uygulama, Spring Boot + MongoDB backend REST API ile haberleşerek tutorial kayıtları üzerinde listeleme, ekleme, güncelleme, silme ve başlığa göre arama işlemlerini gerçekleştirir.

## Technologies

- Vue 3
- Vue Router 4
- Axios
- Bootstrap 4.6
- JavaScript
- Vue CLI
- Babel
- ESLint

## Features

- Tutorial listeleme
- Tutorial detay görüntüleme
- Yeni tutorial ekleme
- Tutorial güncelleme
- Tutorial silme
- Tüm tutorial kayıtlarını silme
- Başlığa göre arama
- Published / Unpublished durumunu güncelleme
- Vue Router ile sayfa yönlendirme
- Axios ile REST API entegrasyonu

## Project Structure

```text
src
├── components
│   ├── AddTutorial.vue
│   ├── Tutorial.vue
│   └── TutorialsList.vue
├── services
│   └── TutorialDataService.js
├── http-common.js
├── router.js
├── App.vue
└── main.js
