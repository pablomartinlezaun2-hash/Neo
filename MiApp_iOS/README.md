# MiApp iOS (SwiftUI)

## Requisitos
- macOS con Xcode 15+
- Cuenta de Apple Developer para firmar y exportar `.ipa`

## Instalación
1. Abre Xcode y crea un proyecto SwiftUI llamado `MiApp_iOS` (Bundle ID: `com.miproyecto.norteneo`).
2. Copia el contenido de `App/` dentro del proyecto (arrastrar a Xcode y marcar "Copy items if needed").
3. Añade permisos en `Info.plist`:
   - `NSCameraUsageDescription`
   - `NSLocationWhenInUseUsageDescription`
   - `NSUserNotificationUsageDescription`

## Ejecutar en simulador o dispositivo
1. Selecciona un simulador o un iPhone/iPad físico.
2. Pulsa **Run** (⌘R).

## Configurar certificados y perfiles
1. En **Signing & Capabilities**, selecciona tu equipo.
2. Activa **Automatically manage signing**.
3. Para builds de producción, crea un **App ID** y un **Provisioning Profile** en Apple Developer.

## Generar `.ipa`
1. **Product > Archive**.
2. En el Organizer, selecciona la build.
3. **Distribute App** y exporta como `.ipa`.

## Integración con backend
- Cambia `AppConstants.apiBaseURL` en `App/Models/AppConstants.swift` para apuntar al backend real.
- Los endpoints `POST /auth/login`, `POST /auth/signup` y `GET /dashboard` están declarados en `App/Models/APIClient.swift`.
- Para agregar nuevas rutas, extiende `APIClient` con métodos adicionales y modelos `Codable`.

## Notificaciones push
1. Activa **Push Notifications** en el target.
2. Integra tu proveedor (APNs/Firebase) en `NotificationManager`.
3. Registra el token de dispositivo en el backend.

## Almacenamiento seguro
- Tokens almacenados con `KeychainStore` (service `com.norteneo.auth`).

## Estructura
```
MiApp_iOS/
├── MiApp_iOS.xcodeproj
├── App/
│   ├── Views/
│   ├── ViewModels/
│   └── Models/
├── Assets/
└── README.md
```
