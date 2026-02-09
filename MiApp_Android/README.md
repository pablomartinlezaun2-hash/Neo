# MiApp Android (Jetpack Compose)

## Requisitos
- Android Studio Iguana/Koala
- JDK 17
- Dispositivo físico o emulador

## Instalación
1. Abre `MiApp_Android` en Android Studio.
2. Sincroniza Gradle.
3. Ejecuta en emulador o dispositivo.

## Ejecutar
- Botón **Run** en Android Studio o:
  ```bash
  ./gradlew :app:installDebug
  ```

## Generar `.apk` o `.aab`
- APK debug:
  ```bash
  ./gradlew :app:assembleDebug
  ```
- AAB release:
  ```bash
  ./gradlew :app:bundleRelease
  ```

## Firma y Play Store
1. Crea un keystore:
   ```bash
   keytool -genkey -v -keystore release.keystore -alias norteneo -keyalg RSA -keysize 2048 -validity 10000
   ```
2. Declara `signingConfigs` en `app/build.gradle`.
3. Genera el `.aab` y súbelo en Google Play Console.

## Integración con backend
- Actualiza la constante `BASE_URL` en `model/ApiClient.kt`.
- Endpoints definidos: `POST /auth/login`, `POST /auth/signup`, `GET /dashboard`.
- Agrega nuevos modelos en `model/` y métodos en `ApiService` según la API real.

## Notificaciones push
1. Configura Firebase Cloud Messaging (FCM) y añade `google-services.json`.
2. Solicita permisos en `SettingsScreen` con `NotificationHelper`.
3. Envía el token FCM al backend.

## Almacenamiento seguro
- Tokens guardados en `DataStore` (`SessionStore`).

## Estructura
```
MiApp_Android/
├── app/
│   ├── src/main/java/com/miproyecto/
│   │   ├── ui/
│   │   ├── viewmodel/
│   │   └── model/
│   └── res/
├── build.gradle
└── README.md
```
