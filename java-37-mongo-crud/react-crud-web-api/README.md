# React CRUD Frontend

React 18 kullanılarak geliştirilmiş bir **Tutorial CRUD frontend** uygulamasıdır.

Uygulama, Spring Boot + MongoDB backend REST API ile haberleşerek tutorial kayıtları üzerinde listeleme, ekleme, güncelleme, silme ve başlığa göre arama işlemlerini gerçekleştirir.

## Technologies

- React 18
- React Router 6
- Axios
- Bootstrap 4.6
- JavaScript
- React Scripts
- Testing Library

## Features

- Tutorial listeleme
- Tutorial detay görüntüleme
- Yeni tutorial ekleme
- Tutorial güncelleme
- Tutorial silme
- Tüm tutorial kayıtlarını silme
- Başlığa göre arama
- Published / Unpublished durumunu güncelleme
- React Router ile sayfa yönlendirme
- Axios ile REST API entegrasyonu

## Project Structure

```text
src
├── common
│   └── with-router.js
├── components
│   ├── add-tutorial.component.js
│   ├── tutorial.component.js
│   └── tutorials-list.component.js
├── services
│   └── tutorial.service.js
├── http-common.js
├── App.js
└── index.js
