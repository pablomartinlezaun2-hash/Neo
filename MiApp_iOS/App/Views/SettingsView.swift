import SwiftUI

struct SettingsView: View {
    @EnvironmentObject private var notificationManager: NotificationManager
    @EnvironmentObject private var locationManager: LocationManager
    @State private var wantsNotifications = false

    var body: some View {
        Form {
            Section(header: Text("Notificaciones")) {
                Toggle("Recibir notificaciones", isOn: $wantsNotifications)
                    .onChange(of: wantsNotifications) { _, newValue in
                        if newValue {
                            notificationManager.requestPermission()
                        }
                    }
                Text(notificationManager.permissionGranted ? "Permiso concedido" : "Permiso pendiente")
                    .foregroundColor(AppConstants.Colors.textSecondary)
            }

            Section(header: Text("Ubicación")) {
                Text("Estado: \(locationManager.status == .authorizedWhenInUse ? "Activo" : "Inactivo")")
                if let location = locationManager.currentLocation {
                    Text("Lat: \(location.coordinate.latitude), Lon: \(location.coordinate.longitude)")
                        .font(.footnote)
                }
            }

            Section(header: Text("Cámara")) {
                NavigationLink("Abrir cámara") {
                    CameraPickerView()
                }
            }
        }
        .navigationTitle("Ajustes")
    }
}
