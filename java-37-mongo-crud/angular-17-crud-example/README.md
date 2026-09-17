# Angular 17 CRUD Frontend

Angular 17 ve TypeScript kullanılarak geliştirilmiş bir **Tutorial CRUD frontend** uygulamasıdır.

Uygulama, Spring Boot + MongoDB backend REST API ile haberleşerek tutorial kayıtları üzerinde listeleme, ekleme, güncelleme, silme ve başlığa göre arama işlemlerini gerçekleştirir.

## Technologies

- Angular 17
- TypeScript 5.2
- RxJS
- Angular Router
- Angular Forms
- HttpClient
- Bootstrap 4.6
- Jasmine
- Karma

## Features

- Tutorial listeleme
- Tutorial detay görüntüleme
- Yeni tutorial ekleme
- Tutorial güncelleme
- Tutorial silme
- Tüm tutorial kayıtlarını silme
- Başlığa göre arama
- Published durumunu güncelleme
- Angular routing
- REST API entegrasyonu

## Project Structure

```text
src/app
├── components
│   ├── add-tutorial
│   ├── tutorial-details
│   └── tutorials-list
├── models
│   └── tutorial.model.ts
├── services
│   └── tutorial.service.ts
├── app-routing.module.ts
├── app.module.ts
└── app.component.ts
